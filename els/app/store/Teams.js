Ext.define('AngelsSencha.store.Teams', {
    extend: 'Ext.data.Store',
    config: {
        storeId: 'teamstore',
        model: 'AngelsSencha.model.Team',
        autoLoad: true,
        proxy: {
            type: "ajax",
            //url: "resources/data/teams.json",
            url: "/teams",
            reader: {
                type: "json",
                rootProperty: ""
            }
        },
    }
});
