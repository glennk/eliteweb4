Ext.define('AngelsSencha.view.PlayerList', {

	extend : 'Ext.List',
	xtype : 'playerlist',

	requires : [ 'AngelsSencha.store.Players' ],

	config : {
		title : 'Players',
		itemTpl : 'jn={jerseNum} fn={firstname} ln={lastname} lvl={level}',
		store : 'playerstore',
		grouped : true,
		indexBar : true,
		onItemDisclosure : true,
	}
});
