const API_URL = 'http://localhost:8080/api';

function getToken() {
    return localStorage.getItem('token');
}

function getHeaders() {
    return {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getToken()}`
    };
}

function cerrarSesion() {
    localStorage.removeItem('token');
    localStorage.removeItem('nombre');
    localStorage.removeItem('rol');
    window.location.href = '/frontend/index.html';
}

function verificarAuth() {
    if (!getToken()) {
        window.location.href = '/frontend/index.html';
    }
}

async function apiGet(endpoint) {
    const response = await fetch(`${API_URL}${endpoint}`, {
        method: 'GET',
        headers: getHeaders()
    });
    if (response.status === 401) {
        cerrarSesion();
        return;
    }
    return response.json();
}

async function apiPost(endpoint, body) {
    const response = await fetch(`${API_URL}${endpoint}`, {
        method: 'POST',
        headers: getHeaders(),
        body: JSON.stringify(body)
    });
    if (response.status === 401) {
        cerrarSesion();
        return;
    }
    return response.json();
}

async function apiPut(endpoint, body) {
    const response = await fetch(`${API_URL}${endpoint}`, {
        method: 'PUT',
        headers: getHeaders(),
        body: JSON.stringify(body)
    });
    if (response.status === 401) {
        cerrarSesion();
        return;
    }
    return response.json();
}

async function apiPatch(endpoint) {
    const response = await fetch(`${API_URL}${endpoint}`, {
        method: 'PATCH',
        headers: getHeaders()
    });
    if (response.status === 401) {
        cerrarSesion();
        return;
    }
    return response;
}

async function apiDelete(endpoint) {
    const response = await fetch(`${API_URL}${endpoint}`, {
        method: 'DELETE',
        headers: getHeaders()
    });
    if (response.status === 401) {
        cerrarSesion();
        return;
    }
    return response;
}