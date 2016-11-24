'use strict';

angular.module('myApp.shopDetailsView', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/shopDetailsView', {
    templateUrl: 'app/shopDetailsView/shopDetailsView.html',
    controller: 'userShopCtrl'
  });
 }]);

