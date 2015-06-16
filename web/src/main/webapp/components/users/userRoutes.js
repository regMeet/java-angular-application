'use strict';

angular.module('myApp.users', ['ngRoute', 'ngResource'])
.config(['$routeProvider', function ($routeProvider) {
//    $routeProvider.when('/user-list', {
//        templateUrl: 'templates/users/user-list.html',
//        controller: 'UserListCtrl'
//    });
    
    $routeProvider.when('/user-list', {templateUrl: 'templates/users/user-list.html', controller: 'UserListCtrl'});
    $routeProvider.when('/user-detail/:id', {templateUrl: 'templates/users/user-detail.html', controller: 'UserDetailCtrl'});
    $routeProvider.when('/user-creation', {templateUrl: 'templates/users/user-creation.html', controller: 'UserCreationCtrl'});
//    $routeProvider.otherwise({redirectTo: '/user-list'});
}]);
