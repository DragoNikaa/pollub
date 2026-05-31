const BASE_URL = "http://localhost:8080";

export async function fetchApi(path, options = {}) {
    const response = await fetch(`${BASE_URL}${path}`, options);

    if (!response.ok) {
        throw new Error("API error occurred");
    }
    return response.json();
}

export function buildQueryString(paramsObject) {
    const params = new URLSearchParams();

    Object.entries(paramsObject).forEach(([key, value]) => {
        if (Array.isArray(value)) {
            value.forEach(item => params.append(key, item));
        } else {
            params.append(key, value);
        }
    });

    return params.toString();
}
