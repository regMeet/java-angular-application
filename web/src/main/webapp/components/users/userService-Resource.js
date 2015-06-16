'use strict';

var userServices = angular.module('myApp.users');

userServices.factory('User', ['$resource', function($resource){
    return $resource('http://localhost:8089/web-services/api/users/:userId', {}, {
      query: {method:'GET', isArray:true}
    });
}]);