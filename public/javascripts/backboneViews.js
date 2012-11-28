workflow.views.AppView = Backbone.View.extend({
    el: "elements",
});

workflow.views.ActivityView = Backbone.View.extend({
   


    initialize: function() {
          //creates new Raphael elements  
         this.raphaelActivity = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), 100, 60, 4);
         this.raphaelText = RaphaelElement.text((this.model.get("cx") + 50), (this.model.get("cy") + 30), this.model.get("value"));
        
        //binds Raphael to EL
         this.el = this.raphaelActivity.node;
         
        //Creates pair from element to text
        this.raphaelActivity.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelActivity;
        
        this.color = "#000";
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.raphaelActivity.attr({fill: this.color, stroke: this.color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.raphaelActivity.drag(move, dragger, up);
        this.raphaelText.drag(move, dragger, up);
        this.raphaelText.toBack();
        
        
        //adds id to Raphael Element and stores them to list
        this.raphaelActivity.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = this.raphaelActivity;
        
        
         this.model.bind("change", this.render, this);
        $(this.el).mouseup(_.bind(function() { this.clicked()}, this));
     
    },
            
    render: function() {
        
    	this.raphaelActivity.attr({"cx":this.model.get("cx"), "cy":this.model.get("cy") });
        this.raphaelText.attr({"cx":(this.model.get("cx")+ 50), "cy":(this.model.get("cy")+30),"text":this.model.get("value") });
    
    },
            
    clicked: function() {
        
        var raphaelActivity = this.el;

        this.model.set({cx: raphaelActivity.getAttribute("x")});
        this.model.set({cy: raphaelActivity.getAttribute("y")});
   
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
        
        this.model.updateModel();
        
        
    },


});
    
workflow.views.StartsView = Backbone.View.extend({

    initialize: function() {
        this.raphaelStart = RaphaelElement.circle(this.model.get("cx"), this.model.get("cy"), 20);
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value"));
        
        
        this.raphaelStart.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelStart;
        var color = "red";
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.raphaelStart.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.raphaelStart.drag(move, dragger, up);
        this.raphaelText.drag(move, dragger, up);
        this.el = this.raphaelStart.node;

        this.raphaelStart.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = this.raphaelStart;
		
        $(this.el).mouseup(_.bind(function() {
            this.clicked()
        }, this));
        
        this.model.bind("change", this.render, this);
    },
    
    render: function() {
        
        this.raphaelStart.attr({"x":this.model.get("cx"), "y":this.model.get("cy") });
        this.raphaelText.attr({"cx":(this.model.get("cx")+ 50), "cy":(this.model.get("cy")+30),"text":this.model.get("value") });
    
    },
    
  
    
    clicked: function() {
        console.log("starttia siirretty");
       var raphaelStart = this.el;

        this.model.set({cx: raphaelStart.getAttribute("x")});
        this.model.set({cy: raphaelStart.getAttribute("y")});
   
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
        
        this.model.updateModel();
    },

});

workflow.views.endsView = Backbone.View.extend({


    initialize: function() {
        this.raphaelEnd = RaphaelElement.circle(this.model.get("cx"), this.model.get("cy"), 20);
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value"));
        
        this.raphaelEnd.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelEnd;
        
        var color = "red";
        this.raphaelEnd.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.raphaelEnd.drag(move, dragger, up);
        this.raphaelText.drag(move, dragger, up);
        
        this.el = this.raphaelEnd.node;
        this.raphaelEnd.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = this.raphaelEnd;

        $(this.el).mouseup(_.bind(function() { this.clicked()}, this));
         this.model.bind("change", this.render, this);
    },
            
    render: function() {
        
         
        this.raphaelEnd.attr({"x":this.model.get("cx"), "y":this.model.get("cy") });
        this.raphaelText.attr({"cx":(this.model.get("cx")+ 50), "cy":(this.model.get("cy")+30),"text":this.model.get("value") });
    
    },
    
    clicked: function() {
        var raphaelEnd = this.el;

        this.model.set({cx: raphaelEnd.getAttribute("x")});
        this.model.set({cy: raphaelEnd.getAttribute("y")});
        
      
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
        
        this.model.updateModel();
    }

})

