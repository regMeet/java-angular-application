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
            .state('verifyA-account', {
                url: '/verify?token',
                templateUrl: 'templates/auth/verify.html',
                controller: 'VerifyController',
                controllerAs: 'vm'
            })
            .state('forgot-password', {
                url: '/forgot-password',
                templateUrl: 'templates/auth/forgotPassword.html',
                controller: 'ForgotPasswordController',
                controllerAs: 'vm'
            })
            .state('password-forgotten', {
                url: '/password-forgotten?token',
                templateUrl: 'templates/auth/password-forgotten.html',
                controller: 'PasswordForgottenController',
                controllerAs: 'vm'
            })
            .state('logout', {
                url: '/logout',
                template: ' ',
                controller: 'LogoutController',
                controllerAs: 'vm'
            });
    }]);
