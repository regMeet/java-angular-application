'use strict';

angular.module('myApp.users', ['ui.router', 'ngResource'])
    .config(['$stateProvider', 'USER_ROLES', function ($stateProvider, USER_ROLES) {
        $stateProvider
            .state('user-list', {
                url: '/user-list',
                templateUrl: '/templates/users/user-list.html',
                controller: 'UserListCtrl',
                controllerAs: 'vm',
                access: {
                    authorizedRoles: [USER_ROLES.ADMIN]
                }
            })
            .state('user-detail', {
                url: '/user-detail/:id',
                templateUrl: 'templates/users/user-detail.html',
                controller: 'UserDetailCtrl',
                controllerAs: 'vm',
                access: {
                    authorizedRoles: [USER_ROLES.ADMIN]
                }
            })
            .state('user-creation', {
                url: '/user-creation',
                templateUrl: 'templates/users/user-creation.html',
                controller: 'UserCreationCtrl',
                controllerAs: 'vm',
                access: {
                    authorizedRoles: [USER_ROLES.ADMIN]
                }
            });
    }]);
