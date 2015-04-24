'use strict';

angular.module('contactmgrApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'site',
                url: '/error',
                data: {
                    roles: [],
                    pageTitle: 'errors.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/error/error.html'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('errors');
                        return $translate.refresh();
                    }]
                }
            })
            .state('access_denied', {
                parent: 'site',
                url: '/access_denied',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/error/access_denied.html'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('errors');
                        return $translate.refresh();
                    }]
                }
            })
        .state('not_found', {
            parent: 'site',
            url: '/not_found',
            data: {
                roles: []
            },
            views: {
                'content@': {
                    templateUrl: 'scripts/app/error/not_found.html'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('errors');
                    return $translate.refresh();
                }]
            }
        });
    });
