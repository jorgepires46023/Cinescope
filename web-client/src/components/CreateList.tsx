import * as React from "react"
import { useNavigate } from 'react-router-dom'
import { useState, useEffect, useContext } from 'react'
import { createMoviesList } from "../RequestsHelpers/MoviesRequestsHelper"
import { UserContext } from "./UserProvider"
import { NewListInfo, EMPTY_NEW_LIST_INFO } from "../utils/Types"
import { createSeriesList } from "../RequestsHelpers/SeriesRequestsHelper"

export function CreateList() {

    const userInfo = useContext(UserContext)

    const [newList, setNewList] = useState<NewListInfo>(EMPTY_NEW_LIST_INFO)

    const [errMsg, setErrMsg] = useState<boolean>(false);

    const navigate = useNavigate()


    //useEffect(() => { }, [errMsg])

    async function handleCreateList(ev: React.FormEvent<HTMLFormElement>) {
        ev.preventDefault()
        
        if (newList.type == "Movies") {
            try {
                await createMoviesList(newList.name, userInfo.token)
            } catch (error) {
                setErrMsg(true)
                return
            }
        }

        if (newList.type == "Series") {
            try {
                await createSeriesList(newList.name, userInfo.token)
            } catch (error) {
                setErrMsg(true)
                return
            }
        }
        navigate("/lists")
    }

    function handleChange(ev: React.FormEvent<HTMLInputElement>) {
        const id = ev.currentTarget.id
        setNewList({ ...newList, [id]: ev.currentTarget.value })
    }

    function handleChangeSelect(ev: React.FormEvent<HTMLSelectElement>) {
        setNewList({ ...newList, type: ev.currentTarget.value })
    }

    return (
        <div >
            <div className="centerDiv">
                <div className="titleDiv">
                    <h1 className="titleClass">Create a New List</h1>
                </div>
                <form id="cardForm" className="cardForm" onSubmit={handleCreateList}>

                    <div className="selectDiv">
                        <label className="labelSelect" htmlFor="type">Type of List: </label>
                        <select name="type" id="type" form="cardForm" onChange={handleChangeSelect} className="selectForm">
                            <option value="Movie" selected> Movies </option>
                            <option value="Series"> Series </option>
                        </select>
                    </div>

                    <div className="inputDiv">
                        <input required type="text" id="name" placeholder="Name..." onChange={handleChange} value={newList.name} className="nameInput" /><br />
                    </div>
                    {errMsg && <div className="errorDiv">
                        <h6 className='errorMsg'>Something when wrong. Please try again.</h6>
                        <button className="errorButton" onClick={() => setErrMsg(false)}> X </button>
                    </div>}

                    <input type="submit" value="Create List" className="submitButton" />
                </form>
            </div>
        </div>
    )
}