'use strict';

angular.module('myApp.auth')

.constant('AUTH_EVENTS', {
  notAuthenticated: 'auth-not-authenticated',
  notAuthorized: 'auth-not-authorized'
})

.constant('USER_ROLES', {
  ADMIN: 'ADMIN',
  USER: 'USER',
  public: 'public_role'
})

.constant('LOCALES', {
    'locales': {
        'es_AR': 'Español',
        'en_US': 'English'
    },
    'DEFAULT_LANGUAGE': 'es_AR',
    'LANGUAGE_PATH': '/languages',
    'I18N_PATH': '/i18n'
});
