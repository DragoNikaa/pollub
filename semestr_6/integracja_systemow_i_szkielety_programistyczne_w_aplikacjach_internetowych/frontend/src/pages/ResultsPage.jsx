import {useEffect, useState} from "react";
import {getFilters, index} from "../services/resultsApi.js";
import ResultsChart from "../components/ResultsChart.jsx";

function ResultsPage() {

    const [filters, setFilters] = useState(null);

    const [sexes, setSexes] = useState([]);
    const [weightCategories, setWeightCategories] = useState([]);
    const [universityTypes, setUniversityTypes] = useState([]);

    const [nationalResults, setNationalResults] = useState([]);
    const [regionalResults, setRegionalResults] = useState([]);

    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchFilters = async () => {
            try {
                const response = await getFilters();

                setFilters(response);
                setError(null);
            } catch (err) {
                setError(err.message);
            }
        };
        fetchFilters();
    }, []);

    if (!filters) {
        return <p>Loading...</p>;
    }

    const updateArray = (setter, value, checked) => {
        setter(previous => checked ? [...previous, value] : previous.filter(item => item !== value));
    };

    const weightCategoriesComparator = (a, b) => {
        const aNumber = parseInt(a);
        const bNumber = parseInt(b);

        if (aNumber !== bNumber) {
            return aNumber - bNumber;
        }
        return a.localeCompare(b);
    };

    const handleSubmit = async event => {
        event.preventDefault();

        const selectedOrAll = (selected, all) => selected.length ? selected : all;

        const request = {
            sexes: selectedOrAll(sexes, ["FEMALE", "MALE"]),
            weightCategories: selectedOrAll(weightCategories, filters.weightCategories),
            universityTypes: selectedOrAll(universityTypes, filters.universityTypes)
        };

        try {
            const nationalResults = await index({competitionLevel: "NATIONAL", ...request});
            const regionalResults = await index({competitionLevel: "REGIONAL", ...request});

            setNationalResults(nationalResults);
            setRegionalResults(regionalResults);
            setError(null);
        } catch (err) {
            setError(err.message);
            setNationalResults(null);
            setRegionalResults(null);
        }
    };

    return (
        <>
            <form onSubmit={handleSubmit}>
                <fieldset>
                    <legend>Sex</legend>
                    <label>
                        <input type="checkbox" value="FEMALE"
                               onChange={e => updateArray(setSexes, "FEMALE", e.target.checked)}/>
                        Female
                    </label>
                    <label>
                        <input type="checkbox" value="MALE"
                               onChange={e => updateArray(setSexes, "MALE", e.target.checked)}/>
                        Male
                    </label>
                </fieldset>
                <fieldset>
                    <legend>Weight Categories</legend>
                    {filters.weightCategories.sort(weightCategoriesComparator).map(category => (
                        <label key={category}>
                            <input type="checkbox" value={category}
                                   onChange={e => updateArray(setWeightCategories, category, e.target.checked)}
                            />
                            {category}
                        </label>
                    ))}
                </fieldset>
                <fieldset>
                    <legend>University Types</legend>
                    {filters.universityTypes.sort().map(type => (
                        <label key={type}>
                            <input type="checkbox" value={type}
                                   onChange={e => updateArray(setUniversityTypes, type, e.target.checked)}
                            />
                            {type}
                        </label>))}
                </fieldset>
                <button>
                    Send request
                </button>
            </form>

            {nationalResults && regionalResults && (
                <ResultsChart nationalResults={nationalResults} regionalResults={regionalResults}/>
            )}

            {error && (
                <div>
                    <h3>Error:</h3>
                    <p>{error}</p>
                </div>
            )}
        </>
    );
}

export default ResultsPage;
