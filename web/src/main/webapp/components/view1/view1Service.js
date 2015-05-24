'use strict';

var userServices = angular.module('userServices', ['ngResource']);

userServices.factory('User', ['$resource',
  function($resource){
    return $resource('http://localhost:8089/web-services/users/:userId', {}, {
      query: {method:'GET', params:{userId:'1'}, isArray:true}
    });
  }]);