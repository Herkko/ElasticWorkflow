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

workflow.views.AppView = Backbone.View.extend({
    el: "elements",
});

workflow.views.ActivityView = Backbone.View.extend({
   
    initialize: function() {

         this.raphaelActivity = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), 100, 60, 4);
         this.raphaelText = RaphaelElement.text((this.model.get("cx")+20), (this.model.get("cy") + 30), this.model.get("value"));
         this.raphaelActivity.attr({width: Math.max(100, 40 + this.raphaelText.getBBox().width)})
         
        //binds Raphael to EL
         this.el = this.raphaelActivity.node;
         
        //Creates pair from element to text
        this.raphaelActivity.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelActivity;
        
        this.raphaelText.attr({"font-size": 16, cursor: "move", 'text-anchor': 'start'});
        this.raphaelActivity.attr({fill: "#FFFFFF", stroke: colors.get("activity"), "stroke-width": 2, cursor: "move"});
        this.raphaelActivity.drag(move, dragger, up);
        this.raphaelText.drag(move, dragger, up);
        
        //adds id to Raphael Element and stores them to list
        this.raphaelActivity.attr({data: this.model.get("id")});
  
        //if model has already id, add to list
        if (this.model.get("id")){
            this.addToRaphaelObjects();
        }

        this.model.bind("change", function() {this.render() }, this);
        this.model.bind("sync", function() { this.addToRaphaelObjects() }, this);
        this.model.bind("destroy", function() { this.delete() }, this);

        $(this.el).mouseup(_.bind(function() { this.clicked()}, this));
        $(this.raphaelText.node).mouseup(_.bind(function() { this.clicked()}, this));
        this.render();
    },
            
    render: function() {
        this.raphaelText.attr({"cx": this.model.get("cx"), "cy": this.model.get("cy")+this.raphaelActivity.getBBox().height/2,"text":this.model.get("value") });
    	this.raphaelActivity.attr({"cx":this.model.get("cx"), "cy":this.model.get("cy"), "width": Math.max(100, 40 + this.raphaelText.getBBox().width)});


    	for (var i = connections.length-1; i >= 0; i--) {
    		RaphaelElement.connection(connections[i]);
    	}

    },
            
    addToRaphaelObjects: function(){
        RaphaelObjects[this.model.get("id")] = this.raphaelActivity;  
    },       
    clicked: function() {
        
        var raphaelActivity = this.el;
        this.model.set({cx: parseInt(raphaelActivity.getAttribute("x"))});
        this.model.set({cy: parseInt(raphaelActivity.getAttribute("y"))});
       
        if(workflow.views.editView){
            if (workflow.views.editView.startRelId) {
                var relId = workflow.views.editView.startRelId;
            }
            workflow.views.editView.undelegateEvents();
            $("#editElements").removeData().unbind(); 
        }
        workflow.views.editView = null;
        workflow.views.editView = new workflow.views.EditElementsView({model: this.model, startRelId: relId});
        this.model.save();  
    },
    
    delete: function() {
        console.log("deleting activity...");
    	this.raphaelActivity.remove();
    	this.raphaelText.remove();
   
    }
});
    
