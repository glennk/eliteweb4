'use strict';

/* jasmine specs for controllers go here */

describe('controllers', function () {

    beforeEach(function () {
        this.addMatchers({
            toEqualData: function (expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    beforeEach(module('myApp'));
//    beforeEach(module('myApp.controllers'));
//    beforeEach(module('myApp.services'));

    describe('TeamListCtrl', function () {
        var scope, ctrl, $httpBackend;

        beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
            $httpBackend = _$httpBackend_;
            $httpBackend.expectGET('/teams')
                .respond(
                    [
                        { name: 'Austin Elite 15U', level: 'Major', age: '15U'},
                        { name: 'Austin Elite 17U', level: 'Major', age: '17U'}
                    ]
                );
            scope = $rootScope.$new();
            ctrl = $controller('TeamListCtrl', { $scope: scope});
        }));

        it('should create Teams model with 2 teams fecthed via xhr', inject(function () {
            expect(scope.teams).toEqualData([]);
            $httpBackend.flush();

            expect(scope.teams).toEqualData(
                [
                    { name: 'Austin Elite 15U', level: 'Major', age: '15U'},
                    { name: 'Austin Elite 17U', level: 'Major', age: '17U'}
                ]
            );
        }));

        it('should ....', inject(function () {
            //spec body
        }));
    });

    describe('TeamEditCtrl', function() {
        var scope, $httpBackend, ctrl,
            xyzTeamData = function() {
                return {
                    name: 'Austin Elite XYZ', level: 'Minor', age: '12U'
                }
            };

        beforeEach(inject(function(_$httpBackend_, $rootScope, $routeParams, $controller) {
            $httpBackend = _$httpBackend_;
            $httpBackend.expectGET('/teams/xyz')
                .respond(xyzTeamData());

            $routeParams.teamId = 'xyz';
            scope = $rootScope.$new();
            ctrl = $controller('TeamEditCtrl', { $scope: scope});
        }));

        it('should fetch team detail', function() {
            expect(scope.team).toEqualData({});
            $httpBackend.flush();

            expect(scope.team).toEqualData(xyzTeamData());
        });

        it('should ....', inject(function () {
            //spec body
        }));
    });

    describe('PlayerListCtrl', function () {
        var scope, ctrl, $httpBackend;

        beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
            $httpBackend = _$httpBackend_;
            $httpBackend.expectGET('/players')
                .respond(
                    [
                        { firstname: '_firstname1', lastname: '_lastname1', email: 'bob@mgail.com', phone: '512-555-1212', jerseyNum: '32'},
                        { firstname: '_firstname2', lastname: '_lastname2', email: 'bob2@mgail.com', phone: '512-555-1214', jerseyNum: '34'},
                    ]
                );
            scope = $rootScope.$new();
            ctrl = $controller('PlayerListCtrl', { $scope: scope});
        }));

        it('should create Players model with 2 players fecthed via xhr', inject(function () {
            expect(scope.players).toEqualData([]);
            $httpBackend.flush();

            expect(scope.players).toEqualData(
                [
                    { firstname: '_firstname1', lastname: '_lastname1', email: 'bob@mgail.com', phone: '512-555-1212', jerseyNum: '32'},
                    { firstname: '_firstname2', lastname: '_lastname2', email: 'bob2@mgail.com', phone: '512-555-1214', jerseyNum: '34'},
                ]
            );
        }));

        it('should ....', inject(function () {
            //spec body
        }));
    });

    describe('PlayerEditCtrl', function() {
        var scope, $httpBackend, ctrl,
            abcPlayerData = function() {
                return {
                    firstname: '_firstname1', lastname: '_lastname1', email: 'bob@mgail.com', phone: '512-555-1212', jerseyNum: '32'
                }
            };

        beforeEach(inject(function(_$httpBackend_, $rootScope, $routeParams, $controller) {
            $httpBackend = _$httpBackend_;
            $httpBackend.expectGET('/players/abc')
                .respond(abcPlayerData());

            $routeParams.playerId = 'abc';
            scope = $rootScope.$new();
            ctrl = $controller('PlayerEditCtrl', { $scope: scope});
        }));

        it('should fetch player detail', function() {
            expect(scope.player).toEqualData({});
            $httpBackend.flush();

            expect(scope.player).toEqualData(abcPlayerData());
        });

        it('should ....', inject(function () {
            //spec body
        }));
    });

});
