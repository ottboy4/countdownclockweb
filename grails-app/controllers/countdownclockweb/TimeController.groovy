package countdownclockweb

import grails.converters.JSON
import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

class TimeController {

    def http = new HTTPBuilder('https://api.planningcenteronline.com')

    def config = [
            types: [
                    'Weekend Service': [
                            'Bumper',
                            'Prayer'
                    ]
            ]
    ]

    TimeController() {
        http.auth.basic('e625d981f7f7af6d6152be094f1441691a7b4021b5d0840fa031221c1c1ad26c', '6155e49415051cd4463a07602a0ccb38bd11be47c851554786265465a78ae1a4')
    }

    def next() {
        // TODO determine next countdown time and return it
        def types = this.serviceTypes()
        def typeIds = types.grep { config.types.keySet().contains(it.attributes.name) }.collect { it.id }
        def serviceTypes = typeIds.collect { serviceType(it) }
//        def plans =
//        def plans = typeIds.collect { servicePlan(it) }

        render serviceType(typeIds[0]) as JSON
    }

    def now() {
        render (([time: System.currentTimeMillis()]) as JSON)
    }

    private def serviceType(id) {
        executeRequest("/services/v2/service_types/${id}/plans")
    }

    private def serviceTypes() {
        executeRequest('/services/v2/service_types').data
    }

    private def executeRequest(path) {
        def resp = http.get(path: path, contentType: ContentType.JSON)
        new JsonSlurper().parseText(resp.toString())
    }

    private static Date parseDate(String date) {
        Date.parse(date, /yyyy-MM-dd'T'hh:mm:ss'Z'/)
    }
}
