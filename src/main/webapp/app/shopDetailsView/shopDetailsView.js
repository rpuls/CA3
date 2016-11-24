'use strict';

angular.module('myApp.shopDetailsView', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/shopDetailsView', {
    templateUrl: 'app/shopDetailsView/shopDetailsView.html',
    controller: 'userShopCtrl'
  });
 }])

.controller('userShopCtrl', function ($scope, $location, $uibModal, ShopService, selectedShopFac, googleFactory, userFactory){
 $scope.shops = [];
            $scope.selectedShop = selectedShopFac.setSelectedShop({});
            ShopService.getUserShop(userFactory.getUser()).then(
                    function (response) {
                        $scope.shops = response.data;
                    },
                    function (response) {
                        console.log(response.data.toString());
                    });
            $scope.editShop = function (shop) {
                selectedShopFac.setSelectedShop(shop);
                $location.path('/view3');
            };
            $scope.showDialog = function (shop) {
                $scope.selectedShop = selectedShopFac.setSelectedShop(shop);
                if (!angular.isUndefined($scope.selectedShop.googlePlaceId)) {
                    googleFactory.getOpeningHours().success(function (data) {
                        $scope.selectedShop.rating = data + " \/ 5";
                    });
                } else {
                    $scope.selectedShop.rating = "no ratings";
                }
                $uibModal.open({
                    templateUrl: 'app/view1/shop/shop.html',
                    scope: $scope
                });
            };
 
});