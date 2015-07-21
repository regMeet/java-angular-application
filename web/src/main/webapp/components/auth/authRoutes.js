'use strict';

angular.module('myApp.auth', ['ngRoute'])
.config(['$routeProvider', function ($routeProvider) {
	$routeProvider
	    .when('/login', {
	        templateUrl: 'templates/auth/login.html',
	        controller: 'LoginController',
        	controllerAs : 'vm'
	    })
	    .when('/signup', {
	        templateUrl: 'templates/auth/signup.html',
	        controller: 'SignUpController',
        	controllerAs : 'vm'
	    })
	    .when('/logout', {
	        template: ' ',
	        controller: 'LogoutController'
	    });
}]);
