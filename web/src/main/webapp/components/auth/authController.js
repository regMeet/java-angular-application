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
    console.log('Controller SignUpController Loaded');
	$scope.signup = function(){
		console.log('Controller SignUpController Loaded---');
        $auth.signup({
            email: $scope.signup.email,
            password: $scope.signup.password
        })
        .then(function() {
            // Si se ha registrado correctamente,
            // Podemos redirigirle a otra parte
            $location.path("/private");
        })
        .catch(function(response) {
            // Si ha habido errores, llegaremos a esta función
        });
    };
} ])

.controller('LoginController', [ '$scope', '$auth', '$location', function($scope, $auth, $location) {
	$scope.login = {};
	console.log('Controller LoginController Loaded');
	$scope.login = function(){
		console.log('Controller LoginController Loaded 21');
	    $auth.login({
	        email: $scope.login.email,
	        password: $scope.login.password
	    })
	    .then(function(){
	        // Si se ha logueado correctamente, lo tratamos aquí.
	        // Podemos también redirigirle a una ruta
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
