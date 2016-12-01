/*
 * Place your global Controllers in this file
 */

angular.module('myApp.controllers', []).
        controller('AppCtrl', function () {

        })
        .controller('ShopCtrl', function ($scope, $location, $uibModal, ShopService, selectedShopFac) {
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
                $uibModal.open({
                    templateUrl: 'app/home/shop/shop.html',
                    scope: $scope
                });
            };
        })

        .controller('addShopCtrl', ["$location", "$http", "$scope", "$timeout", "selectedShopFac", "userAdminFactory","filesFactory", function ($location, $http, $scope, $timeout, selectedShopFac, userAdminFactory, filesFactory) {

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
                                    })
                                    .error(function (data) {
                                        console.log("ERROR");
                                    });
                        }
                    }
                    if (isUser) {
                        $http.post('api/shop/user/edit', $scope.shop)
                                .success(function (data) {
                                        $scope.upload();
                                    $timeout(function () {
                                        $location.path("#/shopDetailsView");
                                    }, 100);
                                })
                                .error(function (data) {
                                    console.log("ERROR");
                                });
                    }

                };
                
$scope.upload = function(){
        var fd = new FormData();
        angular.forEach($scope.files, function(file){
            fd.append('file', file);
        });
        filesFactory.setFiles($scope.files);
          console.log($scope.files);
          console.log("fd: " + fd);
      $http.post('api/shop/upload', fd,
      {
          transformRequest: angular.identity,
          headers:{
              'Content-Type': "multipart/form-data"
          }
      }
              )
                  .success(function(data){
                      console.log(data);
              
          });
         };
            }])
        .controller('catController', function ($scope) {
            $scope.filterOptions = {
                
                categories: [
                    {id: '1', name: 'CUCA'},
                    {id: '2', name: 'FOOD'},
                    {id: '3', name: 'TAWA'},
                    {id: '4', name: 'ETHN'},
                    {id: '5', name: 'DRIN'},
                    {id: '6', name: 'BEER'},
                    {id: '7', name: 'SEDL'},
                    {id: '8', name: 'MUSI'},
                    {id: '9', name: 'CURI'},
                    {id: '10', name: 'PAPE'},
                    {id: '11', name: 'BEBS'},
                    {id: '12', name: 'LESC'},
                    {id: '13', name: 'HINS'},
                    {id: '14', name: 'HOHY'},
                    {id: '15', name: 'CONV'},
                    {id: '16', name: 'HAND'},
                    {id: '17', name: 'SHFA'},
                    {id: '18', name: 'WINE'},
                    {id: '19', name: 'VINT'},
                    {id: '20', name: 'VINY'},
                    {id: '21', name: 'Show All'}
                ]
            };

            $scope.filterItem = {
                category: $scope.filterOptions.categories[20]
            };

        })



        .controller('userShopCtrl',function ($scope, $location, ShopService, selectedShopFac, userFactory, filesFactory) {
            $scope.shops = [];
            $scope.selectedShop = selectedShopFac.setSelectedShop({});
            $scope.files = filesFactory.getFiles();
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
        })

        .controller('LocationController', function ($scope, geolocationFactory) {

            $scope.geolocation = geolocationFactory;

            // basic usage
            geolocationFactory.getCurrentPosition().then(function (location) {
                $scope.location = location;
            });

            // regular updates
            geolocationFactory.watchPosition({
                timeout: 100,
                maximumAge: 2,
                enableHighAccuracy: true
            });
            $scope.coords = geolocationFactory.position.coords; // this is regularly updated
            $scope.error = geolocationFactory.position.error; // this becomes truthy, and has 'code' and 'message' if an error occurs
        });


