'use strict';

angular.module('myApp.auth')

.factory('$AuthService', ['$auth', function albumService($auth) {
		var urlBase = 'http://localhost:8089/web-services/api/users/';
		var currentUser = '';

		// interface
	    var service = {
	        users: [],
	        getUsers: getUsers
	    };
	    return service;

	    // implementation
	    function getUsers() {
	        return $http.get(urlBase)
	            .success(function(data) {
	                service.users = data;
	            });
	    }
}]);
