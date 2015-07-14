'use strict';

angular.module('myApp.auth')

.controller('HomeCtrl', [ '$scope', '$auth', '$location', '$window', '$rootScope', function($scope, $auth, $location, $window, $rootScope) {
// $scope.currentUser = $rootScope.currentUser;
	var vm = this;
	vm.user = {};

	vm.isAuthenticated = function() {
		console.log('$auth ',$auth)
		return $auth.isAuthenticated();
	};
} ])

.controller('SignUpController', [ '$scope', '$auth', '$location', '$alert', '$rootScope', function($scope, $auth, $location, $alert, $rootScope) {
	$scope.signup = {};
	$scope.signup = function(){
        $auth.signup({
        	username: $scope.signup.username,
            email: $scope.signup.email,
            password: $scope.signup.password
        })
        .then(function(response) {
            $rootScope.currentUser = response.data.currentUser;

            // Si se ha registrado correctamente,
            // Podemos redirigirle a otra parte
        	console.log("signup successful");
            $location.path("/user-list");
            $alert({
                content: 'You have successfully sign up',
                animation: 'fadeZoomFadeDown',
                type: 'material',
                duration: 3
            });
        })
        .catch(function(response) {
            // Si ha habido errores, llegaremos a esta función
            if (typeof response.data.message === 'object') {
                angular.forEach(response.data.message, function(message) {
                  $alert({
                    content: message[0],
                    animation: 'fadeZoomFadeDown',
                    type: 'material',
                    duration: 3
                  });
                });
            } else {
                $alert({
                  content: response.data.message,
                  animation: 'fadeZoomFadeDown',
                  type: 'material',
                  duration: 3
                });
            }
        });
    };
} ])

.controller('LoginController', [ '$scope', '$auth', '$location', '$alert', '$rootScope', function($scope, $auth, $location, $alert, $rootScope) {
	$scope.login = {};
	$scope.login = function(){
	    $auth.login({
	        email: $scope.login.email,
	        password: $scope.login.password
	    })
	    .then(function(response){
            $rootScope.currentUser = response.data.currentUser;

	        // Si se ha logueado correctamente, lo tratamos aquí.
	        // Podemos también redirigirle a una ruta
	    	console.log("login successful");
	    	$location.path("/user-list");
	        $alert({
				content: 'You have successfully logged in',
				animation: 'fadeZoomFadeDown',
				type: 'material',
				duration: 3
	        });
	    })
	    .catch(function(response){
	        // Si ha habido errores llegamos a esta parte
	    	$alert({
				content: response.data.message,
				animation: 'fadeZoomFadeDown',
				type: 'material',
				duration: 3
	    	});
	    });
	};
	
	$scope.authenticate = function(provider) {
	      $auth.authenticate(provider)
	        .then(function(response) {
	        	console.log("login successful");
                $rootScope.currentUser = response.data.currentUser;

		    	$location.path("/user-list");
	          $alert({
	            content: 'You have successfully logged in',
	            animation: 'fadeZoomFadeDown',
	            type: 'material',
	            duration: 3
	          });
	        })
	        .catch(function(response) {
	          $alert({
	            content: response.data ? response.data.message : response,
	            animation: 'fadeZoomFadeDown',
	            type: 'material',
	            duration: 3
	          });
	        });
    };
} ])

.controller('LogoutController', [ '$scope', '$auth', '$location', '$alert', function($scope, $auth, $location, $alert) {
	if (!$auth.isAuthenticated()) {
        return;
    }
    $auth.logout()
		.then(function() {
			// Desconectamos al usuario y lo redirijimos
            $location.path('/login');

	        $alert({
	          content: 'You have been logged out',
	          animation: 'fadeZoomFadeDown',
	          type: 'material',
	          duration: 3
	        });
		});
} ]);
