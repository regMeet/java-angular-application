'use strict';

angular.module('myApp.auth', ['ngRoute'])
.config(['$routeProvider', function ($routeProvider) {
	$routeProvider
	    .when('/login', {
	        templateUrl: 'templates/auth/login.html',
	        controller: 'LoginController'
	    })
	    .when('/signup', {
	        templateUrl: 'templates/auth/signup.html',
	        controller: 'SignUpController'
	    })
	    .when('/logout', {
	        templateUrl: null,
	        controller: 'LogoutController'
	    });
}]);
