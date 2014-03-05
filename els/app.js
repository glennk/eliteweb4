//<debug>
Ext.Loader.setPath({
    'Ext': 'touch/src',
    'AngelsSencha': 'app'
});
//</debug>

Ext.application({
    name: 'AngelsSencha',

   requires: [
        'Ext.TitleBar',
        'Ext.tab.Panel',
        'Ext.layout.Fit',
        'Ext.form.Panel',
        'Ext.form.FieldSet',
        'Ext.field.Text',
        'Ext.field.Email',
        'Ext.field.TextArea'
    ],

    controllers: [ 'Players', 'Teams' ],
    views: ['Main', 'PlayerNav', 'TeamNav'],
    stores: [ 'Players', 'Teams'],
    models: [ 'Player', 'Team'],
    
    launch: function() {
        Ext.Viewport.add({
            xtype: 'mainpanel'
        });
    }
    
//    launch: function() {
//        Ext.create("Ext.tab.Panel", {
//            fullscreen: true,
//            tabBarPosition: 'bottom',
//            items: [
//                {
//                    title: 'Home',
//                    iconCls: 'home',
//                    cls: 'home',
//                    html: [
//                        '<h1>Welcome to Sencha Touch</h1>',
//                        "<p>You're creating the Getting Started app. This demonstrates how ",
//                        "to use tabs, lists and forms to create a simple app</p>",
//                        '<h2>Sencha Touch 2</h2>'
//                    ].join("")
//                },
//                {
//                    title: 'Teams',
//                    iconCls: 'team',
//                    xclass: 'AngelsSencha.view.TeamList',
//                },
//                {
//                    title: 'Players',
//                    iconCls: 'user',
//                    xclass: 'AngelsSencha.view.PlayerList'
//                },
//                {
//                    title: 'Contact',
//                    iconCls: 'user',
//                    xtype: 'formpanel',
//                    url: 'contact.php',
//                    layout: 'vbox',
//                    items: [
//                        {
//                            xtype: 'fieldset',
//                            title: 'Contact Us',
//                            instructions: '(email address is optional)',
//                            items: [
//                                {
//                                    xtype: 'textfield',
//                                    label: 'Name'
//                                },
//                                {
//                                    xtype: 'emailfield',
//                                    label: 'Email'
//                                },
//                                {
//                                    xtype: 'textareafield',
//                                    label: 'Message'
//                                }
//                            ]
//                        },
//                        {
//                            xtype: 'button',
//                            text: 'Send',
//                            ui: 'confirm',
//                            handler: function() {
//                                this.up('formpanel').submit();
//                            }
//                        }
//                    ]
//                }
//            ]
//        });
//    }
});
