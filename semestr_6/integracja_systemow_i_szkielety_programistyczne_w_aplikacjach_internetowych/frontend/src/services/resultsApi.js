import {fetchApi} from "../api";

export async function store(resultsData) {
    return fetchApi("/api/results", {
        method: "POST", headers: {
            "Content-Type": "application/json",
        }, body: JSON.stringify(resultsData),
    });
}
