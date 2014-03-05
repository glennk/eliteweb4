'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', [
        'ngRoute',
        'ngCookies',
        'myApp.filters',
        'myApp.services',
        'myApp.directives',
        'myApp.controllers',
        'ui.bootstrap'
    ]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'MyCtrl1'});
        $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'MyCtrl2'});

        $routeProvider.when('/teams', {templateUrl: 'partials/team-list-view.html', controller: 'TeamListCtrl'});
        $routeProvider.when('/teams/:teamId', {templateUrl: 'partials/team-detail-view.html', controller: 'TeamEditCtrl'});

        $routeProvider.when('/players', {templateUrl: 'partials/player-list-view.html', controller: 'PlayerListCtrl'});
        $routeProvider.when('/players/:playerId', {templateUrl: 'partials/player-detail-view.html', controller: 'PlayerEditCtrl'});

//  $routeProvider.when('/admin/teams', {templateUrl: 'partials/admin/team-list-view.html', controller: 'TeamListCtrl'});
//  $routeProvider.when('/admin/teams/:teamId', {templateUrl: 'partials/admin/team-detail-view.html', controller: 'TeamEditCtrl'});
        $routeProvider.when('/admin/create-team', {templateUrl: 'partials/admin/team-detail-view.html', controller: 'TeamCreateCtrl'});

        $routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: 'LoginCtrl'});

        $routeProvider.otherwise({redirectTo: '/view1'});
    }]).
    run(function ($rootScope, $http, $location, $cookieStore) {

        function mappedRole(role) {
            return 'ROLE_USER';

        }
        $rootScope.hasRole = function (role) {
            var mrole = mappedRole(role);

            console.log('role asserted: ' + mrole);
            if ($rootScope.user === undefined) {
                return false;
            }
            if ($rootScope.user.roles[mrole] === 'undefined') {
                return false;
            }

            return $rootScope.user.roles[mrole];
        };

        $rootScope.logout = function() {
            delete $rootScope.user;
            delete $http.defaults.headers.common['X-Auth-Token'];
            $cookieStore.remove('user');
            $location.path('/');
        }
//        var user = 'glenn';
//        if (user !== undefined) {
//            $rootScope.user = user;
//        }
    });
