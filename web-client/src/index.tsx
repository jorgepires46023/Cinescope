import * as React from 'react'
import { createRoot } from 'react-dom/client'
import { App } from './router'

//TODO: check if this file remains like this
const htmlElement = document.getElementById("root")

const root = createRoot(htmlElement)

root.render(
    <App />
)