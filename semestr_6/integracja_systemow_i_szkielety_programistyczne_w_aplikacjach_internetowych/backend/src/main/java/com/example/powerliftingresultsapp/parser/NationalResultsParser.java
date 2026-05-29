package com.example.powerliftingresultsapp.parser;

import com.example.powerliftingresultsapp.model.enums.Sex;
import com.example.powerliftingresultsapp.model.enums.UniversityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NationalResultsParser {

    private static final Set<String> SKIP_RECORD_TOKENS = Set.of("NK", "PK", "DQ");
    private static final List<List<String>> ACADEMIC_PREFIXES = List.of(
            List.of("prof.", "dr", "hab."),
            List.of("mgr", "inż."),
            List.of("dr", "hab."),
            List.of("prof."),
            List.of("mgr"),
            List.of("dr")
    );
    private static final Set<String> UNIVERSITY_TYPES = Arrays.stream(UniversityType.values())
            .map(Enum::name)
            .collect(Collectors.toSet());

    private enum DocumentFormat {

        LT_2026(17, false) {
            @Override
            public double bestSquat(List<String> tokens, int n) {
                return bestLift(tokens.get(n - 12), tokens.get(n - 11), tokens.get(n - 10));
            }

            @Override
            public double bestBenchPress(List<String> tokens, int n) {
                return bestLift(tokens.get(n - 9), tokens.get(n - 8), tokens.get(n - 7));
            }

            @Override
            public double bestDeadlift(List<String> tokens, int n) {
                return bestLift(tokens.get(n - 6), tokens.get(n - 5), tokens.get(n - 4));
            }
        },

        GE_2026(18, true) {
            @Override
            public double bestSquat(List<String> tokens, int n) {
                return parseDouble(tokens.get(n - 12));
            }

            @Override
            public double bestBenchPress(List<String> tokens, int n) {
                return parseDouble(tokens.get(n - 8));
            }

            @Override
            public double bestDeadlift(List<String> tokens, int n) {
                return parseDouble(tokens.get(n - 4));
            }
        };

        private final int fixedTokensSuffix;
        private final boolean lastNameFirst;

        DocumentFormat(int fixedTokensSuffix, boolean lastNameFirst) {
            this.fixedTokensSuffix = fixedTokensSuffix;
            this.lastNameFirst = lastNameFirst;
        }

        public abstract double bestSquat(List<String> tokens, int n);

        public abstract double bestBenchPress(List<String> tokens, int n);

        public abstract double bestDeadlift(List<String> tokens, int n);

        protected double bestLift(String... attempts) {
            return Arrays.stream(attempts)
                    .map(NationalResultsParser::parseDouble)
                    .filter(a -> a > 0)
                    .max(Double::compare)
                    .orElse(0.0);
        }
    }

    private record WeightCategorySection(String weightCategory, String text) {
    }

    private record NameAndUniversity(String firstName, String lastName, String university) {
    }

    public List<ParsedAthleteRecord> parse(String rawText, Sex sex) {
        String text = fixBrokenLines(rawText);
        text = extractIndividualResults(text);
        DocumentFormat format = detectFormat(text);
        List<WeightCategorySection> sections = extractWeightCategorySections(text, format);
        List<ParsedAthleteRecord> records = extractRecords(sections, sex, format);
        assignPlacesInUniversityType(records);
        return records;
    }

    private String fixBrokenLines(String text) {
        text = text.replaceAll("(\\d+),\\r?\\n(\\d+)", "$1,$2");
        text = text.replaceAll("(\\d+,\\d{2,4})\\r?\\n(\\d{2,4})", "$1$2");
        text = text.replaceAll("-\\r?\\n(\\S+)", "-$1");
        text = text.replaceAll("(\\d+)\\r?\\n\\+", "$1+");
        text = text.replaceAll("(?m)^(\\d{1,3})\\s*$\\r?\\n(\\p{L}+)", "$1 $2");
        return text;
    }

    private String extractIndividualResults(String text) {
        String[] endMarkers = {"KLASYFIKACJA DRUŻYNOWA", "KLASYFIKACJA NAJLEPSZE"};
        int end = text.length();
        for (String marker : endMarkers) {
            int index = text.indexOf(marker);
            if (index >= 0 && index < end) end = index;
        }
        return text.substring(0, end);
    }

    private DocumentFormat detectFormat(String text) {
        return text.contains("-- kat.") ? DocumentFormat.GE_2026 : DocumentFormat.LT_2026;
    }

    private List<WeightCategorySection> extractWeightCategorySections(String text, DocumentFormat format) {
        Pattern pattern = Pattern.compile(
                (format == DocumentFormat.LT_2026)
                        ? "kategoria\\s+(\\d{2,3}\\+?)"
                        : "--\\s*kat\\.\\s*(\\d{2,3}\\+?)\\s*--",
                Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = pattern.matcher(text);
        List<int[]> positions = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        while (matcher.find()) {
            positions.add(new int[]{matcher.start(), matcher.end()});
            categories.add(matcher.group(1));
        }

        List<WeightCategorySection> sections = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) {
            int start = positions.get(i)[1];
            int end = (i + 1 < positions.size()) ? positions.get(i + 1)[0] : text.length();
            sections.add(new WeightCategorySection(categories.get(i), text.substring(start, end)));
        }
        return sections;
    }

    private List<ParsedAthleteRecord> extractRecords(List<WeightCategorySection> sections,
                                                     Sex sex, DocumentFormat format) {
        return sections.stream()
                .flatMap(s -> parseWeightCategorySection(s, sex, format).stream())
                .toList();
    }

    private List<ParsedAthleteRecord> parseWeightCategorySection(WeightCategorySection section,
                                                                 Sex sex, DocumentFormat format) {
        List<String> rawRecords = joinContinuationLines(section.text());
        List<ParsedAthleteRecord> parsedRecords = new ArrayList<>();

        for (String rawRecord : rawRecords) {
            if (skipRecordIntentionally(rawRecord)) continue;

            try {
                ParsedAthleteRecord parsedRecord = parseRecord(rawRecord, section.weightCategory(), sex, format);
                if (parsedRecord == null) log.warn("Record '{}' skipped due to invalid format.", rawRecord);
                else parsedRecords.add(parsedRecord);
            } catch (Exception e) {
                log.warn("Record '{}' skipped due to {}: {}.", rawRecord, e.getClass().getSimpleName(), e.getMessage());
            }
        }
        return parsedRecords;
    }

    private List<String> joinContinuationLines(String weightCategorySectionText) {
        List<String> lines = Arrays.stream(weightCategorySectionText.split("\\r?\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        List<String> records = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (String line : lines) {
            if (isRecordStart(line)) {
                if (!current.isEmpty()) {
                    records.add(current.toString());
                    current.setLength(0);
                }
                current.append(line);
            } else if (!current.isEmpty()) {
                current.append(" ").append(line);
            }
        }
        if (!current.isEmpty()) records.add(current.toString());
        return records;
    }

    private boolean isRecordStart(String line) {
        String lineUpper = line.toUpperCase();
        for (String skip : SKIP_RECORD_TOKENS) {
            if (lineUpper.startsWith(skip + " ") || lineUpper.startsWith(skip + "\t")) return true;
        }
        return line.matches("\\d{1,3}\\s+\\p{L}{2,}.*");
    }

    private boolean skipRecordIntentionally(String rawRecord) {
        String recordUpper = rawRecord.trim().toUpperCase();
        return SKIP_RECORD_TOKENS.stream()
                .anyMatch(token -> recordUpper.startsWith(token + " ") || recordUpper.startsWith(token + "\t"));
    }

    private ParsedAthleteRecord parseRecord(String rawRecord, String weightCategory, Sex sex, DocumentFormat format) {
        List<String> tokens = tokenize(rawRecord);
        int n = tokens.size();
        if (n < format.fixedTokensSuffix) return null;

        List<String> nameAndUniversityTokens = tokens.subList(1, n - format.fixedTokensSuffix);
        NameAndUniversity nameAndUniversity = extractNameAndUniversity(nameAndUniversityTokens, format.lastNameFirst);
        if (nameAndUniversity == null) return null;

        return ParsedAthleteRecord.builder()
                .place(Integer.parseInt(tokens.getFirst()))
                .firstName(nameAndUniversity.firstName())
                .lastName(nameAndUniversity.lastName())
                .university(nameAndUniversity.university())
                .bodyWeight(parseDouble(tokens.get(n - format.fixedTokensSuffix)))
                .weightCategory(weightCategory)
                .squat(format.bestSquat(tokens, n))
                .benchPress(format.bestBenchPress(tokens, n))
                .deadlift(format.bestDeadlift(tokens, n))
                .total(parseDouble(tokens.get(n - 3)))
                .ipfPoints(parseDouble(tokens.get(n - 2)))
                .universityType(UniversityType.fromString(tokens.get(n - 1)))
                .sex(sex)
                .build();
    }

    private List<String> tokenize(String line) {
        List<String> tokens = new ArrayList<>(List.of(line.trim().split("\\s+")));
        return truncateAfterUniversityType(tokens);
    }

    private List<String> truncateAfterUniversityType(List<String> tokens) {
        for (int i = tokens.size() - 1; i >= 0; i--) {
            if (UNIVERSITY_TYPES.contains(tokens.get(i).toUpperCase())) {
                return tokens.subList(0, i + 1);
            }
        }
        return tokens;
    }

    private NameAndUniversity extractNameAndUniversity(List<String> tokens, boolean lastNameFirst) {
        if (tokens.size() < 3) return null;

        int index = findAcademicPrefixLength(tokens);
        if (tokens.size() < index + 3) return null;

        String firstName, lastName;
        if (lastNameFirst) {
            lastName = tokens.get(index++);
            firstName = tokens.get(index++);
        } else {
            firstName = tokens.get(index++);
            lastName = tokens.get(index++);
        }
        String university = String.join(" ", tokens.subList(index, tokens.size()));
        return new NameAndUniversity(firstName, lastName, university);
    }

    private int findAcademicPrefixLength(List<String> tokens) {
        for (List<String> prefix : ACADEMIC_PREFIXES) {
            if (startWith(tokens, prefix)) return prefix.size();
        }
        return 0;
    }

    private boolean startWith(List<String> tokens, List<String> prefix) {
        if (tokens.size() < prefix.size()) return false;
        for (int i = 0; i < prefix.size(); i++) {
            if (!tokens.get(i).equalsIgnoreCase(prefix.get(i))) return false;
        }
        return true;
    }

    private void assignPlacesInUniversityType(List<ParsedAthleteRecord> records) {
        records.stream()
                .collect(Collectors.groupingBy(r -> r.getWeightCategory() + "|" + r.getUniversityType().name()))
                .values()
                .forEach(group -> {
                    group.sort(Comparator.comparingInt(ParsedAthleteRecord::getPlace));
                    for (int i = 0; i < group.size(); i++) {
                        group.get(i).setPlaceInUniversityType(i + 1);
                    }
                });
    }

    private static double parseDouble(String s) {
        return Double.parseDouble(s.replace(",", "."));
    }
}
