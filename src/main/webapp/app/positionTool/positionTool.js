'use strict';

angular.module('myApp.positionTool', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/positionTool', {
                    templateUrl: 'app/positionTool/positionTool.html',
                });
            }])
        .controller('positionCtrl', function ($scope, $location, ShopService, selectedShopFac) {
            $scope.positionCtrl = [];
            $scope.selectedShop = selectedShopFac.setSelectedShop({});
            ShopService.getShops().then(
                    function (response) {
                        $scope.shops = response.data;
                    },
                    function (response) {
                        console.log(response.data.toString());
                    });
            
            $scope.selectShop = function (shop) {
                selectedShopFac.setSelectedShop(shop);
                $location.path('/positionTool');
            };
            
        })
        .controller('placeShopCtrl', ["$location", "$http", "$scope", "$timeout", "selectedShopFac", function ($location, $http, $scope, $timeout, selectedShopFac) {

                $scope.shop = selectedShopFac.getSelectedShop();

                $scope.saveShop = function () {

                    if(angular.isUndefined($scope.shop.id)){
                        $http.post('api/shop/add', $scope.shop)
                                .success(function (data) {
                                    $timeout(function () {
                                        $location.path("#/view4");
                                    }, 100);
                                })
                                .error(function (data) {
                                    console.log("ERROR");
                                });
                    }else{
                        $http.post('api/shop/edit', $scope.shop)
                                .success(function (data) {
                                    $timeout(function () {
                                        $location.path("#/view4");
                                    }, 100);
                                })
                                .error(function (data) {
                                    console.log("ERROR");
                                });
                    }
                };

            }]);