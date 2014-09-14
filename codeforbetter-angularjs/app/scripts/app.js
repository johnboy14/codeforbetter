'use strict';

/**
 * @ngdoc overview
 * @name codeforbetterAngularjsApp
 * @description
 * # codeforbetterAngularjsApp
 *
 * Main module of the application.
 */
angular
  .module('codeforbetterAngularjsApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .when('/beds', {
        templateUrl: 'views/beds.html',
        controller: 'BedsCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
