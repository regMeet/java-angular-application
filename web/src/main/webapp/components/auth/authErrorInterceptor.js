'use strict';

angular.module('myApp.auth')

.factory('authInterceptor', function ($rootScope, $q, AUTH_EVENTS) {
  return {
    responseError: function (response) {
      $rootScope.$broadcast({
        401: AUTH_EVENTS.notAuthenticated,
        403: AUTH_EVENTS.notAuthorized
      }[response.status], response);
      return $q.reject(response);
    }
  };
})
 
.config(function ($httpProvider) {
  $httpProvider.interceptors.push('authInterceptor');
});

//.config(function($httpProvider) {
//
//	var logsOutUserOn401 = [ '$q', '$location', function($q, $location) {
//		var success = function(response) {
//			return response;
//		};
//
//		var error = function(response) {
//			if (response.status === 401) {
//				// redirect them back to login page
//				$location.path('/login');
//
//				return $q.reject(response);
//			} else {
//				return $q.reject(response);
//			}
//		};
//
//		return function(promise) {
//			return promise.then(success, error);
//		};
//	} ];
//
//	$httpProvider.responseInterceptors.push(logsOutUserOn401);
//});