import * as React from "react"
import { createUser } from "../RequestsHelpers/UserRequestsHelper"
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { EMPTY_NEW_USER_INFO, NewUserInfo } from "../utils/Types"
import { NavBar } from "./navbar/Navbar"

export function CreateUser() {

    const [newUser, setNewUser] = useState<NewUserInfo>(EMPTY_NEW_USER_INFO)

    const [errMsg, setErrMsg] = useState(false);

    const navigate = useNavigate()

    async function handleCreateUser(ev: React.FormEvent<HTMLFormElement>) {
        ev.preventDefault()

        createUser(newUser.username, newUser.email, newUser.password)
            .then(res => {
                if (res.status == 201) {
                    navigate("/login")
                } else {
                    throw new Error("Failed Creating New User")
                }
            }
            )
            .catch(() => setErrMsg(true))

    }

    function handleChange(ev: React.FormEvent<HTMLInputElement>) {
        const id = ev.currentTarget.id
        setNewUser({ ...newUser, [id]: ev.currentTarget.value })
    }
    
    return (
        <div >
            <NavBar/>
            <div className="centerDiv">
                <div className="titleDiv">
                    <h1 className="titleClass">Create a New User</h1>
                </div>
                <form className="cardForm" onSubmit={handleCreateUser}>
                    <div className="inputDiv">
                        <input required type="text" id="username" placeholder="Name" onChange={handleChange} value={newUser.username} className="nameInput" /><br />
                    </div>
                    
                    <div className="inputDiv">
                        <input required type="email" id="email" placeholder="E-mail" onChange={handleChange} value={newUser.email} className="emailInput" /><br />
                    </div>

                    <div className="inputDiv">
                        <input required type="password" id="password" placeholder="Password" onChange={handleChange} value={newUser.password} className="passwdInput" /><br></br><br />
                    </div>
                    {errMsg && <div className="errorDiv">
                        <h6 className='errorMsg'>Something when wrong. Please try again.</h6>
                        <button className="errorButton" onClick={() => setErrMsg(false)}> X </button>
                    </div>}
                    <input type="submit" value="Register Now" className="submitButton" />
                </form>
            </div>
        </div>
    )
}