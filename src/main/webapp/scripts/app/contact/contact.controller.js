'use strict';

angular.module('contactmgrApp')
    .controller('ContactController', function($scope, ContactService, ngTableParams) {
        function init(){
            $scope.getCompanies();
        };

        $scope.criteria = {
            name: '',
            email: '',
            mobile: '',
            jobTitle: '',
            department: '',
            company: '',
            pageIndex: 1
        };

        $scope.contacts = [];
        $scope.searchClicked = false;
        $scope.searchContacts = function() {
            if ($scope.isLoading) {
                return;
            }

            $scope.searchClicked = true;
            $scope.isLoading = true;
            $scope.tableParams.reload();
        }

        $scope.tableParams = new ngTableParams({
            count: 15
        }, {
            counts: [],
            getData: function ($defer, params) {
                if (!$scope.searchClicked) {
                    return;
                }

                $scope.criteria.pageIndex = params.page();
                $scope.criteria.pageSize = params.count();
                ContactService.searchContacts($scope.criteria)
                    .success(function(data, status) {
                        params.total(data.totalItems);
                        $scope.contacts = data.items;
                        $defer.resolve(data.items);

                        $scope.isLoading = false;
                    })
                    .error(function(data, status) {
                        console.log(status);
                    });

                $scope.checkboxes = {
                    'checked': false,
                    items: {}
                };
                $scope.checkedIds = '';
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
                        console.log('Deleted ' + data + ' contact(s)');
                        $scope.tableParams.reload();
                    })
                    .error(function (data, status) {
                        console.log('Error', status);
                    });
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
                .error(function (data, status) {
                    console.log('Error get companies', status);
                });
        }

        init();
    });
