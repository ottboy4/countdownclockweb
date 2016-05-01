'use strict';

angular.module('countdownclockweb').service('PcoService',
    function ($http) {

        var pullData = function (it) {
            return it.data;
        };

        return {
            now: function () {
               return Rx.Observable.fromPromise($http.get('/Time/now')).map(pullData).map(function (it) {
                   it.time = moment(it.time);
                   return it;
               });
            },
            next: function () {
                return Rx.Observable.fromPromise($http.get('/Time/next')).map(pullData).map(function (it) {
                    it.time = moment(it.time);
                    return it;
                });
            }
        };
    });
