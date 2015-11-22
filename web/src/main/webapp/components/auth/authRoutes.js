'use strict';

angular.module('myApp.auth', ['ui.router', 'ngCookies'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: '/templates/auth/login.html',
                controller: 'LoginController',
                controllerAs: 'vm'
            })
            .state('signup', {
                url: '/signup',
                templateUrl: 'templates/auth/signup.html',
                controller: 'SignUpController',
                controllerAs: 'vm'
            })
            .state('logout', {
                url: '/logout',
                template: ' ',
                controller: 'LogoutController',
                controllerAs: 'vm'
            });
    }]);
