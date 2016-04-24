'use strict';

angular.module('countdownclockweb').controller('MainCtrl',
    function ($scope, PcoService, $timeout) {

        var HALF_SECOND = 500;

        $scope.init = function () {
            loadTime();
        };

        var loadTime = function () {
            // TODO rework this to check every 5 or so seconds for a time update and update every half second based on local clock diff
            PcoService.now().subscribe(function (it) {
                $scope.time = it.time;
                console.log('loaded time');
                $timeout(loadTime, HALF_SECOND);
            });
        }
    });
