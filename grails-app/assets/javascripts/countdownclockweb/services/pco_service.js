'use strict';

angular.module('countdownclockweb').service('PcoService',
    function ($http) {

        var pullData = function (it) {
            return it.data;
        };

        return {
            now: function () {
               return Rx.Observable.fromPromise($http.get('/Time/now')).map(pullData);
            },
            next: function () {
                return Rx.Observable.fromPromise($http.get('/Time/next')).map(pullData);
            }
        };
    });
