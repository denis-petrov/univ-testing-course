import mb from "mountebank"
import settings from "./settings.js"
import derivativeService from "./derivative-currencies-service.js"

const mbServerInstance = mb.create({
    port: settings.port,
    pidfile: '../mb.pid',
    logfile: '../mb.log',
    protofile: '../protofile.json',
    ipWhitelist: ['*']
})

mbServerInstance.then(() => derivativeService.addService().then(r => r))