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

        .controller('addShopCtrl', ["$location", "$http", "$scope", "$timeout", "selectedShopFac", "userAdminFactory", "fileUploadService", "$upload", function ($location, $http, $scope, $timeout, selectedShopFac, userAdminFactory, fileUploadService, $upload) {

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
                                    $timeout(function () {
                                        $location.path("#/shopDetailsView");
                                    }, 100);
                                })
                                .error(function (data) {
                                    console.log("ERROR");
                                });
                    }

                };

//        $scope.uploadFile = function () {
//            var file = $scope.myFile;
//            var uploadUrl = "/api/shop/upload", //Url of webservice/api/server
//                promise = fileUploadService.uploadFileToUrl(file, uploadUrl);
// 
//            promise.then(function (response) {
//                $scope.serverResponse = response;
//            }, function () {
//                $scope.serverResponse = 'An error has occurred';
//            });
//        };

                $scope.model = {};
                $scope.selectedFile = [];
                $scope.uploadProgress = 0;

                $scope.uploadFile = function () {
                    var file = $scope.selectedFile[0];
                    $scope.upload = $upload.upload({
                        url: 'api/shop/upload',
                        method: 'POST',
                        data: angular.toJson($scope.model),
                        file: file
                    }).progress(function (evt) {
                        $scope.uploadProgress = parseInt(100.0 * evt.loaded / evt.total, 10);
                    }).success(function (data) {
                        //do something
                    });
                };

                $scope.onFileSelect = function ($files) {
                    $scope.uploadProgress = 0;
                    $scope.selectedFile = $files;
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

        })

        .controller('LocationController', function ($scope, geolocationFactory) {

            $scope.$geolocation = geolocationFactory

            // basic usage
            geolocationFactory.getCurrentPosition().then(function (location) {
                $scope.location = location
            });

            // regular updates
            geolocationFactory.watchPosition({
                timeout: 100,
                maximumAge: 2,
                enableHighAccuracy: true
            });
            $scope.coords = geolocationFactory.position.coords; // this is regularly updated
            $scope.position = geolocationFactory.position.mapPosition;
            $scope.error = geolocationFactory.position.error; // this becomes truthy, and has 'code' and 'message' if an error occurs
        });
;

