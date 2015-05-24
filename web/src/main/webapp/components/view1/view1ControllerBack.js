'use strict';

angular.module('myApp.view1-back').controller('View1Ctrl',
		[ '$scope', '$http', function($scope, $http) {
			$scope.people = [ {
				'idUser' : '1',
				'name' : 'Alan',
				'lastname' : 'null',
				'email' : 'alan@email.com',
				'username' : 'regMeet',
				'password' : 'admin',
				'telephone' : 'null',
				'mobile' : 'null'
			}, {
				'idUser' : '2',
				'name' : 'test',
				'lastname' : 'null',
				'email' : 'test@email.com',
				'username' : 'test',
				'password' : 'testing',
				'telephone' : 'null',
				'mobile' : 'null'
			} ];

		} ]);