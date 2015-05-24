'use strict';

var userServices = angular.module('userServices', ['ngResource']);

userServices.factory('User', ['$resource',
  function($resource){
    return $resource('http://localhost:8089/web-services/users/:userId', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);