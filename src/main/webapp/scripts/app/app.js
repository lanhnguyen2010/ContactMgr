'use strict';

angular.module('contactmgrApp', ['LocalStorageModule', 'tmh.dynamicLocale','ngResource', 'ui.router', 
                                 'ngCookies', 'pascalprecht.translate', 'ngCacheBuster', 'ngTable',
                                 'angularjs-dropdown-multiselect','ui.bootstrap'])
    .run(function($rootScope, $location, $window, $http, $state, $translate, Language, ENV, VERSION) {
        $rootScope.ENV = ENV;
        $rootScope.VERSION = VERSION;
        $rootScope.back = function() {
            // If previous state is 'activate' or do not exist go to 'home'
            if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
                $state.go('home');
            } else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
        };
    })

    .config(function($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider, $translateProvider, 
    	             tmhDynamicLocaleProvider, httpRequestInterceptorCacheBusterProvider) {

        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        $urlRouterProvider.otherwise('/');
        $stateProvider.state('site', {
            'abstract': true,
            views: {
                'navbar@': {
                    templateUrl: 'scripts/components/navbar/navbar.html',
                    controller: 'NavbarController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', 
                                         function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('language');
                    return $translate.refresh();
                }]
            }
        });

        // Initialize angular-translate
        $translateProvider.useLoader('$translatePartialLoader', {
            urlTemplate: 'i18n/{lang}/{part}.json'
        });

        $translateProvider.preferredLanguage('en');
        $translateProvider.useCookieStorage();

        tmhDynamicLocaleProvider.localeLocationPattern('bower_components/angular-i18n/angular-locale_{{locale}}.js');
        tmhDynamicLocaleProvider.useCookieStorage('NG_TRANSLATE_LANG_KEY');
    }).factory('authHttpResponseInterceptor',['$q','$location',function($q,$location){
        return {
            responseError: function(rejection) {
                if (rejection.status === 404) {
                    $location.path('/not_found');
                }
                if (rejection.status === 500) {
                    $location.path('/error');
                }
                if (rejection.status === 403) {
                    $location.path('/access_denied');
                }
                if (rejection.status === 401) {
                    $location.path('/login');
                }
                return $q.reject(rejection);
            }
        }
    }]).config(['$httpProvider',function($httpProvider) {
        //Http Intercpetor to check auth failures for xhr requests
        $httpProvider.interceptors.push('authHttpResponseInterceptor');
    }]);
