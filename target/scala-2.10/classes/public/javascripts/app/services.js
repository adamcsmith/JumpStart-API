'use strict';

/* Services */

angular.module('services', [])
    .factory('userService', ['$resource', function($resource){
        return $resource("/api/users/:id", {id:'@id'});
    }])

    .factory('securityService', ['$resource', function($resource){
        var service = {};
        var sessionsApi = $resource('api/sessions');

        service.setConnectedUser = function(id, callback){
            window.Conf.userInfo.id = id;

            if(callback){
                callback();
            }
        }

        service.clearConnectedUser = function (callback) {
            sessionsApi.remove(function () {
                window.Conf.userInfo = {};
                if (callback) {
                    callback();
                }
            });
        }

        service.login = function(username, password, callback){
            sessionsApi.save({username: username, password: password}, function(response){
                service.setConnectedUser(response.data.id, callback);
            });
        }

        return service;
    }]);