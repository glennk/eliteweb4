Ext.define('AngelsSencha.view.TeamDetail', {
	extend : 'Ext.List',
	xtype : 'teamdetail',

	requires : [ 'AngelsSencha.store.Players' ],

	config : {
		onItemDisclosure : true,
		// store: 'playerstore',
		grouped : false,
		scrollable : 'vertical',
		itemTpl : ' jn={jerseyNum}, fn={firstname}, ln={lastname}',
		tpl : [ '... team gif here ...', '<h1>Team {name} {level}</h1>', ]
				.join(""),
		listeners : {
			activate : 'myshowTeamTab',
			disclose : 'mydisclose'
		},

	},

	mydisclose : function(item, record) {
		console.log('mydisclose' + record.get('lastname'));

		this.getParent().push({
			xtype : 'playerdetail',
			title : record.fullName(),
			data : record.getData()

		})

	},

	myshowTeamTab : function(newActiveItem, container, oldActiveItem, eOpts) {
		console.log('myshowTeamDetailTab');
		// var store = Ext.data.StoreManager.lookup('playerstore');
		// console.log('store = ' + store);
		// var results = store.queryBy(function(record){
		// var teamid = record.get('idteam');
		// if (teamid == 3) {
		// return true;
		// }
		// });
		// this.setStore({
		// data: Ext.Array.pluck(results.getRange(),'data'),
		// });
	}

});
