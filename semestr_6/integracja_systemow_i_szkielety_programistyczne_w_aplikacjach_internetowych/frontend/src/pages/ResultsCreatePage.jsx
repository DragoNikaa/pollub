import {useState} from "react";
import {store} from "../services/resultsApi.js";

function ResultsCreatePage() {

    const currentYear = new Date().getFullYear();

    const [url, setUrl] = useState("");
    const [year, setYear] = useState(currentYear);
    const [competitionLevel, setCompetitionLevel] = useState("");
    const [sex, setSex] = useState("");

    const [response, setResponse] = useState(null);
    const [error, setError] = useState(null);

    const handleStoreResults = async (event) => {
        event.preventDefault();

        try {
            const resultsData = {url, year, competitionLevel, sex: competitionLevel === "NATIONAL" ? sex : null};
            const storedResultsStats = await store(resultsData);

            setResponse(storedResultsStats);
            setError(null);
        } catch (err) {
            setError(err.message);
            setResponse(null);
        }
    };

    return (
        <>
            <form onSubmit={handleStoreResults}>
                <label>
                    URL
                    <input type="url" name="url" placeholder="https://azs.pl/wyniki.pdf" required
                           onChange={e => setUrl(e.target.value)}
                    />
                </label>
                <label>
                    Year
                    <input type="number" name="year" placeholder={currentYear.toString()}
                           min="2023" max={currentYear} required
                           onChange={e => setYear(Number(e.target.value))}
                    />
                </label>
                <fieldset>
                    <legend>Competition Level</legend>
                    <label>
                        <input type="radio" name="competitionLevel" value="NATIONAL" required
                               onChange={e => setCompetitionLevel(e.target.value)}
                        />
                        National
                    </label>
                    <label>
                        <input type="radio" name="competitionLevel" value="REGIONAL"
                               onChange={e => setCompetitionLevel(e.target.value)}
                        />
                        Regional
                    </label>
                </fieldset>
                <fieldset disabled={competitionLevel === "REGIONAL"}>
                    <legend>Sex</legend>
                    <label>
                        <input type="radio" name="sex" value="FEMALE" required
                               onChange={e => setSex(e.target.value)}/>
                        Female
                    </label>
                    <label>
                        <input type="radio" name="sex" value="MALE"
                               onChange={e => setSex(e.target.value)}/>
                        Male
                    </label>
                </fieldset>
                <button>
                    Import results
                </button>
            </form>
            <div>
                {response && (
                    <div>
                        <h3>Import completed successfully:</h3>
                        <ul>
                            <li>New results imported: {response.created}</li>
                            <li>Existing results updated: {response.updated}</li>
                        </ul>
                    </div>
                )}

                {error && (
                    <div>
                        <h3>Error:</h3>
                        <p>{error}</p>
                    </div>
                )}
            </div>
        </>
    );
}

export default ResultsCreatePage;
