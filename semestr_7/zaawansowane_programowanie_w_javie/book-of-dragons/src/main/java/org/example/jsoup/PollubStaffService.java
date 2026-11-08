package org.example.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PollubStaffService {
    private static final String STAFF_URL = "https://cs.pollub.pl/staff/";

    private static final String DEPARTMENT_PREFIX = "Zakład";
    private static final String PHD_PREFIX = "dr";

    public static void main() {
        Document document;
        try {
            document = Jsoup.connect(STAFF_URL).get();
        } catch (IOException e) {
            System.err.println("Could not fetch URL: " + e.getMessage());
            return;
        }

        Map<String, List<String>> departmentEmployees = extractDepartmentEmployees(document);
        printDepartmentEmployees(departmentEmployees);
        printPhdHolders(departmentEmployees);
    }

    private static Map<String, List<String>> extractDepartmentEmployees(Document document) {
        Map<String, List<String>> departmentEmployees = new HashMap<>();

        for (Element h3 : document.select("h3")) {
            String department = h3.text();
            Element nextElement = h3.nextElementSibling();
            if (!department.startsWith(DEPARTMENT_PREFIX) || nextElement == null) continue;

            List<String> employees = nextElement.select("a").stream().map(Element::text).sorted().toList();
            departmentEmployees.put(department, employees);
        }

        return departmentEmployees;
    }

    private static void printDepartmentEmployees(Map<String, List<String>> departmentEmployees) {
        departmentEmployees.forEach((department, employees) -> {
            System.out.println(department + ":");
            for (int i = 0; i < employees.size(); i++) {
                System.out.println(i + 1 + ". " + employees.get(i));
            }
            System.out.println();
        });
    }

    private static void printPhdHolders(Map<String, List<String>> departmentEmployees) {
        System.out.println("PhD holders:");
        departmentEmployees.forEach((department, employees) -> employees.forEach(employee -> {
            if (employee.startsWith(PHD_PREFIX)) System.out.println("- " + employee);
        }));
    }
}