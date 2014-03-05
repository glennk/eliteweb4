Ext.define('AngelsSencha.view.PlayerDetail', {
	extend : 'Ext.Panel',

	xtype : 'playerdetail',

	config : {
		styleHtmlContent : true,
		scrollable : 'vertical',
		// <img style="width: 100%;" src="resources/{level}.png" />
		// <img class="player-photo" src="resources/aaa.jpg"/>
		tpl : [ '<p>... team gif here ...</p>',
				'<h2>fn={firstname}, ln={lastname}, jn={jerseyNum}</h2>',
				'<h2>ph={phone}</h2>',
				'<h2>em={email}</h2>',
				'<p> ... player photo here ... </p>' ]
				.join("")
	}
});