workflow.views.StartView = Backbone.View.extend({

    initialize: function() {
    	//creates new Raphael elements
        this.raphaelStart = RaphaelElement.circle(this.model.get("cx"), this.model.get("cy"), 27);
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value"));
        
        //binds Raphael to EL
        this.el = this.raphaelStart.node;
        
        //creates pair fomr element to text
        this.raphaelStart.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelStart;
        
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.raphaelStart.attr({fill: "#FFFFFF", stroke: colors.get("start"), "stroke-width": 2, cursor: "move"});
        this.raphaelStart.drag(move, dragger, up);
        this.raphaelText.drag(move, dragger, up);
        
        //adds id to Raphael Element and stores to list
        this.raphaelStart.attr({data: this.model.get("id")});
        
        if (this.model.get("id")){
            this.addToRaphaelList();
        }
        
        this.model.bind("change", this.render, this);
        this.model.bind("sync", this.addToRaphaelList, this);
        $(this.el).mouseup(_.bind(function() { this.clicked()}, this));
        $(this.raphaelText.node).mouseup(_.bind(function() { this.clicked()}, this));
    },
    
    addToRaphaelList: function(){
        RaphaelObjects[this.model.get("id")] = this.raphaelStart;
    },        
       
    render: function() {
        this.raphaelStart.attr({'x':this.model.get("cx"), 'y':this.model.get('cy')});
        this.raphaelText.attr({"cx":(this.model.get("cx")+ 50), "cy":(this.model.get("cy")+30),"text":this.model.get("value") });
    },
    
    clicked: function() {

       var raphaelStart = this.el;

        this.model.set({cx: parseInt(raphaelStart.getAttribute("cx"))});
        this.model.set({cy: parseInt(raphaelStart.getAttribute("cy"))});
   
        if(workflow.views.editView){
            if (workflow.views.editView.startRelId) {
                var relId = workflow.views.editView.startRelId;
                console.log(relId);

            }
        
        //COMPLETELY UNBIND THE VIEW
        workflow.views.editView.undelegateEvents();
        $("#editElements").removeData().unbind(); 

        }
        workflow.views.editView = null;
        workflow.views.editView = new workflow.views.EditElementsView({model: this.model, startRelId: relId});
        
        this.model.save();
    },

});

workflow.views.EndView = Backbone.View.extend({


    initialize: function() {
        this.raphaelEnd = RaphaelElement.circle(this.model.get("cx"), this.model.get("cy"), 20);
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value"));
        
        this.raphaelEnd.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelEnd;
        
        var color = "red";
        this.raphaelEnd.attr({fill: "#FFFFFF", stroke: "red", "stroke-width": 3.5, cursor: "move"});
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.raphaelEnd.drag(move, dragger, up);
        this.raphaelText.drag(move, dragger, up);
       
        this.el = this.raphaelEnd.node;
        this.raphaelEnd.attr({data: this.model.get("id")});
        
        if (this.model.get("id")){
            this.addToRaphaelList();
        }
        
        this.model.bind("change", this.render, this);
        this.model.bind("sync", this.addToRaphaelList, this);
        $(this.el).mouseup(_.bind(function() { this.clicked()}, this));
        $(this.raphaelText.node).mouseup(_.bind(function() { this.clicked()}, this));
    },
            
    addToRaphaelList: function(){
        RaphaelObjects[this.model.get("id")] = this.raphaelEnd;
    },         
            
    render: function() {
        
         
    	this.raphaelEnd.attr({"x":this.model.get("cx"), "y":this.model.get("cy") });
        this.raphaelText.attr({"cx":(this.model.get("cx")+ 50), "cy":(this.model.get("cy")+30),"text":this.model.get("value") });
    
    },
    
    clicked: function() {
        var raphaelEnd = this.el;
       
        this.model.set({cx: parseInt(raphaelEnd.getAttribute("cx"))});
        this.model.set({cy: parseInt(raphaelEnd.getAttribute("cy"))});
      
      
        if(workflow.views.editView){
            if (workflow.views.editView.startRelId) {
                var relId = workflow.views.editView.startRelId;
                
            }
        
        //COMPLETELY UNBIND THE VIEW
        workflow.views.editView.undelegateEvents();
        $("#editElements").removeData().unbind(); 

        }
        
        workflow.views.editView = null;
        workflow.views.editView = new workflow.views.EditElementsView({model: this.model, startRelId: relId});
        
        this.model.save();
    }

})

