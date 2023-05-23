import * as React from "react"
import { NavBar } from "./navbar/Navbar"
import { Link, useNavigate } from "react-router-dom"
import { login } from "../RequestsHelpers/UserRequestsHelper"
import { useContext, useState } from "react"

export function Login() {
    //const userInfo = useContext(UserContext)
    const [User, setUser] = useState({
        email: "",
        password: "",
    })
    const [errMsg, setErrMsg] = useState(true);
    const navigate = useNavigate()
    
    //useEffect(() => { }, [errMsg])  //If failed login we show message error

    async function handleLogin(ev: React.FormEvent<HTMLFormElement>) {
        ev.preventDefault()

        const user = login(User.email, User.password)

        if (user != undefined) {
            //userInfo.setToken(user.token)
            //userInfo.setUserId(user.id)
            console.log("SECCESS")
            navigate("/")
        } else {
            setErrMsg(false)
        }

    }
    
    function handleChange(ev: React.FormEvent<HTMLInputElement>) {
        const id = ev.currentTarget.id
        setUser({ ...User, [id]: ev.currentTarget.value })
    }

    return (

        <div>
            <NavBar />
            <div className="centerDiv">
                <div className="titleDiv">
                    <h1 className="titleClass">Login</h1>
                </div>
                <form className="cardform" onSubmit={handleLogin}>
                    {/*<label htmlFor="email"> E-mail</label>*/}
                    <div className="inputDiv">
                        <input type="text" id="email" placeholder="E-mail" className="emailInput" onChange={handleChange} value={User.email}/><br />
                    </div>
                    {/*<label htmlFor="password"> Password</label>*/}
                    <div className="inputDiv">
                        <input type="password" id="password" placeholder="Password" className="passwdInput" onChange={handleChange} value={User.password}/><br></br><br />
                    </div>
                    <input type="submit" className="submitButton" value="Login" />
                    <div className="hrDiv">
                        <hr className="hrClass" />
                    </div>
                    <div className='createUserDiv'>
                        <Link className='createUserLink' to="/createuser">Create User</Link>
                    </div>
                    <div className="hrDiv">
                        <hr className="hrClass" />
                    </div>
                    <div>
                        <h1>OpenId</h1>
                    </div>
                </form>
            </div>
        </div>
    )
}