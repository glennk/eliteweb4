'use strict';

/* Controllers */

angular.module('myApp.controllers', []).
    controller('MyCtrl1', [function () {

    }])
    .controller('MyCtrl2', [function () {

    }])
//  .controller('XTeamCtrl', ['$scope', '$http',
//        function($scope, $http) {
//            $http.get('/teams').success(function(data) {
//                $scope.teams = data;
//            });
//
//            $scope.orderProp = 'age';
//  }])
    .controller('TeamListCtrl', ['$scope', 'Team',
        function ($scope, Team) {
            $scope.teams = Team.query();
        }])
    .controller('TeamCreateCtrl', ['$scope', '$location', '$timeout', 'Team',
        function ($scope, $location, $timeout, Team) {

            $scope.save = function () {
//                var team = new Team({name: 'A', level: 'B', age: 'C'});
//                team.$save();
                Team.save($scope.team, function () {
                    $location.path('/teams');
                });
            }
        }])
    .controller('TeamEditCtrl', ['$scope', '$location', '$routeParams', 'Team',
        function ($scope, $location, $routeParams, Team) {
            $scope.team = Team.get({teamId: $routeParams.teamId}, function (team) {
            });

            $scope.destroy = function () {
                $scope.team.$remove(function () {
                    $location.path('/teams');
                });
            }

            $scope.save = function () {
                var $id = $scope.team.id;
                Team.update({id: $id}, $scope.team, function () {
                    $location.path('/teams');
                });
            }
        }])
    .controller('PlayerListCtrl', ['$scope', 'Player',
        function ($scope, Player) {
            $scope.players = Player.query();
        }])
    .controller('PlayerCreateCtrl', ['$scope', '$location', '$timeout', 'Player',
        function ($scope, $location, $timeout, Player) {

            $scope.save = function () {
                Player.save($scope.player, function () {
                    $location.path('/players');
                });
            }
        }])
    .controller('PlayerEditCtrl', ['$scope', '$location', '$routeParams', 'Player',
        function ($scope, $location, $routeParams, Player) {
            $scope.player = Player.get({playerId: $routeParams.playerId}, function (player) {
            });

            $scope.destroy = function () {
                $scope.player.$remove(function () {
                    $location.path('/players');
                });
            }

            $scope.save = function () {
                var $id = $scope.player.id;
                Player.update({id: $id}, $scope.player, function () {
                    $location.path('/players');
                });
            }
        }])
    .controller('LoginCtrl', ['$scope', '$rootScope', '$location', '$http', 'LoginService',
        function ($scope, $rootScope, $location, $http, LoginService) {

            $scope.alerts = [
                { type: 'success', msg: 'Login as letsnosh:noshing for Admin functions.' }
            ];

           $scope.login = function() {
                LoginService.authenticate({username: $scope.username, password: $scope.password}, function(user) {
                    console.log("here");
                    $rootScope.user = user;
                    $http.defaults.headers.common['X-Auth-Token'] = user.token;
                    //$cookieStore.put('user', user)
;                   $location.path('/teams');
                },
                function(data) {
                    $scope.alerts = [
                        { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
                    ];

                });
            }
        }]);