import {buildQueryString, fetchApi} from "../api";

export async function index(filters) {
    const queryString = buildQueryString(filters);
    return fetchApi(`/api/results?${queryString}`);
}

export async function store(resultsData) {
    return fetchApi("/api/results", {
        method: "POST", headers: {
            "Content-Type": "application/json",
        }, body: JSON.stringify(resultsData),
    });
}

export async function getFilters() {
    return fetchApi("/api/results/filters");
}
