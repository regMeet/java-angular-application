'use strict';

angular.module('myApp.users')

.controller('UserListCtrl', [ '$scope', '$alert', '$location', 'UserResource', function($scope, $alert, $location, UserResource) {
		var vm = this;
		vm.status = {};
	    vm.users = [];

        // callback for ng-click 'deleteUser':
    	vm.deleteUser = function(idUser) {
        	UserResource.deleteUser(idUser)
	            .success(function () {
	            	vm.status = 'Deleted User! Refreshing user list.';
	                vm.getUsers();
	            })
	            .error(function (error) {
	            	vm.status = 'Unable to delete user: ' + error.message;
	            });
        };

        vm.getUsers = function() {
	    	UserResource.getUsers()
	    		.success(function(users) {  
	    			vm.users = users;
		        })
		        .error(function() {
		            console.log('users retrieval failed.');
		        });
        };

        vm.getUsers();
	} ])

.controller('UserCreationCtrl', ['$scope', 'UserResource', '$location', function ($scope, UserResource, $location) {
		var vm = this;
		vm.status = {};
		vm.user = {};

		// callback for ng-click 'createNewUser':
	    vm.insertUser = function () {
	        UserResource.insertUser(vm.user)
	            .success(function () {
	                vm.status = 'Inserted User! Refreshing user list.';
	                $location.path('/user-list');
	            }).
	            error(function(error) {
	                vm.status = 'Unable to insert user: ' + error.message;
	            });
	    };

	}])

.controller('UserDetailCtrl', ['$scope', '$routeParams', 'UserResource', '$location', '$authService', function ($scope, $routeParams, UserResource, $location, $authService) {
		var vm = this;
		vm.status = {};
    	vm.user = {};
	
		// callback for ng-click 'updateUser':
    	vm.updateUser = function () {
    		UserResource.updateUser(vm.user)
    			.success(function () {
					vm.status = 'Updated User! Refreshing user list.';

					if (vm.user.idUser == $authService.userId){
						$authService.setCurrentUser(vm.user.username);
					}

					$location.path('/user-list');
				})
				.error(function (error) {
					vm.status = 'Unable to update user: ' + error.message;
				});
	    };

		// callback for ng-click 'cancel':
	    vm.cancel = function () {
		    $location.path('/user-list');
		};

		vm.getUsers = function(id) {
			UserResource.getUser(id)
			.success(function (user) {
	              vm.status = 'get User! Refreshing user detail.';
	              vm.user = user;
	          })
	          .error(function (error) {
	              vm.status = 'Unable to get user: ' + error.message;
	          });
        };

		vm.getUsers($routeParams.id);
	}]);
