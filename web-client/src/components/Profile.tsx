import * as React from "react";
import { useContext, useEffect, useState } from "react";
import { UserContext } from "./UserProvider";
import { useNavigate } from "react-router-dom";
import { getUserById } from "../RequestsHelpers/UserRequestsHelper";
import { User, EMPTY_USER } from "../utils/Types"

export function Profile() {
    const userInfo = useContext(UserContext)
    const navigate = useNavigate()

    const [User, setUser] = useState<User>(EMPTY_USER)

    if (!userInfo.token || !userInfo.userId) {
        navigate("/login")
    }

    async function getUserInfo() {
        const user = await getUserById(userInfo.userId, userInfo.token)
        setUser(user)
    }

    useEffect(() => {
        getUserInfo()
    }, [])

    return (
        <div className="pageDiv">

            <div className="profileDiv">
                <div className="profileDivTitle">
                    <h1 className="titleClass">Profile</h1>
                </div>
                <div className="profileCard">
                    <h1>{User.name}</h1>
                    <h3>E-mail: {User.email}</h3>
                </div>
            </div>


        </div>
    )
}