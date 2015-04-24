'use strict';

angular.module('contactmgrApp')
    .config(function($stateProvider,$httpProvider) {
        $stateProvider
            .state('login', { 
                parent: 'site',
                url: '/login',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/authen/login.html',
                        controller: 'LoginController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('login');
                        return $translate.refresh();
                    }]
                }
            }),
            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        });
