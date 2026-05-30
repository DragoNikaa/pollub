const BASE_URL = "http://localhost:8080";

export async function fetchApi(path, options = {}) {
    const response = await fetch(`${BASE_URL}${path}`, options);

    if (!response.ok) {
        throw new Error("API error occurred");
    }
    return response.json();
}
