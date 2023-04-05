import * as React from "react"
import { Link } from "react-router-dom"

export function NavBar() { 
    return (
        <div className='iselnavbar'>
            <div className="iselGroupLeft">
                <Link className='iselLink' to="/cinescope">Cinescope</Link>
                <Link className='iselLink' to="/cinescope/tasks">Tasks</Link>
                <Link className='iselLink' to="/cinescope/messages">Messages</Link>
            </div>
            <div className="iselGroupRight">
                { /* Links quando User NÃO está com Login Feito */ }
                { /* !loggedIn && <Link className='iselLink' to="/login">Login/Create User</Link>*/ }
                { /* Links quando User está com Login Feito */}
                { /* loggedIn && <Link className='iselLink' to={"/user/" + userInfo.userId}>Perfil</Link>*/ }
                { /*loggedIn && <Link className='iselLink' to="/logout">Logout</Link>*/ }
                
            </div>
        </div>
    )

}