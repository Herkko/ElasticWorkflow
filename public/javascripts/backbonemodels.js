
var activity = Backbone.Model.extend({


    updateModel: function() {
        this.save();
    }


});

var start = Backbone.Model.extend({

    updateModel: function() {
        this.save();
    }

});

var relation = Backbone.Model.extend({

    
   
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

var end = Backbone.Model.extend({

    updateModel: function() {
        this.save();
    }
});

var swimlane = Backbone.Model.extend({

    updateModel: function() {
        this.save();
    }

});

var gateway = Backbone.Model.extend({

    updateModel: function() {
        this.save();
    }

});






var ActivityList = Backbone.Collection.extend({
    model: activity,
    url: 'http://morning-fjord-4117.herokuapp.com/activity'
});

var StartList = Backbone.Collection.extend({
    model: start,
    url: 'http://morning-fjord-4117.herokuapp.com/start'
});

var EndList = Backbone.Collection.extend({
    model: end,
    url: 'http://morning-fjord-4117.herokuapp.com/end'
});

var SwimlaneList = Backbone.Collection.extend({
    model: swimlane,
    url: 'http://morning-fjord-4117.herokuapp.com/swimlane'
});

var GatewayList = Backbone.Collection.extend({
    model: gateway,
    url: 'http://morning-fjord-4117.herokuapp.com/gateway'
});

var RelationList = Backbone.Collection.extend({
    model: relation,
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

function newActivity() {
    activity = new activity();
    view = new activityView({model: activity});
    activity.save();
    activityElements.push(activity);
}
;

function newStart() {
    start = new start();
    view = new startView({model: start});
    start.save();
    startElements.push(start);
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
;
