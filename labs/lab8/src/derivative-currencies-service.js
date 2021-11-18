import mbHelper from "./mountebank-helper.js"
import settings from "./settings.js"

const addService = () => {
    const USD = {
        "USD-RUB": {
            "date": "Nov 13, 2021, 13:13 UTC",
            "value": "72.8717"
        },
        "USD-CHF": {
            "date": "Nov 13, 2021, 13:17 UTC",
            "value": "0.921172"
        }
    }

    const EUR = {
        "EUR-RUB": {
            "date": "Nov 13, 2021, 13:14 UTC",
            "value": "83.3884"
        },
        "EUR-CHF": {
            "date": "Nov 13, 2021, 13:17 UTC",
            "value": "1.05411"
        }
    }

    const CHF = {
        "CHF-RUB": {
            "date": "Nov 13, 2021, 13:14 UTC",
            "value": "79.1076"
        }
    }
    const allCurrencies = {
        USD, EUR, CHF
    }

    const stubs = [
        {
            predicates: [
                {
                    equals: {
                        method: "GET",
                        "path": "/",
                    }
                }
            ],
            responses: [
                {
                    is: {
                        statusCode: 200,
                        headers: {
                            "Content-Type": "application/json",
                            "Location": "http://localhost:5001/",
                        },
                        body: JSON.stringify(allCurrencies)
                    },
                },
            ]
        },
        {
            predicates: [
                {
                    equals: {
                        method: "GET",
                        "path": "/USD",
                    }
                }
            ],
            responses: [
                {
                    is: {
                        statusCode: 200,
                        headers: {
                            "Content-Type": "application/json",
                            "Location": "http://localhost:5001/USD",
                        },
                        body: JSON.stringify(USD)
                    },
                },
            ]
        },
        {
            predicates: [
                {
                    equals: {
                        method: "GET",
                        "path": "/EUR",
                    }
                }
            ],
            responses: [
                {
                    is: {
                        statusCode: 200,
                        headers: {
                            "Content-Type": "application/json",
                            "Location": "http://localhost:5001/EUR",
                        },
                        body: JSON.stringify(EUR)
                    },
                },
            ]
        },
        {
            predicates: [
                {
                    equals: {
                        method: "GET",
                        "path": "/CHF",
                    }
                }
            ],
            responses: [
                {
                    is: {
                        statusCode: 200,
                        headers: {
                            "Content-Type": "application/json",
                            "Location": "http://localhost:5001/CHF",
                        },
                        body: JSON.stringify(CHF)
                    },
                },
            ]
        }
    ]

    const imposter = {
        port: settings.derivative_currencies_service_port,
        protocol: 'http',
        stubs: stubs
    }

    return mbHelper.postImposter(imposter)
}

export default {addService}