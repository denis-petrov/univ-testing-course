import fetch from "node-fetch"
import settings from "./settings.js"

const postImposter = body => {
    const url = `http://127.0.0.1:${settings.port}/imposters`

    return fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(body)
    })
}

export default {postImposter}