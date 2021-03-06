/**Copyright 2012 University of Helsinki, Daria Antonova, Herkko Virolainen, Panu Klemola
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.*/

workflow.models.swimlane = Backbone.Model.extend({
     
	urlRoot: workflow.domainHost+'/swimlane',
	
	defaults: {
        'id': null,
        'modelProcessId': null,
        'elementTypeId': 1,
        'value': 'Swimlane',
        'width': 800,
        'height': 600,
    }
});

workflow.models.start = Backbone.Model.extend({
    
    urlRoot: workflow.domainHost+'/start',

    defaults: {
        'id': null,
        'modelProcessId': null,
        'elementTypeId': 2,
        'value': 'Start',
        'width': 20,
        'height': 20,
        'cx': 850,
        'cy': 110
    }    
});

workflow.models.end = Backbone.Model.extend({
 
    urlRoot: workflow.domainHost+'/end',
    
    defaults: {
        'id': null,
        'modelProcessId': null,
        'elementTypeId': 3,
        'value': 'End',
        'width': 20,
        'height': 20,
        'cx': 850,
        'cy': 160
    }
});

workflow.models.activity = Backbone.Model.extend({

    urlRoot: workflow.domainHost+'/activity',
     
    defaults: {
        'id': null,
        'modelProcessId': null,
        'elementTypeId': 4,
        'value': 'Activity',
        'width': 100,
        'height': 60,
        'cx': 830,
        'cy': 20
    }
});

workflow.models.gateway = Backbone.Model.extend({

    urlRoot: workflow.domainHost+'/gateway',
    
    defaults: {
        'id': null,
        'modelProcessId': null,
        'elementTypeId': 5,
        'value': 'Gateway',
        'width': 0,
        'height': 0,
        'cx': 880,
        'cy': 195
    }
});

workflow.models.relation = Backbone.Model.extend({
    
    urlRoot: workflow.domainHost+'/relation',
  
    defaults: {
       id: null,
       relationTypeId: 1,
       value: "Relation"
    },
    
    initialize: function() {
        this.set({ from:  getBackboneModelById(this.get("startId"))});  
        this.set({ to:  getBackboneModelById(this.get("endId"))});
    }
});

workflow.collections.ActivityList = Backbone.Collection.extend({
	url: workflow.domainHost+'/activity', 
    model: workflow.models.activity
});

workflow.collections.StartList = Backbone.Collection.extend({
    url: workflow.domainHost+'/start',
    model: workflow.models.start
});

workflow.collections.EndList = Backbone.Collection.extend({
	url: workflow.domainHost+'/end',
    model: workflow.models.end
});

workflow.collections.SwimlaneList = Backbone.Collection.extend({
    url: workflow.domainHost+'/swimlane',
    model: workflow.models.swimlane
});

workflow.collections.GatewayList = Backbone.Collection.extend({
	url: workflow.domainHost+'/gateway',
    model: workflow.models.gateway 
});

workflow.collections.RelationList = Backbone.Collection.extend({
    model: workflow.models.relation,
    url: workflow.domainHost+'/relation',
    
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
};


function uusiActivity() {
    var activityModel = new workflow.models.activity();
    ActivityElements.add(activityModel);
     new workflow.views.ActivityView({model: activityModel});
    activityModel.save();
   
   
};

function uusiStart() {
    var startModel = new workflow.models.start();
    StartElements.add(startModel);
    new workflow.views.StartView({model: startModel});
    startModel.save();
};

function uusiEnd() {
    var endModel = new workflow.models.end();
    EndElements.add(endModel);
    new workflow.views.EndView({model: endModel});
    endModel.save();
};

function uusiGateway() {
    var gatewayModel = new workflow.models.gateway();
    GatewayElements.add(gatewayModel);
    new workflow.views.GatewayView({model: gatewayModel});
    gatewayModel.save();
};

function uusiRelation() {
    var relationModel = new workflow.models.relation();
    RelationElements.add(relationModel);
    new workflow.views.RelationView({model: relationModel})
    relationModel.save();
};

