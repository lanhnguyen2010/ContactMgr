'use strict';

angular.module('contactmgrApp')
    .controller('NavbarController', function($scope, $location, $state) {
        $scope.$state = $state;
    });