workflow.views.swimlanesView = Backbone.View.extend({

    initialize: function(){
        this.swimlane = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), 800, 300, 1);
        this.swimlaneNameBox = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), 25, 300, 1);
        this.swimlaneNameText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value")).attr({fill: "#000000", "font-size": 18}).transform('t12,' + 300 / 2 + 'r270');

        this.swimlane.toBack();
        this.el = this.swimlane.node;
        this.swimlane.namebox = this.swimlaneNameBox;
        this.swimlane.nametext = this.swimlaneNameText;
        this.swimlane.attr({stroke: "#000", "stroke-width": 2});
        this.swimlane.drag(resize_move, resize_start);
   
        $(this.el).mouseup(_.bind(function() { this.clicked()}, this));
        this.model.bind("change", this.render, this);
    },


    render: function(element) {
        
        this.swimlaneNameText.attr({"text": this.model.get("value")});

    },
            
    clicked: function(){
        console.log("swimlanea klikattu");
        workflow.views.editView = new workflow.views.EditElementsView({model: this.model});
    }
    
    
    
})

workflow.views.gatewayView = Backbone.View.extend({
/*
     initialize: function(){
         this.render();
        
         this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.Raphaelgateway.attr({data: this.model.get("id")});
     },


    render: function() {
        this.Raphaelgateway = RaphaelElement.path('M' + this.model.get("cx") + ',' + this.model.get("cy") + 'L' + (this.model.get("cx") - 50) + ',' + (this.model.get("cy") + 50) + 'L' + (this.model.get("cx")) + ',' + (this.model.get("cy") + 100) + 'L' + (this.model.get("cx") + 50) + ',' + (this.model.get("cy") + 50) + 'Z');
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), (this.model.get("cy") + 50), this.model.get("value"));
       
        this.Raphaelgateway.pair = this.raphaelText;
        this.raphaelText.pair = this.Raphaelgateway;
        
        
        RaphaelObjects[this.model.get("id")] = this.Raphaelgateway;

        var color = Raphael.getColor();
        this.Raphaelgateway.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.Raphaelgateway.drag(movePath, dragger, up);
        this.raphaelText.drag(movePath, dragger, up);

    }
*/    
    initialize: function() {
        this.render();
        this.color = Raphael.getColor();
        this.raphaelGateway.attr({data: this.model.get("id"),  fill: this.color, stroke: this.color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});

        this.raphaelGateway.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelGateway;
       
        this.raphaelGateway.drag(movePath, dragger, up);
        this.raphaelText.drag(movePath, dragger, up);
       
        this.el = this.raphaelGateway;

        this.raphaelGateway.attr({data: this.model.get("id")});

        RaphaelObjects[this.model.get("id")] = this.raphaelGateway;


        $(this.el.node).click(_.bind(function() {
            this.clicked()
        }, this));


    },
            
    render: function() {
        this.raphaelGateway = RaphaelElement.path('M ' + this.model.get("cx") + ' ' + this.model.get("cy") + 'L' + (this.model.get("cx") - 50) + ' ' + (this.model.get("cy") + 50) + 'L' + (this.model.get("cx")) + ' ' + (this.model.get("cy") + 100) + 'L' + (this.model.get("cx") + 50) + ' ' + (this.model.get("cy") + 50) + 'Z');
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), (this.model.get("cy") + 50), this.model.get("value"));
    },
    
    clicked: function() {
        var raphaelGateway = this.el;
      
        console.log("gateway x: " +raphaelGateway.getBBox(false).x + " y "+ raphaelGateway.getBBox(false).y)  
        
        this.model.set({cx: raphaelGateway.getAttribute("ox")});
        this.model.set({cy: raphaelGateway.getAttribute("oy")});
        
//        this.model.set({cx: raphaelGateway.getBBox().ox});
//        this.model.set({cy: raphaelGateway.getBBox().oy});
        this.model.updateModel();
    }
})


workflow.views.relationView = Backbone.View.extend({
    
    initialize: function(){
        this.render();
    },

    render: function() {
        var startPoint = RaphaelObjects[this.model.get("startId")];
        var endPoint = RaphaelObjects[this.model.get("endId")];
        connections.push(RaphaelElement.connection(startPoint, endPoint, "#000"));
    }
})

//EditBox
workflow.views.EditElementsView = Backbone.View.extend({
   
    el: $("#editElements"),
    
    events:  {
        "keyup #editValue" : "editValue",
        "click #newRelationButton" : "newRelation"
    },
             
    initialize: function(){
        
        if (this.options.startRelId){
            this.createRelation(); 
        }else {
           this.render();  
        }
    },       
            
    editValue: function(){
        var newValue = $("#editValue").val();
        this.model.set({value: newValue});
        
    },   
       
    render: function(){
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
       new workflow.views.relationView({model: relationModel}); 
    }        
    
})
