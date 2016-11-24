'use strict';

angular.module('myApp.view2', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/login', {
              templateUrl: 'app/login/login.html',
              controller: 'LogInCtrl'
            });
          }])

     .controller('LogInCtrl', ["$window","$rootScope","$location","$http","$scope","$timeout","userFactory",function($window,$rootScope,$location,$http,$scope,$timeout,userFactory) {
             
   $scope.login = function () {
            $http.post('api/login', $scope.user)
                    .success(function (data) {
                      userFactory.setUser($scope.user.username);
                      $window.sessionStorage.id_token = data.token;
                    $rootScope.$broadcast("loginEvent",{token:data.token, status:true});
                      $timeout(function(){
                          
                      $location.path("#/view1");
                      },100);
                    })
                    .error(function (data) {
                      delete $window.sessionStorage.id_token;
               $rootScope.$broadcast("loginEvent",{token:null, status:false});
//                      clearUserDetails($scope);
                    });
          };
}]);