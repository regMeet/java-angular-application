'use strict';

angular.module('myApp.auth')

.controller('HomeCtrl', [ '$scope', '$auth', '$location', function($scope, $auth, $location) {
	$scope.greetings = "Hello world";

	$scope.isAuthenticated = function() {
		return $auth.isAuthenticated();
	};
} ])

.controller('SignUpController', [ '$scope', '$auth', '$location', '$alert', function($scope, $auth, $location, $alert) {
	$scope.signup = {};
	$scope.signup = function(){
        $auth.signup({
        	username: $scope.signup.username,
            email: $scope.signup.email,
            password: $scope.signup.password
        })
        .then(function() {
            // Si se ha registrado correctamente,
            // Podemos redirigirle a otra parte
        	console.log("signup successful");
            $location.path("/view1");
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

.controller('LoginController', [ '$scope', '$auth', '$location', '$alert', function($scope, $auth, $location, $alert) {
	$scope.login = {};
	$scope.login = function(){
	    $auth.login({
	        email: $scope.login.email,
	        password: $scope.login.password
	    })
	    .then(function(){
	        // Si se ha logueado correctamente, lo tratamos aquí.
	        // Podemos también redirigirle a una ruta
	    	console.log("login successful");
	    	$location.path("/view1");
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
	        .then(function() {
	        	console.log("login successful");
		    	$location.path("/view1");
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
	if ($auth.isAuthenticated()) {
		console.log("logout successful");
    }
    $auth.logout()
    .then(function() {
        $alert({
          content: 'You have been logged out',
          animation: 'fadeZoomFadeDown',
          type: 'material',
          duration: 3
        });
    });
} ]);

/*
 * controller('LogoutController', [ '$scope', '$auth', '$location',
 * function($scope, $auth, $location) { console.log('Controller LoginController
 * Loaded'); $auth.logout() .then(function() { // Desconectamos al usuario y lo
 * redirijimos $location.path("/"); });
 */