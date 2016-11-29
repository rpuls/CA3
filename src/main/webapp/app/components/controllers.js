/*
 * Place your global Controllers in this file
 */

angular.module('myApp.controllers', []).
        controller('AppCtrl', function () {

        })
        .controller('ShopCtrl', function ($scope, $location, $uibModal, ShopService, selectedShopFac, googleFactory) {
            $scope.shops = [];
            $scope.selectedShop = selectedShopFac.setSelectedShop({});
            ShopService.getShops().then(
                    function (response) {
                        $scope.shops = response.data;
                    },
                    function (response) {
                        console.log(response.data.toString());
                    });
            $scope.editShop = function (shop) {
                selectedShopFac.setSelectedShop(shop);
                $location.path('/addShop');
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
                    templateUrl: 'app/home/shop/shop.html',
                    scope: $scope
                });
            };
        })

        .controller('addShopCtrl', ["$location", "$http", "$scope", "$timeout", "selectedShopFac", "userAdminFactory", function ($location, $http, $scope, $timeout, selectedShopFac, userAdminFactory) {

                $scope.shop = selectedShopFac.getSelectedShop();

                $scope.saveShop = function () {
                    isAdmin = userAdminFactory.getIsAdmin();
                    isUser = userAdminFactory.getIsUser();
                    if (isAdmin) {
                        if (angular.isUndefined($scope.shop.id)) {
                            $http.post('api/shop/add', $scope.shop)
                                    .success(function (data) {
                                        $timeout(function () {
                                            $location.path("#/shopDetailsView");
                                        }, 100);
                                    })
                                    .error(function (data) {
                                        console.log("ERROR");
                                    });
                        } else {
                            $http.post('api/shop/edit', $scope.shop)
                                    .success(function (data) {
                                        $timeout(function () {
                                            $location.path("#/shopDetailsView");
                                        }, 100);
                                        
          console.log("here at success post");
                                    })
                                    .error(function (data) {
                                        console.log("ERROR");
                                    });
                                    $scope.upload();
                        }
                    }
                    if (isUser) {
                        $http.post('api/shop/user/edit', $scope.shop)
                                .success(function (data) {
                                    $timeout(function () {
                                        $location.path("#/shopDetailsView");
                                    }, 100);
                                })
                                .error(function (data) {
                                    console.log("ERROR");
                                });
                    }

                };
                
         $scope.filesChanged = function(elm){
             $scope.files = elm.files;
             $scope.$apply();

$scope.upload = function(){
        var fd = new FormData();
        angular.forEach($scope.files, function(file){
            fd.append('file', file);
        });
         };
          console.log("here at upload()");
      $http.post('api/shop/upload', $scope.files,
      {
          transformRequest: angular.identity,
          headers:{
              'Content-Type': undefined
          }})
                  .success(function(data){
                      console.log(data);
          });
      };

            }])
        .controller('catController', function ($scope) {
            $scope.catList = [
                {name: 'CUCA'},
                {name: 'FOOD'},
                {name: 'TAWA'},
                {name: 'ETHN'},
                {name: 'DRIN'},
                {name: 'BEER'},
                {name: 'SEDL'},
                {name: 'MUSI'},
                {name: 'CURI'},
                {name: 'PAPE'},
                {name: 'BEBS'},
                {name: 'LESC'},
                {name: 'HINS'},
                {name: 'HOHY'},
                {name: 'CONV'},
                {name: 'HAND'},
                {name: 'SHFA'},
                {name: 'WINE'},
                {name: 'VINT'},
                {name: 'VINY'}
            ];
        })



        .controller('userShopCtrl', function ($scope, $location, $uibModal, ShopService, selectedShopFac, googleFactory, userFactory) {
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
                $location.path('/userEditShop');
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
                    templateUrl: 'app/home/shop/shop.html',
                    scope: $scope
                });
            };

        });

