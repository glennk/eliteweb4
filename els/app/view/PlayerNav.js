Ext.define('AngelsSencha.view.PlayerNav', {

	extend : 'Ext.navigation.View',
	xtype : 'playernav',

	requires : [ 'AngelsSencha.view.PlayerList',
			'AngelsSencha.view.PlayerDetail' ],

	config : {

		tab : {
			title : 'Players',
			iconCls : 'team',
		},

		listeners : {
			activate : 'myshowPlayersTab'
		},

		items : [ {
			xtype : 'playerlist'
		} ]
	},

	myshowPlayersTab : function() {
		console.log('myshowPlayersTab');
		// Ext.getStore('playerstore').filter('lastname', 'Kronschnabl');

	}
});