import * as React from "react"
import { Link } from "react-router-dom"
import { SearchBar } from "./SearchBar"
import { getCookie } from "../../utils/Tools"
 
export function NavBar() {
   
    let loggedIn = getCookie('userToken')

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
                {/*Links quando User NÃO está com Login Feito*/}
                { !loggedIn && <Link className='iselLink' to="/login">Login/Create User</Link> }
                {/*Links quando User está com Login Feito */}
                { loggedIn && <Link className='iselLink' to={"/lists"}>Lists</Link>}
                { loggedIn && <Link className='iselLink' to="/profile">Profile</Link> }
                { loggedIn && <Link className='iselLink' to="/logout">Logout</Link>}
                
            </div>
        </div>
    )

}