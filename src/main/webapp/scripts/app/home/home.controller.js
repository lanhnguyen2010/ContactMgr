'use strict';

angular.module('contactmgrApp')
    .controller('HomeController', function($scope,$http) {
    
        function init(){
           $scope.getRole();
        }
    
        $scope.getRole = function(){
            $http.get('api/security/current-user').success(function(user){
                $scope.role = user.role;
            })
        }
        
        $scope.isAdmin = function(){
            if ($scope.role === "ADMINISTRATOR"){
                return true;
            } else {
                return false;
            }
        }
        
        $scope.isDesigner = function(){
            if ($scope.role === "DESIGNER"){
                return true;
            } else {
                return false;
            }
        }
        
        $scope.isEditor = function(){
            if ($scope.role === "EDITOR"){
                return true;
            } else {
                return false;
            }
        }
        init();
    });
