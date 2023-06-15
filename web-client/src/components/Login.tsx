import * as React from "react"
import { Link, useNavigate } from "react-router-dom"
import { login } from "../RequestsHelpers/UserRequestsHelper"
import { useContext, useState } from "react"
import { UserContext } from "./UserProvider"
import { EMPTY_LOGIN_INFO, LoginInfo } from "../utils/Types"


export function Login() {
    const userInfo = useContext(UserContext)

    const [User, setUser] = useState<LoginInfo>(EMPTY_LOGIN_INFO)

    const [errMsg, setErrMsg] = useState(false);

    const navigate = useNavigate()

    //useEffect(() => { }, [errMsg])  //If failed login we show message error

    async function handleLogin(ev: React.FormEvent<HTMLFormElement>) {
        ev.preventDefault()

        const user = await login(User.email, User.password)

        if (user != undefined) {
            navigate(-1)
        } else {
            setErrMsg(true)
        }
    }

    function handleChange(ev: React.FormEvent<HTMLInputElement>) {
        const id = ev.currentTarget.id
        setUser({ ...User, [id]: ev.currentTarget.value })
    }

    return (
        <div>
            <div className="centerDiv">
                <div className="titleDiv">
                    <h1 className="titleClass">Login</h1>
                </div>
                <form className="cardForm" onSubmit={handleLogin}>
                    <div className="inputDiv">
                        <input required type="text" id="email" placeholder="E-mail" className="emailInput" onChange={handleChange} value={User.email} /><br />
                    </div>
                    <div className="inputDiv">
                        <input required type="password" id="password" placeholder="Password" className="passwdInput" onChange={handleChange} value={User.password} /><br></br><br />
                    </div>
                    {errMsg && <div className="errorDiv">
                        <h6 className='errorMsg'>Something when wrong. Please try again.</h6>
                        <button className="errorButton" onClick={() => setErrMsg(false)}> X </button>
                    </div>}
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