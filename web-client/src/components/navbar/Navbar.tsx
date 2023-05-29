import * as React from "react"
import { Link } from "react-router-dom"
import { SearchBar } from "./SearchBar"
import { useContext } from "react"
import { UserContext } from "../UserProvider"
//import { DarkModeToggleButton } from "./DarkModeToggleButton"

export function NavBar() {
    const userInfo = useContext(UserContext)
    const loggedIn: boolean = userInfo.token != null
    return (
        <div className='iselnavbar'>
            <div className="iselGroupLeft">
                <Link className='iselLinkLogo' to="/home"><img src="cinescope logo.png" alt="Cinescope" className="logo"/></Link>
                <Link className='iselLink' to="/series">Series</Link>
                <Link className='iselLink' to="/movies">Movies</Link>
            </div>
            <div className="iselGroupCenter">
                <SearchBar />
            </div>
            <div className="iselGroupRight">
                {/*<DarkModeToggleButton />}
                {/*Links quando User NÃO está com Login Feito*/}
                { !loggedIn && <Link className='iselLink' to="/login">Login/Create User</Link> }
                {/*Links quando User está com Login Feito */}
                { loggedIn && <Link className='iselLink' to={"/user/" + userInfo.userId}>Lists</Link>}
                { loggedIn && <Link className='iselLink' to="/profile">Profile</Link> }
                { loggedIn && <Link className='iselLink' to="/logout">Logout</Link>}
                
            </div>
        </div>
    )

}