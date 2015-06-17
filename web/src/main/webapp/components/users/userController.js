'use strict';

angular.module('myApp.users')

.controller('UserListCtrl', [ '$scope', '$alert', '$location', 'UserResource', function($scope, $alert, $location, UserResource) {
//		$scope.status;
//	    $scope.users;
//	    $scope.orders;
	
	    getUsers();
	
	    function getUsers() {
	    	UserResource.getUsers()
	            .success(function (data) {
	                $scope.users = data;
	            })
	            .error(function (error) {
	                $scope.status = 'Unable to load user data: ' + error.message;
	            });
	    }
	
		/**
		 * Get user's profile information.
		 
		$scope.getUsers = function() {
			UserResource.getUsers()
			.success(function(data) {
				$scope.users = data;
			}).error(function(error) {
				$alert({
					content : error.message,
					animation : 'fadeZoomFadeDown',
					type : 'material',
					duration : 3
				});
			});
		};*/

		// callback for ng-click 'createUser':
        $scope.createNewUser = function () {
            $location.path('/user-creation');
        };

		// callback for ng-click 'editUser':
        $scope.editUser = function (userId) {
            $location.path('/user-detail/' + userId);
        };

        // callback for ng-click 'deleteUser':
        /*
        $scope.deleteUser = function (userId) {
        	UserResource.delete({ id: userId });
        	// $scope.users = UsersFactory.query();
        	$scope.getUsers();
        };*/
        
        $scope.deleteUser = function (idUser) {
        	UserResource.deleteUser(idUser)
            .success(function () {
                $scope.status = 'Deleted User! Refreshing user list.';
                getUsers();
            })
            .error(function (error) {
                $scope.status = 'Unable to delete user: ' + error.message;
            });
        };

//		$scope.getUsers();
	} ])

.controller('UserCreationCtrl', ['$scope', 'UserResource', '$location', function ($scope, UserResource, $location) {

		// callback for ng-click 'createNewUser':
	    $scope.createNewUser = function () {
	    	UserResource.create($scope.user);
	    	$location.path('/user-list');
	    };
	    
	    $scope.insertUser = function () {
	        //Fake user data
//	        var newUser = {
//	        	username: 'testUsername',
//	            name: 'testName',
//	            lastname: 'testLastName'
//	        };
	        UserResource.insertUser($scope.user)
	            .success(function () {
	                $scope.status = 'Inserted User! Refreshing user list.';
	                // $scope.users.push(newUser);
	                $location.path('/user-list');
	            }).
	            error(function(error) {
	                $scope.status = 'Unable to insert user: ' + error.message;
	            });
	    };
	    
	}])

.controller('UserDetailCtrl', ['$scope', '$routeParams', 'UserResource', '$location', function ($scope, $routeParams, UserResource, $location) {

		// callback for ng-click 'updateUser':
		$scope.updateUser = function () {
			UserResource.update($scope.user);
		    $location.path('/user-list');
		};
		
		$scope.updateUser = function () {
			UserResource.updateUser($scope.user)
	          .success(function () {
	              $scope.status = 'Updated User! Refreshing user list.';
	          })
	          .error(function (error) {
	              $scope.status = 'Unable to update user: ' + error.message;
	          });
	    };

		// callback for ng-click 'cancel':
		$scope.cancel = function () {
		    $location.path('/user-list');
		};
		
		$scope.getUser = function (id) {
			UserResource.getUser(id)
	          .success(function (data) {
	              $scope.status = 'get User! Refreshing user detail.';
	              $scope.user = data;
	          })
	          .error(function (error) {
	              $scope.status = 'Unable to get user: ' + error.message;
	          });
	    };

//		console.log($routeParams);
//		$scope.user = UserResource.getUser({id: $routeParams.id});
//		$scope.user = UserResource.getUser($routeParams.id);
//		console.log($scope.user);
	    $scope.getUser($routeParams.id);
	}]);
