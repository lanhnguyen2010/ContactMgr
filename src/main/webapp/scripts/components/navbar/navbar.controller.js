'use strict';

angular.module('contactmgrApp')
    .controller('NavbarController', function($scope, $location, $state,$http) {
        $scope.$state = $state;
        function init(){
            $scope.getUsername();
        }
        $scope.username='';
        $scope.getUsername = function(){
            $http.get("api/security/current-user").success(function(user){
               $scope.username = user.username;
               console.log($scope.username);
            });
        }
        init();
    });
