'use strict';

angular.module('bookingApp')
// Creating the Angular Controller
    .controller('LoginController', function($http, $scope, $state, LoginService, $rootScope, urls) {
        // method for login
        $scope.login = function() {
            console.log("Debug : Login");
            // requesting the token by usename and password
            $http({
                url : urls.LOGIN_API,
                method : "POST",
                params : {
                    email : $scope.email,
                    password : $scope.password
                }
            }).success(function(res) {
                $scope.password = null;
                // checking if the token is available in the response
                if (res.token) {
                    $scope.message = '';
                    // setting the Authorization Bearer token with JWT token
                    $http.defaults.headers.common['Authorization'] = 'Bearer ' + res.token;

                    // setting the user in AuthService
                    LoginService.user = res.user;
                    $rootScope.$broadcast('LoginSuccessful');
                    // going to the home page
                    console.log("Debug : Login Success -> Going Dashboard");
                    console.log("Debug : Login Token -> "+res.token);
                    if(res.user.role=='Admin'){
                        $state.go('dashboard');
                        //sementara buat debug ke state home semua dulu aja :3
                    }
                    else {
                        $state.go('home');
                    }
                } else {
                    // if the token is not present in the response then the
                    // authentication was not successful. Setting the error message.
                    console.log("Debug : Login Failed");
                    $scope.message = 'Authetication Failed !';
                }
            }).error(function(error) {
                // if authentication was not successful. Setting the error message.
                console.log("Debug : Login Failed");
                $scope.message = 'Authetication Failed !';
            });
        };
        $scope.logout = function(){
            LoginService.user = null;
            $rootScope.$broadcast('LogoutSuccessful');
            $state.go('home');
        };
    });
