'use strict';

angular.module('myApp.profile', ['ui.router'])
    .config(['$stateProvider', 'USER_ROLES', function ($stateProvider, USER_ROLES) {
        $stateProvider
            .state('profile', {
                url: '/profile',
                templateUrl: '/templates/profile/profile.html',
                controller: 'ProfileCtrl',
                controllerAs: 'vm',
                access: {
                    authorizedRoles: [USER_ROLES.user, USER_ROLES.admin]
                }
            });
    }]);