workflow.views.SwimlaneView = Backbone.View.extend({

    initialize: function(){
        this.swimlane = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), this.model.get("width"), this.model.get("height"), 1);
        this.swimlaneDragBox = RaphaelElement.rect(this.model.get("cx") + this.model.get("width") - 30, this.model.get("cy") + this.model.get("height") - 30, 30,30,1)
        this.swimlaneNameBox = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), 25, this.model.get("height"), 1)
        this.swimlaneNameText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value")).attr({fill: "#000000", "font-size": 18}).transform('t12,' + 300 / 2 + 'r270');

        this.swimlaneNameText.toBack();
        this.swimlane.toBack();
        this.el = this.swimlane.node;
        var color = "#66FFFF";
        this.swimlane.namebox = this.swimlaneNameBox;
        this.swimlane.nametext = this.swimlaneNameText;

        
        var color = Raphael.getColor();
        this.swimlane.attr({fill: color, "fill-opacity": 0.05, stroke: "#000", "stroke-width": 2});
        this.swimlaneNameBox.attr({fill: color, "fill-opacity": 0.05});
        
        this.swimlaneDragBox.attr({fill: color, "fill-opacity": 0.05});
        this.swimlaneDragBox.drag(rmove, rstart);
    	this.swimlaneDragBox.box = this.swimlane;
        this.swimlaneDragBox.nameBox = this.swimlaneNameBox;
        
        $(this.swimlaneNameBox.node).mouseup(_.bind(function() { this.clicked()}, this));
        $(this.swimlaneDragBox.node).mouseup(_.bind(function() { this.clicked()}, this));

        this.model.bind("change", this.render, this);
    },


    render: function(element) {
        
        this.swimlaneNameText.attr({"text": this.model.get("value")});

    },
            
     clicked: function() {
        var raphaelSwimlane = this.el;

        this.model.set({width: parseInt(raphaelSwimlane.getAttribute("width"))});
        this.model.set({height: parseInt(raphaelSwimlane.getAttribute("height"))});
   
        if(workflow.views.editView){
            if (workflow.views.editView.startRelId) {
                var relId = workflow.views.editView.startRelId;
               
            }
        
        //COMPLETELY UNBIND THE VIEW
        workflow.views.editView.undelegateEvents();
        $("#editElements").removeData().unbind(); 

        }
          
        workflow.views.editView = new workflow.views.EditElementsView({model: this.model});
        this.model.save();
    },
    
    
    
})

workflow.views.GatewayView = Backbone.View.extend({
 
    initialize: function() {
        
        this.raphaelGateway = RaphaelElement.path('M ' + this.model.get("cx") + ' ' + this.model.get("cy") + 'L' + (this.model.get("cx") - 50) + ' ' + (this.model.get("cy") + 50) + 'L' + (this.model.get("cx")) + ' ' + (this.model.get("cy") + 100) + 'L' + (this.model.get("cx") + 50) + ' ' + (this.model.get("cy") + 50) + 'Z');
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), (this.model.get("cy") + 50), this.model.get("value"));
        
        this.raphaelGateway.attr({data: this.model.get("id"), fill: "#FFFFFF", stroke: colors.get("gateway"), "stroke-width": 2, cursor: "move"});
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});

        this.raphaelGateway.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelGateway;
       
        this.raphaelGateway.drag(movePath, dragger, up);
        this.raphaelText.drag(movePath, dragger, up);
       
        this.el = this.raphaelGateway;     
        
        this.raphaelGateway.attr({data: this.model.get("id")});

       if(this.model.get("id")){
           this.addToRaphaelList();
       }

        this.model.bind("change", this.render, this);
        $(this.el.node).mouseup(_.bind(function() {
            this.clicked()
        }, this));
         $(this.raphaelText.node).mouseup(_.bind(function() { this.clicked()}, this));

    },
            
    addToRaphaelList: function(){
        RaphaelObjects[this.model.get("id")] = this.raphaelGateway;
    },         
            
    render: function() {
    	var temp = this.raphaelGateway.clone();
        temp.translate(this.raphaelGateway.x,this.raphaelGateway.y);
        this.raphaelText.attr({"cx":(this.model.get("cx")+ 50), "cy":(this.model.get("cy")+30),"text":this.model.get("value") });
        this.raphaelGateway.animate({path: temp.attr('path')}, 1000);
        temp.remove();
      },
    
    clicked: function() {

    	var raphaelGateway = this.el;
        var newX = this.model.get("cx") + raphaelGateway.ox;
        var newY = this.model.get("cy") + raphaelGateway.oy;
        this.model.set({cx: newX});
        this.model.set({cy: newY});
        
        if(workflow.views.editView){
            if (workflow.views.editView.startRelId) {
                var relId = workflow.views.editView.startRelId;
            }
        //COMPLETELY UNBIND THE VIEW
        workflow.views.editView.undelegateEvents();
        $("#editElements").removeData().unbind(); 

        }
        workflow.views.editView = null;
        workflow.views.editView = new workflow.views.EditElementsView({model: this.model, startRelId: relId});
        this.model.save();
        
    }
})


