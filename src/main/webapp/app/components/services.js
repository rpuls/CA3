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
        .service('UploadService', function () {

            var file = {};
            file.uploadfile = function (files, $http)
            {

                var fd = new FormData();

                var url = 'api/shop/user/edit/fileupload';

                angular.forEach(files, function (file) {
                    fd.append('file', file);
                });

                //sample data
                var data = {
                    name: name,
                    type: type
                };

                fd.append("data", JSON.stringify(data));

                $http.post(url, fd, {
                    withCredentials: false,
                    headers: {
                        'Content-Type': undefined
                    },
                    transformRequest: angular.identity
                })
                        .success(function (data)
                        {
                            console.log(data);
                        })
                        .error(function (data)
                        {
                            console.log(data);
                        });
            };

        })
        
        .service('fileUploadService', function ($http, $q) {
 
        this.uploadFileToUrl = function (file, uploadUrl) {
            //FormData, object of key/value pair for form fields and values
            var fileFormData = new FormData();
            fileFormData.append('file', file);
 
            var deffered = $q.defer();
            $http.post(uploadUrl, fileFormData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
 
            }).success(function (response) {
                deffered.resolve(response);
 
            }).error(function (response) {
                deffered.reject(response);
            });
 
            return deffered.promise;
        };
    });
        ;