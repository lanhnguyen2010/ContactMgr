'use strict';

angular.module('contactmgrApp')
    .controller('ContactController', function($scope, ContactService, ngTableParams,$http) {
        function init(){
            $scope.getCompanies();
            $scope.getAuthUser();
        };
        $scope.getAuthUser = function(){
            $http.get('api/security/current-user');
        }

        $scope.criteria = {
            name: '',
            email: '',
            mobile: '',
            jobTitle: '',
            department: '',
            company: '',
            pageIndex: 1,
            pageSize: 10
        };
        
        $scope.actualCriteria={};

        $scope.contacts = [];
        $scope.searchClicked = false;
        
        $scope.searchContacts = function() {
            if ($scope.isLoading) {
                return;
            }

            $scope.searchClicked = true;
            $scope.isLoading = true;
            $scope.firstPageLoad = true;
            angular.copy($scope.criteria , $scope.actualCriteria);
            $scope.tableParams.reload();
        }

        $scope.tableParams = new ngTableParams({
            count: 10
        }, {
            counts: [],
            getData: function ($defer, params) {
                if (!$scope.searchClicked) {
                    return;
                }
                if($scope.firstPageLoad){
                    $scope.actualCriteria.pageIndex = 1;
                    params.page(1);
                     $scope.firstPageLoad = false;
                }else{
                     $scope.actualCriteria.pageIndex = params.page();
                }
                $scope.actualCriteria.pageSize = params.count();
                ContactService.searchContacts($scope.actualCriteria)
                    .success(function(data, status) {
                        params.total(data.totalItems);
                        $scope.contacts = data.items;
                        $defer.resolve(data.items);
                        $scope.isLoading = false;
                    })
            }
        });

        $scope.selectedCompany = {};
        $scope.setSelectedCompany = function(contact) {
            if (contact.work) {
                $scope.selectedCompany = contact.work.company || {};
            }
        };

        $scope.selectedIds = [];
        $scope.deleteContacts = function() {
            if (confirm('Do you want to delete?')) {
                ContactService.deleteContacts($scope.selectedIds)
                    .success(function (data, status) {
                        $scope.tableParams.reload();
                    })
            }
        };

        // watch selected contacts
        $scope.$watch('contacts|filter:{checked:true}', function(results) {
                $scope.selectedIds = results.map(function(contact) {
                    return contact.id;
                });

                var count = $scope.selectedIds.length;
                var total = $scope.contacts.length;
                $scope.contacts.checked = (count == total);
                // grayed checkbox
                angular.element(document.getElementById('check_all'))
                       .prop('indeterminate', (count > 0 && count < total));

            }, true);
        $scope.toggleCheckAll = function(e) {
            var checked = (document.getElementById('check_all').checked);
            for (var i=0; i<$scope.contacts.length; i++) {
                $scope.contacts[i].checked = checked;
            }
        }
        $scope.getCompanies = function() {
            ContactService.getCompanies()
                .success(function(data,status) {
                    $scope.companies=data;
                })
        }
        init();
    });
