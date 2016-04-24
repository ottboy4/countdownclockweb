//= require_self
//= require_tree .

'use strict';
angular.module('countdownclockweb', [
    'ngTable',
    'ngAnimate',
    'ngRoute',
    'ui.bootstrap',
    'grails.constants'
]).config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '/countdownclockweb/main.html',
            controller: 'MainCtrl'
        })
        .otherwise({redirectTo: '/'});
});


/**
 * TODO whats left
 * 1. Finish the controller to calculate the next countdown time based on config
 *      a. Figure out how to calculate what is next. Parse date/time and see which one is next that matches the available names
 *      b. Figure out how long to "hold" that countdown till pulling the next one
 * 2. Build out a UI to display the times
 *      a. Show the times then figure out how to set the background color changes etc
 *      b. Pull up old UI to mimic design off of
 *      c. Make sure the website is mobile friendly and test with latency with mobile network etc
 * 3. Update the javascript to do periodic updates and time updates based on local clock/server clock diff
 * 4. Update the controller to pull the config from the yaml instead of hard coded
 *
 * Future updates
 * 1. Build out an "admin" page to setup the config for what times to look for
 * 2. Figure out what happens with slow requests. Does the time get off because the req took too long?
 * 3. Figure out what the best periodic updats is so that the browser doesn't need to be manually refreshed but still stay in sync
 */