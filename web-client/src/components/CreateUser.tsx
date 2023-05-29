import * as React from "react"
import { createUser } from "../RequestsHelpers/UserRequestsHelper"
import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import { EMPTY_NEW_USER_INFO, NewUserInfo } from "../utils/Types"

export function CreateUser() {

    const [newUser, setNewUser] = useState<NewUserInfo>(EMPTY_NEW_USER_INFO)

    const [errMsg, setErrMsg] = useState(true);
    const navigate = useNavigate()

    useEffect(() => { }, [errMsg])

    async function handleCreateUser(ev: React.FormEvent<HTMLFormElement>) {
        ev.preventDefault()
        console.log("hello")
        createUser(newUser.username, newUser.email, newUser.password)
            .then(res => {
                if (res.status == 201) {
                    navigate("/login")
                } else {
                    throw new Error("Failed Creating New User")
                }
            }
            )
            .catch(() => setErrMsg(false))

    }

    function handleChange(ev: React.FormEvent<HTMLInputElement>) {
        const id = ev.currentTarget.id
        setNewUser({ ...newUser, [id]: ev.currentTarget.value })
    }
    //TODO criar algo para mostrar erros
    return (
        <div >
            <div className="centerDiv">
                <div className="titleDiv">
                    <h1 className="titleClass">Create a New User</h1>
                </div>
                <form className="cardForm" onSubmit={handleCreateUser}>
                    <div className="inputDiv">
                        <input type="text" id="username" placeholder="Name" onChange={handleChange} value={newUser.username} className="nameInput" /><br />
                    </div>
                    
                    <div className="inputDiv">
                        <input type="email" id="email" placeholder="E-mail" onChange={handleChange} value={newUser.email} className="emailInput" /><br />
                    </div>

                    <div className="inputDiv">
                        <input type="password" id="password" placeholder="Password" onChange={handleChange} value={newUser.password} className="passwdInput" /><br></br><br />
                    </div>
                    <input type="submit" value="Register Now" className="submitButton" />
                </form>
            </div>
        </div>
    )
}