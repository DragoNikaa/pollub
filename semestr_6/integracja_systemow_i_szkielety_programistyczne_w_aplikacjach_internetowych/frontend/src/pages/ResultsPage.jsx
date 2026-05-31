import {useEffect, useState} from "react";
import {getFilters, index} from "../services/resultsApi.js";

function ResultsPage() {

    const [filters, setFilters] = useState(null);

    const [sexes, setSexes] = useState([]);
    const [weightCategories, setWeightCategories] = useState([]);
    const [universityTypes, setUniversityTypes] = useState([]);

    const [summaryResults, setSummaryResults] = useState([]);

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

    const handleSubmit = async event => {
        event.preventDefault();

        const selectedOrAll = (selected, all) => selected.length ? selected : all;

        const request = {
            sexes: selectedOrAll(sexes, ["FEMALE", "MALE"]),
            weightCategories: selectedOrAll(weightCategories, filters.weightCategories),
            universityTypes: selectedOrAll(universityTypes, filters.universityTypes)
        };

        try {
            const response = await index({competitionLevel: "NATIONAL", ...request});

            setSummaryResults(response);
            setError(null);
        } catch (err) {
            setError(err.message);
            setSummaryResults([]);
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
                    {filters.weightCategories.map(category => (
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
                    {filters.universityTypes.map(type => (
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

            {summaryResults.length > 0 && (
                <div>
                    <h3>Summary Results:</h3>
                    <pre>
                        {JSON.stringify(summaryResults, null, 2)}
                    </pre>
                </div>
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
