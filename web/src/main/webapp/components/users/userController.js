'use strict';

angular.module('myApp.users')

.controller('UserListCtrl', [ '$scope', '$alert', 'UserResource', function($scope, $alert, UserResource) {
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

.controller('UserCreationCtrl', ['$scope', 'UserResource', '$state', function ($scope, UserResource, $state) {
		var vm = this;
		vm.status = {};
		vm.user = {};

		// callback for ng-click 'createNewUser':
	    vm.insertUser = function () {
	        UserResource.insertUser(vm.user)
	            .success(function () {
	                vm.status = 'Inserted User! Refreshing user list.';
					$state.go("user-list");
	            }).
	            error(function(error) {
	                vm.status = 'Unable to insert user: ' + error.message;
	            });
	    };

	}])

.controller('UserDetailCtrl', ['$scope', '$stateParams', 'UserResource', '$state', '$authenticationService', function ($scope, $stateParams, UserResource, $state, $authenticationService) {
		var vm = this;
		vm.status = {};
    	vm.user = {};
	
		// callback for ng-click 'updateUser':
    	vm.updateUser = function () {
    		UserResource.updateUser(vm.user)
    			.success(function () {
					vm.status = 'Updated User! Refreshing user list.';

					if (vm.user.idUser == $authenticationService.userId){
						$authenticationService.setCurrentUser(vm.user.username);
					}

					$state.go("user-list");
				})
				.error(function (error) {
					vm.status = 'Unable to update user: ' + error.message;
				});
	    };

		// callback for ng-click 'cancel':
	    vm.cancel = function () {
			$state.go("user-list");
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

		vm.getUsers($stateParams.id);
	}]);
