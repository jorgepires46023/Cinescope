import * as React from "react" 
import { Outlet } from "react-router-dom"

export function Homepage(){
    return(
        <div>
            <h1>Cinescope</h1>
            <Outlet/>
        </div>
    )
}