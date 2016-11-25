'use strict';

angular.module('myApp.positionTool', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/positionTool', {
                    templateUrl: 'app/positionTool/positionTool.html',
                    controller: 'positionCtrl',
                    controllerAs: 'ctrl'
                });
            }])


        .controller('positionCtrl', function ($scope, $http, $location, $timeout, ShopService, selectedShopFac) {
            $scope.buttonText = "+";
            $scope.shops = [];
            $scope.selectedShop = selectedShopFac.getSelectedShop();
            ShopService.getShops().then(
                    function (response) {
                        $scope.shops = response.data;
                    },
                    function (response) {
                        console.log(response.data.toString());
                    });

            $scope.selectShop = function (shop) {
                $scope.selectedShop = shop;
                $scope.buttonText = "Save";
            };
            $scope.placeShopMode = function(){
                $scope.buttonText = "Place";
            };
            $scope.saveShop = function (shop) {

                if (angular.isUndefined(shop.id)) {
                    $location.path('/positionTool');
                    $http.post('api/shop/add', shop)
                            .success(function (data) {
                                ShopService.getShops().then(
                                        function (response) {
                                            $scope.shops = response.data;
                                            $scope.selectedShop = $scope.shops[$scope.shops.length-1];
                                            $scope.buttonText = "+";
                                        },
                                        function (response) {
                                            console.log(response.data.toString());
                                        });
                            })
                            .error(function (data) {
                                console.log("ERROR");
                            });
                } else {
                    $http.post('api/shop/edit', shop)
                            .success(function (data) {
                                ShopService.getShops().then(
                                        function (response) {
                                            $scope.shops = response.data;
                                        },
                                        function (response) {
                                            console.log(response.data.toString());
                                        });
                            })
                            .error(function (data) {
                                console.log("ERROR");
                            });
                    $scope.selectedShop = {};
                }
            };

            $scope.placeShop = function () {
                $scope.selectedShop = selectedShopFac.getSelectedShop();
            }

        })

        .directive('draggable', function ($document, selectedShopFac) {
            return {
                //controller: 'positionCtrl',
                link: function (scope, element, controller) {
                    var startX = 0, startY = 0;
                    var dragBox = angular.element(document.querySelector('#mapDiv'));
                    var dragBoxWidth = parseInt(dragBox.css('width'));
                    var dragBoxHeight = parseInt(dragBox.css('height'));

                    element.on('mousedown', function (event) {
                        // Prevent default dragging of selected content
                        controller.selectedShop = scope.shop;  //selectedShopFac.setSelectedShop(scope.shop);
                        event.preventDefault();
                        startX = event.pageX - parseInt(element.css('left'));
                        startY = event.pageY - parseInt(element.css('top'));
                        $document.on('mousemove', mousemove);
                        $document.on('mouseup', mouseup);
                    });

                    function mousemove(event) {
                        var y = event.pageY - startY;
                        var x = event.pageX - startX;
                        element.css({
                            left: x / dragBoxWidth * 100 + '%',
                            top: y / dragBoxHeight * 100 + '%'
                        });
                    }

                    function mouseup() {
                        $document.off('mousemove', mousemove);
                        $document.off('mouseup', mouseup);
                    }
                }
            };
        });
;