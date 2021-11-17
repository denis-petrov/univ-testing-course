import mbHelper from "./mountebank-helper.js"
import settings from "./settings.js"

const addService = () => {
    const response = {
        "USD-RUB": {
            "date": "Nov 13, 2021, 13:13 UTC",
            "value": "72.8717"
        },
        "EUR-RUB": {
            "date": "Nov 13, 2021, 13:14 UTC",
            "value": "83.3884"
        },
        "CHF-RUB": {
            "date": "Nov 13, 2021, 13:14 UTC",
            "value": "79.1076"
        },
        "USD-CHF": {
            "date": "Nov 13, 2021, 13:17 UTC",
            "value": "0.921172"
        },
        "EUR-CHF": {
            "date": "Nov 13, 2021, 13:17 UTC",
            "value": "1.05411"
        }
    }

    const stubs = [
        {
            predicates: [{
                equals: {
                    method: "GET",
                    "path": "/"
                }
            }],
            responses: [
                {
                    is: {
                        statusCode: 200,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(response)
                    }
                }
            ]
        }
    ];

    const imposter = {
        port: settings.derivative_currencies_service_port,
        protocol: 'http',
        stubs: stubs
    }

    return mbHelper.postImposter(imposter)
}

export default {addService}