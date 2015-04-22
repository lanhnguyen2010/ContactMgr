'use strict';

angular.module('contactmgrApp')
    .controller('HomeController', function($scope) {
    
        $scope.role = "ADMINISTRATTOR";
        
        $scope.isAdmin = function(){
            if ($scope.role === "ADMINISTRATTOR"){
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
    });
