'use strict';

var userServices = angular.module('userServices', ['ngResource']);

userServices.service('User', ['$http', function ($http) {

    var urlBase = 'http://localhost:8089/web-services/api/users/';

    this.getUsers = function () {
        return $http.get(urlBase);
    };

    this.getUser = function (id) {
        return $http.get(urlBase + '/' + id);
    };

    this.insertUser = function (user) {
        return $http.post(urlBase, user);
    };

    this.updateUser = function (user) {
        return $http.put(urlBase + '/' + user.ID, user);
    };

    this.deleteUser = function (id) {
        return $http.delete(urlBase + '/' + id);
    };

    this.getOrders = function (id) {
        return $http.get(urlBase + '/' + id + '/orders');
    };
}]);