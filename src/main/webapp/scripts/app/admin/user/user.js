'use strict';

angular.module('contactmgrApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('user', {
                parent: 'site',
                url: '/admin/user',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/user/user.html',
                        controller: 'UsersController'

                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('user');
                        return $translate.refresh();
                    }]
                }
            })
            .state('create-user', {
	            parent: 'site',
	            url: '/admin/user/new',
	            data: {
	                roles: []
	            },
	            views: {
	                'content@': {
	                    templateUrl: 'scripts/app/admin/user/create-user.html',
	                    controller: 'UsersController'
	
	                }
	            },
	            resolve: {
	                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate,$translatePartialLoader) {
	                    $translatePartialLoader.addPart('user');
	                    return $translate.refresh();
	                }]
	            }
	        });
        });