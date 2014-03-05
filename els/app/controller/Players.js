Ext.define('AngelsSencha.controller.Players', {
	extend : 'Ext.app.Controller',

	config : {
		refs : {
			main : 'playernav'
		},
		control : {
			playerlist : {
				disclose : 'showDetail'
			}
		}
	},

	showDetail : function(list, record) {
		this.getMain().push({
			xtype : 'playerdetail',
			title : record.fullName(),
			data : record.getData()
		})
	},

	init : function() {
		console.log('Players controller init()');
		Ext.getStore('playerstore').addListener('load', this.onMainStoreLoad,
				this);
	},

	onMainStoreLoad : function(me, records, success) {
		if (success) {
			console.log('loaded player data successfully');

			var st = Ext.getStore('playerstore');
//			st.each(function(item, index, length) {
//				console.log("" + index + item.get('lastname')
//						+ item.get('idteams'));
//			});
			console.log('st count = ' + st.getCount());

		}
	},

});
