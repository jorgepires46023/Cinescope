
const DOMAIN_URL = "http://localhost:8080/api"

export async function createUser(name: string, email: string, password: string) {
    return await fetch(`${DOMAIN_URL}/users`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            email: email,
            password: password
        })
    })
        .then(res => res.json())
}

export async function getUserById(userId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/users/${userId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    })
        .then(res => res.json())
}

export async function editUser(userId: number, name: string, email: string, password: string, token: string) {
    return await fetch(`${DOMAIN_URL}/users/${userId}/edit`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
            name: name,
            email: email,
            password: password
        })
    })
        .then(res => res.json())
}

export async function removeUser(userId: number, token: string) {
    return await fetch(`${DOMAIN_URL}/users/${userId}/edit`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    })
        .then(res => res.json())
}

export async function login(email: string, password: string) {
    return await fetch(`${DOMAIN_URL}/login`, {
        method: "POST",
        body: JSON.stringify({
            email: email,
            password: password
        }),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(res => {
            if (res.status == 200) {
                return res.json()
            } else {
                throw new Error("Failed Login");
            }
        })
        .catch(() => {
            return undefined
        })
}
