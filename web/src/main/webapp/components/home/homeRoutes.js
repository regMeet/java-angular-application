'use strict';

angular.module('myApp.home', ['ngRoute'])
.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
	    .when('/', {
	        templateUrl: 'templates/home/home.html',
	        controller: 'HomeCtrl'
	    })
	    .when('/home', {
	        templateUrl: 'templates/home/home.html',
	        controller: 'HomeCtrl'
	    });
}]);
