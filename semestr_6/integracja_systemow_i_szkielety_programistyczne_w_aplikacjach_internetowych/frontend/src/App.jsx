import './App.css'
import {createResults} from "./services/resultsApi.js";
import {useState} from "react";
import Button from "./components/Button.jsx";

function App() {

    const [response, setResponse] = useState(null);
    const [error, setError] = useState(null);

    const handleCreateResults = async () => {
        try {
            const resultsData = {
                url: "https://azs.pl/Uploads/Dokumenty/amp/2526/wyniki/2526_trojboj_k_wyniki.pdf",
                year: 2026,
                competitionLevel: "NATIONAL",
                sex: "FEMALE"
            };
            const createdResultsStats = await createResults(resultsData);

            setResponse(createdResultsStats);
            setError(null);
        } catch (err) {
            setError(err.message);
            setResponse(null);
        }
    };

    return (
        <div>
            <Button text={"Import sample results"} onClick={handleCreateResults}></Button>

            {response && (
                <div>
                    <h3>Response from backend:</h3>
                    <pre>{JSON.stringify(response)}</pre>
                </div>
            )}

            {error && (
                <div>
                    <h3>Error:</h3>
                    <p>{error}</p>
                </div>
            )}
        </div>
    );
}

export default App
