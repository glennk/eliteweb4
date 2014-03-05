Ext.define('AngelsSencha.view.TeamNav', {

	extend : 'Ext.navigation.View',
	xtype : 'teamnav',

	requires : [ 'AngelsSencha.view.TeamList', 'AngelsSencha.view.TeamDetail',
			'AngelsSencha.view.PlayerDetail' ],

	config : {

		tab : {
			title : 'Teams',
			iconCls : 'star',
		},

		listeners : {
			activate : 'myshowTeamTab'
		},

		items : [ {
			xtype : 'teamlist',
			handler : function() {
				// use the push() method to push another view. It works much
				// like
				// add() or setActiveItem(). it accepts a view instance, or you
				// can give it
				// a view config.
				view.push({
					title : 'Second',
					html : 'Second view!'
				});
			}

		} ]
	},

	myshowTeamTab : function(newActiveItem, container, oldActiveItem, eOpts) {
		console.log('myshowTeamTab' + newActiveItem.toString());

	}

});
