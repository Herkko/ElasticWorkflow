
workflow.models.activity = Backbone.Model.extend({
	
	urlRoot: 'http://localhost:9000/activity',
	
    updateModel: function() {
        this.save();
    }

});

workflow.models.start = Backbone.Model.extend({
    defaults: {
        cx: 10,
        cy: 10,        
    },
    
    updateModel: function() {
        this.save();
    }

});

workflow.models.relation = Backbone.Model.extend({


    
   
    initialize: function() {

        function getBackboneModelById(id) {
                    if (ActivityElements.get(id) != null)
                        return ActivityElements.get(id);
                    else if (StartElements.get(id) != null)
                        return StartElements.get(id);
                    else if (EndElements.get(id) != null)
                        return EndElements.get(id);
                    else if (SwimlaneElements.get(id) != null)
                        return SwimlaneElements.get(id);
                    else if (GatewayElements.get(id) != null)
                        return GatewayElements.get(id);
                }
        ;
        this.set({ startPointModel:  getBackboneModelById(this.get("startId")).get("element")}),   
        this.set({endPointModel:  getBackboneModelById(this.get("endId")).get("element")});
    }




});

workflow.models.end = Backbone.Model.extend({

    updateModel: function() {
        this.save();
    }
});

workflow.models.swimlane = Backbone.Model.extend({

    updateModel: function() {
        this.save();
    }

});

workflow.models.gateway = Backbone.Model.extend({

    updateModel: function() {
        this.save();
    }

});



workflow.collections.ActivityList = Backbone.Collection.extend({
    model: workflow.models.activity,
    url: 'http://morning-fjord-4117.herokuapp.com/activity',

});

workflow.collections.StartList = Backbone.Collection.extend({
    model: workflow.models.start,
    url: 'http://morning-fjord-4117.herokuapp.com/start'
});

workflow.collections.EndList = Backbone.Collection.extend({
    model: workflow.models.end,
    url: 'http://morning-fjord-4117.herokuapp.com/end'
});

workflow.collections.SwimlaneList = Backbone.Collection.extend({
    model: workflow.models.swimlane,
    url: 'http://morning-fjord-4117.herokuapp.com/swimlane'
});

workflow.collections.GatewayList = Backbone.Collection.extend({
    model: workflow.models.gateway,
    url: 'http://morning-fjord-4117.herokuapp.com/gateway'
});

workflow.collections.RelationList = Backbone.Collection.extend({
    model: workflow.models.relation,
    url: 'http://morning-fjord-4117.herokuapp.com/relation',
    
    render: function() {
        for (var i = 0; i < this.length; i++) {
            var relation = this.get(i);

            connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
        }
    }

});

function post_to_url(path, params, method) {
    method = method || "post"; // Set method to post by default, if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for (var key in params) {
        if (params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}
;

function uusActivity() {
	var activityModel = new activity({cx:100, cy:200});
	//activityElements.add(uusimodel);
	activityModel.save();
    nakyma = new ActivityView({model: activityModel});
};

function newStart() {
	var uusimodel = new start();
    var nakyma = new StartsView({model: start});
    nakyma.render();
    uusimodel.save();
    startElements.push(uusimodel);
}
;

function newEnd() {
    end = new end();
    view = new endView({model: end});
    end.save();
    endElements.push(end);
}
;

function newGateway() {
    gateway = new gateway();
    view = new gatewayView({model: gateway});
    gateway.save();
    gatewayElements.push(gateway);
}
;

function newRelation() {
    relation = new relation();
    view = new relationView({model: relation});
    relation.save();
    relationElements.push(relations);
}

