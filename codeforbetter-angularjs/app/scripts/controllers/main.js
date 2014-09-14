'use strict';

/**
 * @ngdoc function
 * @name codeforbetterAngularjsApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the codeforbetterAngularjsApp
 */
angular.module('codeforbetterAngularjsApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
