package countdownclockweb
/**
 * Created by Caleb on 4/30/2016.
 */
class ServiceTimesUtils {

    // TODO get api token from a config
    def api = new PlanningCenterAPI('e625d981f7f7af6d6152be094f1441691a7b4021b5d0840fa031221c1c1ad26c', '6155e49415051cd4463a07602a0ccb38bd11be47c851554786265465a78ae1a4')

    def findNextCountdown(String name, List<String> items) {
        def typeID = findServiceTypeID(name)
        if (!typeID) {
            throw new Exception("Cannot find service type with name: ${name}")
        }
        def plans = findServicePlans(typeID)
        def plan = findNextPlan(typeID, plans)
        def planItems = api.listPlanItems(typeID, plan.id)

        // calculate actual time
        def startTime = parseDate(plan.current.attributes.starts_at)
        def now = new Date()

        def countdownTimes = planItems.grep { it.attributes.title in items }.sort { it.attributes.sequence }
        def nextItem = countdownTimes.find { item ->
            int length = planItems.takeWhile {
                item.attributes.sequence != it.attributes.sequence
            }.sum {
                it.attributes.length
            }
            def time = Calendar.getInstance()
            time.setTime(startTime)
            time.add(Calendar.SECOND, length)
            return now < time.getTime()
        }

        if (nextItem) {
            int length = planItems.takeWhile { nextItem.attributes.sequence != it.attributes.sequence }.sum {
                it.attributes.length
            }
            def time = Calendar.getInstance()
            time.setTime(startTime)
            time.add(Calendar.SECOND, length)
            return time.getTimeInMillis()
        } else {
            return null
        }
    }

    private String findServiceTypeID(String name) {
        api.listServiceTypes().find { name == it.attributes.name }?.id
    }

    private List<Map> findServicePlans(typeID) {
        return api.listPlans(typeID)
    }

    private def findNextPlan(String typeID, List<Map> plans) {
        plans.each { plan ->
            plan.plan_times = api.listPlanTimes(typeID, plan.id)
        }
        def now = new Date()
        def next = plans.find { plan ->
            def current = plan.plan_times.find { time ->
                now < parseDate(time.attributes.ends_at)
            }
            plan.current = current
            return current
        }
        return next
    }

    private static Date parseDate(String date) {
        Date.parse(/yyyy-MM-dd'T'hh:mm:ss'Z'/, date, TimeZone.getTimeZone('GMT'))
    }

}
