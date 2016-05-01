package countdownclockweb

import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

/**
 * Created by Caleb on 4/30/2016.
 */
class PlanningCenterAPI {

    def http = new HTTPBuilder('https://api.planningcenteronline.com')

    PlanningCenterAPI(String user, String pass) {
        http.auth.basic(user, pass)
    }

    List<Map> listPlanItems(serviceTypeID, planID) {
        executeRequest("/services/v2/service_types/${serviceTypeID}/plans/${planID}/items").data
                .grep {
            // only keep the items during the service and not the headers
            it.attributes.item_type != 'header' && it.attributes.service_position == 'during'
        }
    }

    List<Map> listPlanTimes(serviceTypeID, planID) {
        executeRequest("/services/v2/service_types/${serviceTypeID}/plans/${planID}/plan_times").data
                .grep {
            // only keep service times
            it.attributes.time_type == 'service'
        }
    }

    Map listPlan(serviceTypeID, planID) {
        executeRequest("/services/v2/service_types/${serviceTypeID}/plans/${planID}").data
    }

    List<Map> listPlans(serviceTypeID, numPerPage = 1) {
        executeRequest("/services/v2/service_types/${serviceTypeID}/plans?filter=future&per_page=${numPerPage}").data
    }

    List<Map> listServiceTypes() {
        executeRequest('/services/v2/service_types').data
    }

    private def executeRequest(path) {
        def resp = http.get(path: path, contentType: ContentType.JSON)
        new JsonSlurper().parseText(resp.toString())
    }
}
