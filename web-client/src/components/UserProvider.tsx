import * as React from 'react'
import { useContext, useState } from 'react'

type UserCtxType = {
    token: string | null,
    setToken?: (str:string) => void,
    userId: number | null,
    setUserId?: (n:number) => void
}

export const UserContext = React.createContext<UserCtxType>({
    token: null,
    userId: null,
})

export function UserProvider({children}: { children: React.ReactNode }){
    const [token, setToken] = useState(null)
    const [userId, setUserId] = useState(null)

    return(
        <UserContext.Provider value={{token, setToken, userId, setUserId}}>
            {children}
        </UserContext.Provider>
    )
}
