'use strict';

/* https://github.com/angular/protractor/blob/master/docs/getting-started.md */

describe('my app', function () {

    browser.get('index.html');

    it('should automatically redirect to /view1 when location hash/fragment is empty', function () {
        expect(browser.getLocationAbsUrl()).toMatch("/view1");
    });


    describe('view1', function () {

        beforeEach(function () {
            browser.get('index.html#/view1');
        });


        it('should render view1 when user navigates to /view1', function () {
            expect(element.all(by.css('[ng-view] p')).first().getText()).
                toMatch(/partial for view 1/);
        });

    });


    describe('view2', function () {

        beforeEach(function () {
            browser.get('index.html#/view2');
        });


        it('should render view2 when user navigates to /view2', function () {
            expect(element.all(by.css('[ng-view] p')).first().getText()).
                toMatch(/partial for view 2/);
        });

    });

    describe('teams', function () {

        beforeEach(function () {
            browser.get('index.html#/teams');
        });


        it('should render teams when user navigates to /teams', function () {
            expect(element.all(by.css('[ng-view] p')).first().getText()).
                toMatch(/Team List View/);
        });

        it('should render teams as ng-repeat', function() {
            var cell = element(by.repeater('team in teams').row(0).column('name'));
            expect(cell.getText()).toEqual('Name: Austin Elite 15U');
        });

    });

    describe('team detail view', function() {

        beforeEach(function () {
            browser.get('index.html#/teams/e2e-tests-sample-team');
        });

        it('should render team detail view when user navigates to /teams/e2e-tests-sample-team', function () {
            expect(element.all(by.css('[ng-view] p')).first().getText()).
                toMatch(/Team Detail view/);
        });

        it('should display Austin elite 15U page', function() {
            var teamName = element(by.model('team.name'));
            expect(teamName.getAttribute('value')).toEqual('Austin Elite 15U');

        });
    });

    describe('edit team via detail view', function() {

        beforeEach(function () {
            browser.get('index.html#/teams/e2e-tests-sample-team');
        });

        it('should display Austin elite 15U page', function() {
            var teamName = element(by.model('team.name'));
            teamName.clear();
            teamName.sendKeys('Austin Elite 15U edited');

            element(by.id('save')).click();

            //expect(teamName.getAttribute('value')).toEqual('Austin Elite 15U edited');

        });
    });

    describe('players', function () {

        beforeEach(function () {
            browser.get('index.html#/players');
        });


        it('should render players when user navigates to /players', function () {
            expect(element.all(by.css('[ng-view] p')).first().getText()).
                toMatch(/Player List View/);
        });

        it('should render players as ng-repeat', function() {
            var cell = element(by.repeater('player in players').row(0).column('lastname'));
            expect(cell.getText()).toEqual('Lastname: Kronschnabl');
        });

    });

    describe('player detail view', function() {

        beforeEach(function () {
            browser.get('index.html#/players/e2e-tests-sample-player');
        });

        it('should render player detail view when user navigates to /players/e2e-tests-sample-player', function () {
            expect(element.all(by.css('[ng-view] p')).first().getText()).
                toMatch(/Player Detail view/);
        });

        it('should display Michael Kronschnabl page', function() {
            var playerLastname = element(by.model('player.lastname'));
            expect(playerLastname.getAttribute('value')).toEqual('Kronschnabl');

        });
    });

    describe('edit player via detail view', function() {

        beforeEach(function () {
            browser.get('index.html#/players/e2e-tests-sample-player');
        });

        it('should display Kronschnabl page', function() {
            var playerLastname = element(by.model('player.lastname'));
            playerLastname.clear();
            playerLastname.sendKeys('Kronschnabl - edited');

            element(by.id('save')).click();

            expect(playerLastname.getAttribute('value')).toEqual('Kronschnabl - edited');

        });
    });
});
