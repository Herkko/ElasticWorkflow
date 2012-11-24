
workflow.models.activity = Backbone.Model.extend({
	
	//urlRoot: 'http://morning-fjord-4117.herokuapp.com/activity',
	urlRoot: 'http://localhost:9000/activity',
	
    updateModel: function() {
        this.save();
    }

});

workflow.models.start = Backbone.Model.extend({
	
	//urlRoot: 'http://morning-fjord-4117.herokuapp.com/start',
	urlRoot: 'http://localhost:9000/start',
	
    defaults: {
        cx: 10,
        cy: 10,        
    },
    
    updateModel: function() {
        this.save();
    }

});

workflow.models.relation = Backbone.Model.extend({

	//urlRoot: 'http://morning-fjord-4117.herokuapp.com/relation',
    urlRoot: 'http://localhost:9000/relation',
    
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
	
	//urlRoot: 'http://morning-fjord-4117.herokuapp.com/end',
    urlRoot: 'http://localhost:9000/end',
    
    updateModel: function() {
        this.save();
    }
});

workflow.models.swimlane = Backbone.Model.extend({
	
	//urlRoot: 'http://morning-fjord-4117.herokuapp.com/swimlane',
   urlRoot: 'http://localhost:9000/swimlane',
  
    updateModel: function() {
        this.save();
    }

});

workflow.models.gateway = Backbone.Model.extend({
	
	//urlRoot: 'http://morning-fjord-4117.herokuapp.com/gateway',
	urlRoot: 'http://localhost:9000/gateway',
	
    updateModel: function() {
        this.save();
    }

});



workflow.collections.ActivityList = Backbone.Collection.extend({
    model: workflow.models.activity,
    //url: 'http://morning-fjord-4117.herokuapp.com/activity'
	url: 'http://localhost:9000/activity'
});

workflow.collections.StartList = Backbone.Collection.extend({
    model: workflow.models.start,
    //url: 'http://morning-fjord-4117.herokuapp.com/start'
    url: 'http://localhost:9000/start'
});

workflow.collections.EndList = Backbone.Collection.extend({
    model: workflow.models.end,
    //url: 'http://morning-fjord-4117.herokuapp.com/end'
    url: 'http://localhost:9000/end'
});

workflow.collections.SwimlaneList = Backbone.Collection.extend({
    model: workflow.models.swimlane,
    //url: 'http://morning-fjord-4117.herokuapp.com/swimlane'
    url: 'http://localhost:9000/swimlane'
});

workflow.collections.GatewayList = Backbone.Collection.extend({
    model: workflow.models.gateway,
    //url: 'http://morning-fjord-4117.herokuapp.com/gateway'
   url: 'http://localhost:9000/gateway'
});

workflow.collections.RelationList = Backbone.Collection.extend({
    model: workflow.models.relation,
  // url: 'http://morning-fjord-4117.herokuapp.com/relation',
    url: 'http://localhost:9000/relation', 
    
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

function uusiActivity() {
    var activityModel = new workflow.models.activity();
	$.when(activityModel.save()).then(function() {
	    ActivityElements.add(activityModel);
		new workflow.views.ActivityView({model: activityModel})
	});
};

function uusiStart() {
	var startModel = new workflow.models.start();
	$.when(startModel.save()).then(function() {
	    StartElements.add(startModel);
		new workflow.views.StartsView({model: startModel})
	});
};

function uusiEnd() {
	var endModel = new workflow.models.end();
	$.when(endModel.save()).then(function() {
	    EndElements.add(endModel);
		new workflow.views.endsView({model: endModel})
	});
};

//add to raphaelobjects
function uusiGateway() {
	var gatewayModel = new workflow.models.gateway();
	$.when(gatewayModel.save()).then(function() {
	    GatewayElements.add(gatewayModel);
		new workflow.views.gatewayView({model: gatewayModel})
	});
};

function uusiRelation() {
	var relationModel = new workflow.models.relation();
	$.when(relationModel.save()).then(function() {
	    RelationElements.add(relationModel);
		new workflow.views.relationView({model: relationModel})
	});
}

