'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'satellizer',
    'myApp.auth',
    'myApp.home',
    'myApp.profile',
    'myApp.users',
    'myApp.directives',
    'myApp.translation',
    'myApp.handling-error',
    'mgcrea.ngStrap', // model http://mgcrea.github.io/angular-strap/
    'ngMessages',
    'ui.router',
    'ui.bootstrap',
    'angular.filter', // filter unique -- ui.filters -- ui.unique -- angular-ui-utils": "0.2.3" https://github.com/a8m/angular-filter#get-started
    'pascalprecht.translate', // angular-translate
    'tmh.dynamicLocale' // angular-dynamic-locale
])
    .config(['$stateProvider', '$authProvider', '$httpProvider', 'LOCALES', '$translateProvider', 'tmhDynamicLocaleProvider',
             function ($stateProvider, $authProvider, $httpProvider, LOCALES, $translateProvider, tmhDynamicLocaleProvider) {

        $stateProvider
            .state('unauthorized', {
                url: '/unauthorized',
                templateUrl: '/templates/403.html'
            })
            .state("otherwise", {
                url: "*path",
                templateUrl: "/templates/404.html"
            });
        // Parametros de configuración de satellizer
        $authProvider.authHeader = 'Authorization'; // default
        $authProvider.authToken = '';
        $authProvider.httpInterceptor = true; // Add Authorization header to HTTP request
        // https://github.com/sahat/satellizer/issues/261
        //$authProvider.tokenRoot = 'entity'; // set the token parent element if the token is not the JSON root

        $authProvider.baseUrl = 'http://localhost:8089/web-services';
        $authProvider.loginUrl = '/auth/login';
        $authProvider.signupUrl = '/auth/signup';
        $authProvider.tokenName = 'token';
        $authProvider.tokenPrefix = 'myApp';

        $authProvider.facebook({
            clientId: '853633721397866'
        });

        $authProvider.google({
            clientId: '84302306491-ff8lnfb0un9j7dgcvs5iejo9fhrv9lik.apps.googleusercontent.com'
        });

//        $translateProvider.useMissingTranslationHandlerLog();

        $translateProvider.useStaticFilesLoader({
            prefix: LOCALES.LANGUAGE_PATH + '/locale-',// path to translations files
            suffix: '.json'// suffix, currently- extension of the translations
        });
        $translateProvider.preferredLanguage(LOCALES.DEFAULT_LANGUAGE);// is applied on first load

        //$translateProvider.useSanitizeValueStrategy('sanitize');
        $translateProvider.useSanitizeValueStrategy('escapeParameters');

        // TODO: what is this for?
        $translateProvider.useLocalStorage();// saves selected language to localStorage

        tmhDynamicLocaleProvider.localeLocationPattern(LOCALES.I18N_PATH + '/angular-locale_{{locale}}.js');

    }]);
