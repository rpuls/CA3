/*
 * Place your global Controllers in this file
 */

angular.module('myApp.controllers', []).
        controller('AppCtrl', function () {

        })
        .controller('ShopCtrl', function ($scope, $location, $uibModal, $window, ShopService, selectedShopFac) {
            $scope.shops = [];
            $scope.selectedShop = selectedShopFac.setSelectedShop({});

            $scope.isUndefinedOrNull = function (val) {
                return angular.isUndefined(val) || val === null;
            };
            ShopService.getShops().then(
                    function (response) {
                        $scope.shops = response.data;
                    },
                    function (response) {
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
                })
                        .closed.then(function () { //sets meta tags back to scalable state
                            $window.document.getElementsByName('viewport')[0].content = "width=device-width, initial-scale=1.0, maximum-scale=3.0, user-scalable=yes";
                        });
            };
        })

        .controller('addShopCtrl', ["$location", "$http", "$scope", "$timeout", "selectedShopFac", "userAdminFactory", "filesFactory", function ($location, $http, $scope, $timeout, selectedShopFac, userAdminFactory, filesFactory) {

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
            }])

        .controller('fileCtrl', ['$scope', 'fileUpload', '$window', function ($scope, fileUpload, $window) {
                $scope.uploadFile = function () {
                    var files = $scope.myFile;

                    console.log('file is ');
                    console.dir(files);

                    var uploadUrl = "upload";
                    fileUpload.uploadFileToUrl(files, uploadUrl);
                    $window.location.href = "#/home";


                };

            }])

        .controller('catController', function ($scope, selectedCatFactory) {
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

            $scope.setSelectCategory = function (inputData) {
                selectedCatFactory.setSelectedCat(inputData);
            };

        })



        .controller('userShopCtrl', function ($scope, $location, ShopService, selectedShopFac, userFactory, filesFactory) {
            $scope.shops = [];
            $scope.message = "";
            $scope.selectedShop = selectedShopFac.setSelectedShop({});
            $scope.files = filesFactory.setFiles({});
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

            $scope.files = filesFactory.getFiles();
            console.log("FILES: " + $scope.files);
            if ($scope.files === null) {
                $scope.message = "You haven't upload pictures yet.";
            }
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

;
