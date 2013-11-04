'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', ['services', 'ngResource']).
    run(['$rootScope', '$location', 'userService', function($rootScope, $location, userService){

        $rootScope.$on('$routeChangeStart', function (event, next, current) {

            var connectedUserId = Conf.userInfo.id;

            if(connectedUserId) {
                var connectedUser = userService.get({id:connectedUserId}, function(response){
                    $rootScope.connectedUser = connectedUser;
                });

                // If logged in user is trying to access index, redirect to dashboard
                if (next.templateUrl === 'assets/partials/public.html'){
                    $location.url('/home');
                }
            } else {
                // If user is trying to access a restricted page without being logged in
                // except if index, login, or register, then redirect to login
                if (!(next.templateUrl === 'assets/partials/public.html'  ||
                    next.templateUrl === 'assets/partials/login.html' ||
                    next.templateUrl === 'assets/partials/register.html')) {
                    $location.url('/login?req='+$location.path());
                    $rootScope.authorizationErrors = [{message:'Please log in before accessing that page.'}];
                }

                // redirect index to login
                if (next.templateUrl == 'assets/partials/home.html'){
                    $location.url('/login');
                }
            }

        });




    }])

    .config(['$routeProvider', '$httpProvider', '$locationProvider', function($routeProvider, $httpProvider, $locationProvider) {
        $locationProvider.html5Mode(true);

        $routeProvider.when('/', {templateUrl: 'assets/partials/public.html'});
        $routeProvider.when('/home', {templateUrl: 'assets/partials/home.html'});
        $routeProvider.when('/login', {templateUrl: 'assets/partials/login.html', controller: LoginCtrl});
        $routeProvider.when('/register', {templateUrl: 'assets/partials/register.html', controller: RegisterCtrl});
        $routeProvider.otherwise({redirectTo: 'assets/partials/home.html'});

        $httpProvider.responseInterceptors.push('httpInterceptor');
    }])

    .factory('httpInterceptor', ['$rootScope', '$q', function ($rootScope, $q) {
        return function (promise) {
            function isErrorResponse(obj){
                return obj && obj.hasOwnProperty('error');
            }

            return promise.then(function (response) {
                if(isErrorResponse(response.data)){
                    console.log('error detected broadcasting appError');
                    $rootScope.$broadcast('appError', response.data.error);
                    return $q.reject(response.data.error);
                }
                return response;
            }, function (response) {
                // do something on error
                return $q.reject(response);
            });
        };
    }]);

