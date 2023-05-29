import * as React from "react";
import { Outlet, useNavigate } from "react-router-dom";


export function Movies() {
    const navigate = useNavigate()
    React.useEffect(() => {
         navigate("/movies/603692")
    }, [])
   
    return(<div>
        <Outlet></Outlet>
    </div>)
}