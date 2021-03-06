'use strict';

angular.module('countdownclockweb').controller('MainCtrl',
    function ($scope, PcoService, $timeout) {
        
        $scope.serverTimeDiff = 0;
        $scope.countdownTo = null;
        $scope.countdownToName = null;

        var HALF_SECOND = 500;

        $scope.init = function () {
            loadTime();
            cycle();
        };

        var loadTime = function () {
            var now = moment();
            PcoService.now().subscribe(function (it) {
                $scope.serverTimeDiff = now.diff(it.time);
                console.log('loaded time');
            });
            
            PcoService.next().subscribe(function (it) {
                $scope.countdownTo = it.time;
                $scope.countdownToName = it.serviceTimeName + ' - ' + it.itemName;
            });
        };
        
        var cycle = function () {
            $scope.time = moment().subtract($scope.serverTimeDiff, 'milliseconds');
            if ($scope.countdownTo) {
                $scope.timeleft = moment.duration($scope.countdownTo.diff($scope.time), 'milliseconds');
            }
            $timeout(cycle, HALF_SECOND);
        };
    });
