Ext.define('AngelsSencha.model.Team', {
    extend: 'Ext.data.Model',
    
    config: {
        fields: ['id', 'name', 'age', 'level']
    },
    
    fullName: function() {
        var d = this.data,
        names = [
            d.name,
            d.level
        ];
        return names.join(" ");
    }
    
});
