import { DOMAIN_URL } from "../utils/Tools"

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

export async function getUserById(userId: number) {
    return await fetch(`${DOMAIN_URL}/users/${userId}`, {
        method: "GET",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        
        }
    })
        .then(res => res.json())
}

export async function editUser(userId: number, name: string, email: string, password: string) {
    return await fetch(`${DOMAIN_URL}/users/${userId}/edit`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        credentials: 'include',
        body: JSON.stringify({
            name: name,
            email: email,
            password: password
        })
    })
        .then(res => res.json())
}

export async function removeUser(userId: number) {
    return await fetch(`${DOMAIN_URL}/users/${userId}/edit`, {
        method: "PUT",
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
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

