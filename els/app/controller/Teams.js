Ext.define('AngelsSencha.controller.Teams', {
	extend : 'Ext.app.Controller',

	config : {
		refs : {
			main : 'teamnav',
		},
		control : {
			teamlist : {
				disclose : 'showDetail',
			}
		}
	},

	showDetail : function(list, record) {

		var team_id = record.get('id');
		console.log('push() successfully team_id: ' + team_id);
		var tt = Ext.getStore('playerstore');

		var results = tt.queryBy(function(qbrecord) {
			// console.log('queryByRecord = ' + qbrecord.get('lastname') +
			// qbrecord.get('idteams'));
			var ln = qbrecord.get('team_id');
			if (ln == team_id) {
				return true;
			}
		});

		var fs = Ext.create('Ext.data.Store', {
			model : 'AngelsSencha.model.Player',
		});

		fs.setData(Ext.Array.pluck(results.getRange(), 'data'));

		this.getMain().push({
			xtype : 'teamdetail',
			title : record.fullName(),
			store : fs,
		})
	},

	init : function() {
		console.log('Team controller init()');
		Ext.getStore('teamstore').addListener('load', this.onMainStoreLoad,
				this);
	},

	onMainStoreLoad : function(me, records, success) {
		if (success) {
			console.log('loaded team data successfully');

			var tt = Ext.getStore('teamstore');
//			tt.each(function(item, index, length) {
//				console.log("" + index + item.get('name'));
//			});
			console.log('tt count = ' + tt.getCount());
		}
	},

});
