Ext.define('AngelsSencha.model.Player', {
    extend: 'Ext.data.Model',
    
    config: {
        fields: ['id', 'firstname', 'lastname', 'email', 'phone', 'jerseyNum', 'contacts', 'team_id']
    },
    
    fullName: function() {
        var d = this.data,
        names = [
            d.firstname,
            d.lastname,
        ];
        return names.join(" ");
    }
    
});
