'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'satellizer',
    'myApp.auth',
    'myApp.home',
    'myApp.profile',
    'myApp.users',
    'myApp.directives',
    'mgcrea.ngStrap',
    'ngMessages',
    'ui.router'
])
    .config(['$stateProvider', '$authProvider', '$httpProvider', function ($stateProvider, $authProvider, $httpProvider) {
        $stateProvider
            .state('unauthorized', {
                url: '/unauthorized',
                templateUrl: '/templates/403.html'
            })
            .state("otherwise", {
                url: "*path",
                templateUrl: "/templates/404.html"
            });
        // Parametros de configuración de satellizer
        $authProvider.authHeader = 'Authorization'; // default
        $authProvider.authToken = '';
        $authProvider.httpInterceptor = true; // Add Authorization header to HTTP request
        // https://github.com/sahat/satellizer/issues/261
        //$authProvider.tokenRoot = 'entity'; // set the token parent element if the token is not the JSON root

        $authProvider.baseUrl = 'http://localhost:8089/web-services';
        $authProvider.loginUrl = '/auth/login';
        $authProvider.signupUrl = '/auth/signup';
        $authProvider.tokenName = 'token';
        $authProvider.tokenPrefix = 'myApp';

        $authProvider.facebook({
            clientId: '853633721397866'
        });

        $authProvider.google({
            clientId: '84302306491-ff8lnfb0un9j7dgcvs5iejo9fhrv9lik.apps.googleusercontent.com'
        });
    }]);
