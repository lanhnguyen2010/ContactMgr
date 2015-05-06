'use strict';

angular
        .module('contactmgrApp')
        .controller(
                'UsersController',
                function($scope, $filter, UsersService, ngTableParams, $http,
                        $location,$rootScope) {
                    function init() {
                        $scope.getCompanies();
                        $scope.getRoles();
                        $scope.initUser();
                        $scope.checkRole();
                    };

                    $scope.checkRole = function() {
                        $http.get("api/security/current-user").success(
                                function(user) {
                                    if (user.role != "ADMINISTRATOR") {
                                        $location.path("/access_denied");
                                    }
                                })
                    }
                    $scope.criteria = {
                        username : '',
                        firstlastName : '',
                        email : '',
                        role : '',
                        createdFrom : '',
                        createdTo : '',
                        assignedCompanies : [],
                        pageIndex : 1,
                        pageSize : 10
                    };
                    var actualCriteria = {};
                    $scope.initUser = function() {
                        $scope.user = {
                            "id" : "",
                            "createdAt" : "",
                            "createdBy" : "",
                            "updatedAt" : "",
                            "updatedBy" : "",
                            "username" : "",
                            "password" : "",
                            "confirmPassword" : "",
                            "firstname" : "",
                            "lastname" : "",
                            "email" : "",
                            "role" : null,
                            "expiredDate" : "",
                            "active" : false,
                            "language" : "",
                            "assignedCompanies" : []
                        };
                        $scope.checkboxSelection = '1';
                        $scope.exitedCompanies = [];
                        $scope.validator = null;
                        $scope.flagUpdate = false;
                    }
                    var PAGE_SIZE = 10;
                    $scope.users = [];
                    $scope.actualCriteria = {};
                    $scope.searchClicked = false;
                    $scope.currentPage = 1;
                    $scope.selectedCompanies = [];
                    $scope.companysetting = {
                        enableSearch : true,
                        scrollable : true,
                        displayProp : 'name',
                        buttonClasses : 'form-control col-md-9'
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
                        $scope.criteria.assignedCompanies = [];
                        for (var i = 0; i < $scope.selectedCompanies.length; i++) {
                            $scope.criteria.assignedCompanies
                                    .push(parseInt($scope.selectedCompanies[i]["id"]));
                        }
                        $scope.criteria.createdFrom = $filter('date')(
                                $scope.criteria.createdFrom, 'yyyy-MM-dd');
                        $scope.criteria.createdTo = $filter('date')(
                                $scope.criteria.createdTo, 'yyyy-MM-dd');
                        angular.copy($scope.criteria, $scope.actualCriteria);
                        $scope.usersTableParams.reload();

                    }

                    $scope.usersTableParams = new ngTableParams({
                        count : 10
                    // Count per page
                    }, {
                        counts : [],
                        getData : function($defer, params) {
                            $scope.actualCriteria.pageIndex = params.page();
                            $scope.actualCriteria.pageSize = params.count();
                            if (!$scope.searchClicked) {
                                return;
                            } else if ($scope.currentPage == params.page()) {
                                $scope.criteria.pageIndex = 1;
                                params.page(1);
                            }

                            $scope.currentPage = params.page();
                            UsersService.searchUsers($scope.actualCriteria)
                                    .success(function(data, status) {
                                        $scope.users = data['items'];
                                        params.total(data['totalItems']);
                                        $defer.resolve($scope.users);
                                        $scope.isLoading = false;
                                    }).error(function(data, status) {
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
                            UsersService.deleteUsers($scope.selectedIds)
                                    .success(function(data, status) {
                                        $scope.usersTableParams.reload();
                                    })
                        }
                    };
                    // activate Users Controller
                    $scope.activateUsers = function() {
                        if (confirm("Do you want to Activate?")) {
                            UsersService.activateUsers($scope.selectedIds)
                                    .success(function(data, status) {
                                        $scope.searchUsers($scope.currentPage);
                                    })
                        }
                    };

                    // inactivate users Controller
                    $scope.deactivateUsers = function() {
                        if (confirm("Do you want to Inactivate?")) {
                            UsersService.deactivateUsers($scope.selectedIds)
                                    .success(function(data, status) {
                                        $scope.searchUsers($scope.currentPage);
                                    })
                        }
                    };

                    $scope.selectedIds = [];

                    // watch selected users
                    $scope.$watch('users|filter:{checked:true}', function(
                            results) {
                        if (results == null) {
                            return;
                        }
                        $scope.selectedIds = results.map(function(user) {
                            return user.id;
                        });

                        var count = $scope.selectedIds.length;
                        var total = $scope.users.length;
                        $scope.users.checked = (count == total);
                        // grayed checkbox
                        angular.element(document.getElementById('check_all'))
                                .prop('indeterminate',
                                        (count > 0 && count < total));

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
                        })
                    };
                    $scope.getCompanies = function() {
                        UsersService.getCompanies().success(
                                function(data, status) {
                                    $scope.assignedcompanies = data;
                                }).error(function(data, status) {
                        });
                    }
                    
                    $scope.checkUpdating = function(){
                        if($scope.flagUpdate == true){
                            $scope.initUser();
                        }
                    }
                    // save user
                    $scope.saveUser = function() {
                        if ($scope.checkboxSelection == "1") {
                            $scope.user.active = true;
                        } else {
                            $scope.user.active = false;
                        }
                        $scope.setAssignedCompanies();
                        UsersService.saveUser($scope.user)
                                .success(
                                        function(data, status, headers, config) {
                                            window.alert("Save user successful!");
                                            $scope.currentPage = $scope.currentPage - 1;
                                            $scope.usersTableParams.reload();
                                            if($scope.flagUpdate == true){
                                                $('#userModal').modal('toggle');
                                                $scope.flagUpdate == false;
                                            }
                                            $scope.initUser();
                                        }).error(
                                        function(data, status, header, config) {
                                            $scope.validator = data.errors;
                                            window.alert("Cannot save!");
                                        });
                    };
                    // get value of user from search function and set value of
                    // this user
                    // to update
                    $scope.addUser = function(user) {
                        angular.copy(user, $scope.user);
                        $scope.user.password = "";
                        $scope.getCompaniesToDisplayUI();
                        if ($scope.user.active == true) {
                            $scope.checkboxSelection = '1';
                        } else {
                            $scope.checkboxSelection = '0';
                        }
                        $scope.flagUpdate = true;
                    };
                    $scope.minDate = new Date();
                    $scope.openCalendar = function($event, isTo) {
                        $event.preventDefault();
                        $event.stopPropagation();
                        if (isTo == 2) {
                            $scope.openedCalendarTo = true;
                            $scope.openedCalendarFrom = false;
                        } else {
                            $scope.openedCalendarFrom = true;
                            $scope.openedCalendarTo = false;
                        }
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
                    $scope.setAssignedCompanies = function() {
                        $scope.user.assignedCompanies = [];
                        for (var i = 0; i < $scope.exitedCompanies.length; i++) {
                            $scope.user.assignedCompanies
                                    .push(parseInt($scope.exitedCompanies[i]["id"]));
                        }
                    }
                    // set list companies that user are managing into interface
                    $scope.getCompaniesToDisplayUI = function() {
                        $scope.exitedCompanies = [];
                        if ($scope.user.assignedCompanies != null) {
                            $scope.user.assignedCompanies.forEach(function(
                                    entry) {
                                var singleObj = {}
                                singleObj['id'] = entry.toString();
                                $scope.exitedCompanies.push(singleObj);
                            });
                        }
                    }
                    init();
                })
