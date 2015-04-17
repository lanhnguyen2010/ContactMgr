'use strict';

angular.module('contactmgrApp').controller(
        'UsersController',
        function($scope, UsersService, ngTableParams) {

            function init() {
                $scope.getCompanies();
                $scope.getRoles();
                $scope.initUser();
            }
            ;
            $scope.criteria = {
                username : '',
                firstlastName : '',
                email : '',
                role : '',
                createdFrom : '',
                createdTo : '',
                assignedCompanies : '',
                pageIndex : 1,
                pageSize : 10
            };
            $scope.initUser = function() {
                $scope.user = {
                    "id" : "",
                    "createdAt" : "",
                    "createdBy" : "",
                    "updatedAt" : "",
                    "updatedBy" : "",
                    "username" : "",
                    "password" : "",
                    "firstname" : "",
                    "lastname" : "",
                    "email" : "",
                    "role" : "",
                    "expiredDate" : "",
                    "active" : false,
                    "language" : "",
                    "assignedCompanies" : ""
                };
                $scope.confirmPassword = '';
                $scope.checkboxSelection = '1';
            }
            var PAGE_SIZE = 10;
            $scope.users = [];
            $scope.searchClicked = false;
            $scope.currentPage = 1;
            $scope.selectedCompanies = "";
            $scope.companysetting = {
                enableSearch : true,
                scrollable : true,
                displayProp : 'name',
                buttonClasses : 'form-control'
            };
            $scope.selectcompaniestext = {
                buttonDefaultText : 'Select Assigned Companies'
            };
            $scope.searchUsers = function() {
                if ($scope.isLoading) {
                    return;
                }

                $scope.searchClicked = true;
                $scope.isLoading = true;
                $scope.usersTableParams.reload();
            }

            $scope.usersTableParams = new ngTableParams({
                count : 10, // Count per page
            }, {
                counts : [],
                getData : function($defer, params) {
                    if (!$scope.searchClicked)
                        return;
                    $scope.currentPage = params.page();
                    $scope.criteria.pageIndex = params.page();
                    $scope.criteria.pageSize = 10;
                    UsersService.searchUsers($scope.criteria).success(
                            function(data, status) {
                                $scope.users = data['items'];
                                params.total(data['totalItem']);
                                $defer.resolve($scope.users);
                                $scope.isLoading = false;
                            }).error(function(data, status) {
                        console.log("Error", status);
                    });

                    $scope.checkboxes = {
                        'checked' : false,
                        items : {}

                    };
                    $scope.checkedIds = '';
                }
            });
            $scope.selectedIds = [];

            function findAndRemove(array, property, value) {
                $.each(array, function(index, result) {
                    if (result[property] == value) {
                        array.splice(index, 1);
                    }
                });
            }

            // delete Users Controller
            $scope.deleteUsers = function() {
                if (confirm("Do you want to delete?")) {
                    UsersService.deleteUsers($scope.selectedIds).success(
                            function(data, status) {
                                $scope.usersTableParams.reload();
                            }).error(function(data, status) {
                        console.log("Error", status);
                    });
                }
            };
            // activate Users Controller
            $scope.activateUsers = function() {
                if (confirm("Do you want to Activate?")) {
                    UsersService.activateUsers($scope.selectedIds).success(
                            function(data, status) {
                                $scope.searchUsers($scope.currentPage);
                            }).error(function(data, status) {
                        console.log("Error", status);
                    });
                }
            };

            // inactivate users Controller
            $scope.deactivateUsers = function() {
                if (confirm("Do you want to Inactivate?")) {
                    UsersService.deactivateUsers($scope.selectedIds).success(
                            function(data, status) {
                                $scope.searchUsers($scope.currentPage);
                            }).error(function(data, status) {
                        console.log("Error", status);
                    });
                }
            };

            $scope.selectedIds = [];

            // watch selected users
            $scope.$watch('users|filter:{checked:true}', function(results) {
                $scope.selectedIds = results.map(function(user) {
                    return user.id;
                });

                var count = $scope.selectedIds.length;
                var total = $scope.users.length;
                $scope.users.checked = (count == total);
                // grayed checkbox
                angular.element(document.getElementById('check_all')).prop(
                        'indeterminate', (count > 0 && count < total));

            }, true);
            $scope.toggleCheckAll = function(e) {
                var checked = (document.getElementById('check_all').checked);
                for (var i = 0; i < $scope.users.length; i++) {
                    $scope.users[i].checked = checked;
                }
            }

            $scope.getRoles = function() {
                UsersService.getRoles().success(function(data, status) {
                    $scope.roles = data;
                }).error(function(data, status) {
                    console.log("Error get roles", status);
                });
            };
            $scope.getCompanies = function() {
                UsersService.getCompanies().success(function(data, status) {
                    $scope.assignedcompanies = data;
                }).error(function(data, status) {
                    console.log("Error get companies", status);
                });
            }
            // save user
            $scope.saveUser = function() {
                if ($scope.checkboxSelection == "1") {
                    $scope.user.active = true;
                    console.log("value active true");
                } else {
                    $scope.user.active = false;
                    console.log("value active false");
                }
                UsersService.saveUser($scope.user).success(
                        function(data, status, headers, config) {
                            console.log("Save user successfull!");
                            window.alert("Save user successfull!");
                            $scope.initUser();
                        }).error(
                        function(data, status, header, config) {
                            console.log("Error: " + status);
                            $scope.validator = data.errors;
                            window.alert("Can not save!"
                                    + $scope.validator.username + " "
                                    + $scope.validator.password);
                        });

            };
            // get value of user from search function and set value of this user
            // to update
            $scope.setUser = function(user) {
                $scope.user = user;
                console.log("Date: " + $scope.user.expiredDate);
                if ($scope.user.active == true) {
                    $scope.checkboxSelection = '1';
                } else {
                    $scope.checkboxSelection = '0';
                }
            };
            $scope.toggleMin = function() {
                $scope.minDate = $scope.minDate ? null : new Date();
            };
            $scope.toggleMin();

            $scope.openCalendaFrom = function($event) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.openedCalendaFrom = true;
            };
            $scope.openCalendaTo = function($event) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.openedCalendaTo = true;
            };
            $scope.open = function($event) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.opened = true;
            };

            $scope.dateOptions = {
                formatYear : 'yy',
                startingDay : 1
            };
            $scope.selectedCompanies = function(id) {
                $scope.user.assignedCompanies += id;
            };
            init();
        })