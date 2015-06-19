'use strict';

var userServices = angular.module('myApp.users');

//userServices.factory('UserResource', [ '$http', function($http) {
//	var urlBase = 'http://localhost:8089/web-services/api/users/';
//	return {
//		getUsers : function() {
//			return $http.get(urlBase);
//		},
//		updateProfile : function(profileData) {
//			return $http.put('/api/me', profileData);
//		}
//	};
//} ]);


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
