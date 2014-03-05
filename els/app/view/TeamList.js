Ext.define('AngelsSencha.view.TeamList', {

	extend : 'Ext.List',
	xtype : 'teamlist',

	requires : [ 'AngelsSencha.store.Teams' ],

	config : {
		title : 'Teams',
		itemTpl : 'n={name}, a={age}, l={level}',
		store : 'teamstore',
		onItemDisclosure : true,
	}
});
