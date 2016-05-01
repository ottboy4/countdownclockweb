package countdownclockweb

import grails.converters.JSON

class TimeController {

    ServiceTimesUtils util = new ServiceTimesUtils()

    def next() {
        // TODO get the plan/items name from config - or from call arguments
        def next = util.findNextCountdown('Weekend Service', ['Bumper', 'Prayer'])
        def result = [
                time: next
        ]
        render result as JSON
    }

    def now() {
        def result = [
                time: System.currentTimeMillis()
        ]
        render result as JSON
    }
}
