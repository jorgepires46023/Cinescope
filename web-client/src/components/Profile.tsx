import * as React from "react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { editUser, getUserById } from "../RequestsHelpers/UserRequestsHelper";
import { User, EMPTY_USER } from "../utils/Types"
import { getCookie } from "../utils/Tools";

export function Profile() {

    const navigate = useNavigate()

    const userToken = getCookie("userToken")

    const [User, setUser] = useState<User>(EMPTY_USER)

    const [updateUser, setUpdateUser] = useState<User>(EMPTY_USER)

    const [errMsg, setErrMsg] = useState(false);

    async function getUserInfo() {
        const user = await getUserById(null) //TODO alterar para user Token
        setUser(user)
        setUpdateUser(user)
    }

    async function handleEditUser(ev: React.FormEvent<HTMLFormElement>) {

        const user = await editUser(null, updateUser.name, updateUser.email, updateUser.password)

        if (user != undefined) {
            navigate(-1)
        } else {
            setErrMsg(true)
        }
    }

    function handleChange(ev: React.FormEvent<HTMLInputElement>) {
        const id = ev.currentTarget.id
        setUpdateUser({ ...updateUser, [id]: ev.currentTarget.value })
    }

    function deleteUser() {

    }

    useEffect(() => {
        if (!userToken) {
            navigate("/login")
        } else {
            getUserInfo()
        }
    }, [])

    return (
        <div className="pageDiv">
            <div className="userProfileDiv">
                <div className="profileDivTitle">
                    <h1 className="titleClass">Profile</h1>
                </div>
                <div className="userInfoDiv">
                    <div className="profileDiv">
                        <div className="profileCard">
                            <h2 className="userTitle">User Info</h2>
                            <h1>{User.name}</h1>
                            <h3>E-mail: {User.email}</h3>
                            <button className="deleteUserButton" onClick={() => deleteUser()}>Delete User</button>
                        </div>
                    </div>
                    <div className="editUserDiv">
                        <form className="editUsercardForm" onSubmit={handleEditUser}>
                            <h2 className="userTitle">Edit User</h2>
                            <div className="inputDiv">
                                <input required type="text" id="name" placeholder="Name" className="emailInput" onChange={handleChange} value={updateUser.email} /><br />
                            </div>
                            <div className="inputDiv">
                                <input required type="text" id="email" placeholder="E-mail" className="emailInput" onChange={handleChange} value={updateUser.email} /><br />
                            </div>
                            <div className="inputDiv">
                                <input required type="password" id="password" placeholder="Password" className="passwdInput" onChange={handleChange} value={updateUser.password} /><br></br><br />
                            </div>
                            {errMsg && <div className="errorDiv">
                                <h6 className='errorMsg'>Something when wrong. Please try again.</h6>
                                <button className="errorButton" onClick={() => setErrMsg(false)}> X </button>
                            </div>}
                            <input type="submit" className="submitButton" value="Edit User" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}