import * as React from "react";
import { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { UserContext } from "./UserProvider";
import { removeCookie } from "../utils/Tools";

export function Logout() {
    const navigate = useNavigate()

    useEffect(() => {
        removeCookie('userToken')
        navigate("/home")
    }, [])

    return (
        <h1>Goodbye!</h1>
    )
}

