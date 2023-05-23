import * as React from "react"
import { NavBar } from "./navbar/Navbar"
import { createUser } from "../RequestsHelpers/UserRequestsHelper"
import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'

export function CreateUser() {

    const [newUser, setNewUser] = useState({
        username: "",
        email: "",
        password: "",
    })

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
            <NavBar />
            <div className="centerDiv">
                <div className="titleDiv">
                     <h1 className="titleClass">Create a New User</h1>
                </div>
                <form className="cardform" onSubmit={handleCreateUser}>
                    {/*<label htmlFor="username" className="label"> Name: </label>*/}
                    <div className="inputDiv">
                        <input type="text" id="username" placeholder="Name" onChange={handleChange} value={newUser.username} className="nameInput" /><br />
                    </div>
                    {/*<label htmlFor="email" className="label"> E-mail: </label>*/}
                    <div className="inputDiv">
                        <input type="email" id="email" placeholder="E-mail" onChange={handleChange} value={newUser.email} className="emailInput" /><br />
                    </div>
                    {/*<label htmlFor="password" className="label"> Password: </label>*/}
                    <div className="inputDiv">
                        <input type="password" id="password" placeholder="Password" onChange={handleChange} value={newUser.password} className="passwdInput" /><br></br><br />
                    </div>
                    <input type="submit" value="Register Now" className="submitButton" />
                </form>
            </div>
        </div>
    )
}