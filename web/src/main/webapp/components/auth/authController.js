'use strict';

angular.module('myApp.auth')

.controller('HomeCtrl', [ '$scope', '$auth', '$location', function($scope, $auth, $location) {
	$scope.greetings = "Hello world";

	$scope.isAuthenticated = function() {
		return $auth.isAuthenticated();
	};
} ])

.controller('SignUpController', [ '$scope', '$auth', '$location', function($scope, $auth, $location) {
	$scope.signup = {};
	$scope.signup = function(){
        $auth.signup({
            email: $scope.signup.email,
            password: $scope.signup.password
        })
        .then(function() {
            // Si se ha registrado correctamente,
            // Podemos redirigirle a otra parte
        	console.log("signup successful");
            $location.path("/view1");
        })
        .catch(function(response) {
            // Si ha habido errores, llegaremos a esta función
        });
    };
} ])

.controller('LoginController', [ '$scope', '$auth', '$location', function($scope, $auth, $location) {
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
	        $location.path("/private");
	    })
	    .catch(function(response){
	        // Si ha habido errores llegamos a esta parte
	    });
	};
} ])

.controller('LogoutController', [ '$scope', '$auth', '$location', function($scope, $auth, $location) {
	console.log('Controller LoginController Loaded');
	$auth.logout()
	    .then(function() {
	        // Desconectamos al usuario y lo redirijimos
	        $location.path("/");
	    });
} ]);
