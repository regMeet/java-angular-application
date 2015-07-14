'use strict';

var userServices = angular.module('myApp.users');

userServices.factory('UserResource3', [ '$http', function($http) {
	var urlBase = 'http://localhost:8089/web-services/api/users/';

	return {
		getUsers : function() {
			return $http.get(urlBase);
		},
//		updateProfile : function(profileData) {
//			return $http.put('/api/me', profileData);
//		}
	};
} ]);

userServices.factory('UserResource2', ['$http', function ($http) {
		var urlBase = 'http://localhost:8089/web-services/api/users/';

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


userServices.service('UserResource', ['$http', function ($http) {

    var urlBase = 'http://localhost:8089/web-services/api/users';

    this.getUsers = function () {
        return $http.get(urlBase);
    };

    this.getProfile = function () {
        return $http.get(urlBase + '/profile');
    };

    this.getUser = function (id) {
        return $http.get(urlBase + '/' + id);
    };

    this.insertUser = function (user) {
        return $http.post(urlBase, user);
    };

    this.updateUser = function (user) {
    	//return $http.put(urlBase + '/' + user.ID, user);
        return $http.put(urlBase, user);
    };

    this.deleteUser = function (id) {
        return $http.delete(urlBase + '/' + id);
    };

//    this.getOrders = function (id) {
//        return $http.get(urlBase + '/' + id + '/orders');
//    };
}]);
