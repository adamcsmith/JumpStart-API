'use strict';

/* Controllers */


/**
 * Controller for User Login.
 */
var LoginCtrl = ['$scope', 'securityService', '$location', function($scope, securityService, $location){
    $scope.login = function(formData){
        if(!formData){
            formData = {};
        }
        securityService.login(formData.username, formData.password, function(){
            $location.path('/home');
        })
    }

    $scope.$on('appError', function(event, errors){
        $scope.errors = errors;
    })
}];

/**
 * Controller for User Registration.  This is a 'top level' controller meaning that it should be responsible for watching
 * for and handling appError broadcasts
 */
var RegisterCtrl = ['$scope', '$location', 'userService', 'securityService', function($scope, $location, userService, securityService){
    $scope.register = function(formData){
        var user = new userService(formData);
        user.$save(function(response){
            securityService.setConnectedUser(response.data.id, function(){
                $location.path('/home');
            });

        });

    }

    $scope.$on('appError', function(event, errors){
        $scope.errors = errors;
    })
}];

/**
 * Controller for basic actions like logging out of the application and navigating to My Account page
 */
var DefaultActionsCtrl = ['$scope', '$location', 'securityService', function($scope, $location, securityService){

    /**
     * Log out of the application, clears both client and server side user credentials
     */
    $scope.logout = function(){
        securityService.clearConnectedUser(function(){
            $location.path('/');
        });


    }
}];


