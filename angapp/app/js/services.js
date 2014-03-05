'use strict';

/* Services */

angular.module('myApp.services', ['ngResource'])
    .value('version', '0.1')
.factory('Team',['$resource',
        function($resource){
            return $resource('/teams/:teamId', {teamId: '@id'}, {
                headers: { 'auth-bob': 'C3PO R2D2' },
//                query: {method:'GET', params:{teamId:''}, isArray:true},
//                remove: {method:'DELETE', params:{teamId:''} }
                update: { method: 'PUT' }
            });
        }])
.factory('Player',['$resource',
        function($resource){
            return $resource('/players/:playerId', {playerId: '@id'}, {
                update: { method: 'PUT' }
            });
        }])
.factory('LoginService', ['$resource',
        function($resource) {
            return $resource('/user/:action', {},
                {
                    authenticate: {
                        method: 'POST',
                        params: {'action' : 'authenticate'}
                    }
                })
        }])
;