workflow.views.RelationView = Backbone.View.extend({
    
    initialize: function(){
        this.render();
    },
            
    render: function() {
        var startPoint = RaphaelObjects[this.model.get("startId")];
        var endPoint = RaphaelObjects[this.model.get("endId")]
        this.raphaelRelation = RaphaelElement.connection(startPoint, endPoint, "#000");
        this.model.bind("remove", function() { this.delete() }, this);
        connections.push(this.raphaelRelation);
    },
  
    delete: function() {
     	this.raphaelRelation.line.remove();
    	this.model.destroy();
    }
})

//EditBox
workflow.views.EditElementsView = Backbone.View.extend({
   
    el: $("#editElements"),
    
    events: {
        "keyup #editValue" : "editValue",
        "click #newRelationButton" : "newRelation",
	"click #deleteElementButton" : "deleteElement",
        "focusout #editValue" : "outOfFocus"
    },
             
    initialize: function(){
        if (this.options.startRelId){
            this.createRelation(); 
        }else {
           this.render();  
        }
    },       
            
    editValue: function(e){
        var newValue = $("#editValue").val();
        this.model.set({value: newValue});
        if (e.which == workflow.ENTER){
            this.model.save();
            $("#editElements").addClass("hidden");
        }
    },   
            
    focusOut: function(){
        var newValue = $("#editValue").val();
        this.model.set({value: newValue});
        this.model.save();
    },          
            
    render: function(){
    	$("#editElements").removeClass("hidden");
       var data = {
          list: this.model,
          value: this.model.get("value")        
       };
        var html = Mustache.render($("#edit-Template").html(), data);
        $("#editElements").html(html);
    },
    
    //Makes new relation startpoint
    newRelation: function(){    
        this.startRelId= this.model.get("id");
    },
            
    //Creates new relation from preDefined startPoint         
    createRelation: function(){
       var relationModel = new workflow.models.relation({"startId": this.options.startRelId ,"endId": this.model.get("id")});
       new workflow.views.RelationView({model: relationModel}); 
       relationModel.save();
    },
    
    outOfFocus: function(){
    	this.model.save();
    	$("#editElements").addClass("hidden");
    },
    
    deleteElement: function() {
      /* var removed = [];
       for(var i = 0; i < RelationElements.models.length; i++) {
         if(RelationElements.models[i].get("startId") === this.model.get("id") || RelationElements.models[i].get("endId") === this.model.get("id")) {
          console.log(RelationElements.models[i].get("startId") +"->"+RelationElements.models[i].get("endId")+ " " + this.model.get("id"));
          removed.push(RelationElements.models[i]);
       //   RaphaelElement.removeConnection(this.model.get("id"));
          connections[RelationElements.models[i].get("id")].line.remove();
          console.log(connections);
          connections.splice(RelationElements.models[i].get("id"), 1); 
           console.log(connections);
         }
       }

       RelationElements.remove(removed);
       var model = this.model;
       setTimeout(
          function () { 
           //  console.log(RelationElements);
             model.destroy();
       }, 100);
      */
      	
    }
    
})
