import * as React from "react";
import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { UserContext } from "./UserProvider";

export function Logout(){
    const navigate = useNavigate()
    const userInfo = useContext(UserContext)
    userInfo.setToken(null)
    userInfo.setUserId(null)
    navigate("/home")
    return(
        <h1>Goodbye!</h1>
    )
}

