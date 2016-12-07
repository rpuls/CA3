'use strict';

/* Place your Global Services in this File */

// Demonstrate how to register services
angular.module('myApp.services', [])
        .service('InfoService', [function () {
                var info = "Hello World from a Service";
                this.getInfo = function () {
                    return info;
                };
            }]).
        service('NullCheckService', function (input) {
            if (angular.isUndefined(input)) {
                return 'N/A';
            } else {
                return input;
            }
        }).service('ShopService', function ($http) {
            var shop = {};
            shop.getShops = function () {
                return $http.get('api/shop/all'); //<--<-- rest API
            };
            shop.getUserShop = function (username) {
                var url = 'api/shop/usershop/' + username;
                return $http.get(url); //<--<-- rest API
            };
            return shop;

        })
        .service('CompressedShopService', function ($http) {
            var shop = {};
            shop.getShops = function () {
                return $http.get('api/shop/compressed'); 
            };
            return shop;
        })
        .service('ShopService', function ($http) {
            var shop = {};
            shop.getShops = function () {
                return $http.get('api/shop/all'); //<--<-- rest API
            };
            shop.getUserShop = function (username) {
                var url = 'api/shop/usershop/' + username;
                return $http.get(url); //<--<-- rest API
            };
            return shop;

        })
        .service('fileUpload', ['$http', function ($http) {
                this.uploadFileToUrl = function (files, uploadUrl) {
                    var fd = new FormData();
                    for (var i = 0; i < files.length; i++) {
                        fd.append('file', files[i]);
                    }

                    $http({
                        method: 'POST',
                        url: uploadUrl, // The URL to Post.
                        headers: {'Content-Type': undefined}, // Set the Content-Type to undefined always.
                        data: fd,
                        transformRequest: function (data, headersGetterFunction) {
                            return data;
                        }
                    })
                            .success(function (data, status) {
                                console.log("succes");
                            })
                            .error(function (data, status) {

                            });
                };
            }])
        .service('fileService', ['$http', function ($http) {

                this.getFiles = function () {
                        $http.get('upload')
                            .success(function (data) {
                                var files = data;
                                console.dir(data);
                                var fd = new FormData();

                                for (var i = 0; i < files.length; i++) {
                                    var blob = new Blob([files[i]], {type: 'undefined'});
//                                    var file =blobToFile(blob,"IMAGE"+i);
                                    fd.append('file', blob);
//                                    console.log(blob);
                                }
                                return fd;
                            })
                            .error(function (data, status) {

                            });

                };
                function blobToFile(theBlob, fileName) {
                    //A Blob() is almost a File() - it's just missing the two properties below which we will add
                    theBlob.lastModifiedDate = new Date();
                    theBlob.name = fileName;
                    return theBlob;
                }
            }]);