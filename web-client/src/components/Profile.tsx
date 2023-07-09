import * as React from "react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { editUser, getUserByToken } from "../RequestsHelpers/UserRequestsHelper";
import { User, EMPTY_USER } from "../utils/Types"
import { getCookie } from "../utils/Tools";
import { NavBar } from "./navbar/Navbar";

export function Profile() {

    const navigate = useNavigate()

    const userToken = getCookie("userToken")

    const [User, setUser] = useState<User>(EMPTY_USER)

    async function getUserInfo() {
        const user = await getUserByToken()
        setUser(user)
    }

    useEffect(() => {
        if (!userToken) {
            navigate("/login")
        } else {
            getUserInfo()
        }
    }, [])

    return (
        <div className='firstDiv'>
            <NavBar />
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}