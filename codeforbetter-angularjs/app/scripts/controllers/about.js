'use strict';

/**
 * @ngdoc function
 * @name codeforbetterAngularjsApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the codeforbetterAngularjsApp
 */
angular.module('codeforbetterAngularjsApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
