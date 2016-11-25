'use strict';

angular.module('myApp.shopsList', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/shopsList', {
    templateUrl: 'app/shopsList/shopsList.html',
    controller: 'ShopCtrl'
  });
}]);