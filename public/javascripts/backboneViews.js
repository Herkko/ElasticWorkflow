workflow.views.AppView = Backbone.View.extend({
    el: "elements",
});

workflow.views.ActivityView = Backbone.View.extend({
   
   
    

    initialize: function() {
              
        this.render(); 
    
        this.raphaelActivity.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelActivity;
        this.color = "#000";
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.raphaelActivity.attr({fill: this.color, stroke: this.color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.raphaelActivity.drag(move, dragger, up);
        this.raphaelText.drag(move, dragger, up);
        this.raphaelText.toBack();
        this.el = this.raphaelActivity.node;
        this.delegateEvents();
        
       	
        //binded event for mouseclick and doubleClick
        $(this.el).click(_.bind(function() { this.clicked()}, this));

       // $(this.el).dblclick(_.bind(function() { this.editFunc()}, this));

        this.raphaelActivity.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = this.raphaelActivity;

    },
            
    render: function() {
    	this.raphaelActivity = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), 100, 60, 4);

        this.raphaelText = RaphaelElement.text((this.model.get("cx") + 50), (this.model.get("cy") + 30), this.model.get("value"));
        
        //adds id to Raphael Element and stores them to list
        this.raphaelActivity.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = this.raphaelActivity;
    },
            
    clicked: function() {
        
        var raphaelActivity = this.el;

        this.model.set({cx: raphaelActivity.getAttribute("x")});
        this.model.set({cy: raphaelActivity.getAttribute("y")});
        
        this.model.updateModel();
    },

    change: function(){
        this.render();
    },
        

    editFunc: function(){
        console.log("muuttui");
       this.render;
      // EditTemplate.startEdit(this.model);
        
        console.log("hover");
        
    }        
            

});
    
workflow.views.StartsView = Backbone.View.extend({

    initialize: function() {
        this.render();
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
		
        $(this.el).click(_.bind(function() {
            this.clicked()
        }, this));

    },
    
    render: function() {
        this.raphaelStart = RaphaelElement.circle(this.model.get("cx"), this.model.get("cy"), 20);
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value"));
    },
    
    events: {

    	"click": "clicked"
    	//dblclick: doubleclicked
    },
    
    clicked: function() {
        var raphaelStart = this.el;
        
        this.model.set({cx: raphaelStart.getAttribute("x")});
        this.model.set({cy: raphaelStart.getAttribute("y")});
        this.model.updateModel();
    },

});

workflow.views.endsView = Backbone.View.extend({


    initialize: function() {
        this.render();
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

        $(this.el).click(_.bind(function() { this.clicked()}, this));

    },
            
    render: function() {
        this.raphaelEnd = RaphaelElement.circle(this.model.get("cx"), this.model.get("cy"), 20);
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value"));
    },
    
    clicked: function() {
        var raphaelEnd = this.el;

        this.model.set({cx: raphaelEnd.getAttribute("x")});
        this.model.set({cy: raphaelEnd.getAttribute("y")});
        
        this.model.updateModel();
    }

})

workflow.views.swimlanesView = Backbone.View.extend({

    initialize: function(){
        this.render();
        this.swimlane.namebox = this.swimlaneNameBox;
        this.swimlane.nametext = this.swimlaneNameText;
        this.swimlane.attr({stroke: "#000", "stroke-width": 2});
        this.swimlane.drag(resize_move, resize_start);
    },


    render: function(element) {

        this.swimlane = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), 800, 300, 1);
        this.swimlaneNameBox = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), 25, 300, 1);
        this.swimlaneNameText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value")).attr({fill: "#000000", "font-size": 18}).transform('t12,' + 300 / 2 + 'r270');

        this.swimlane.toBack();
        this.el = this.swimlane.node;

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
        this.raphaelGateway.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelGateway;
       
        var color = Raphael.getColor();
        this.raphaelGateway.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        
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
        this.raphaelGateway = RaphaelElement.path('M' + this.model.get("cx") + ',' + this.model.get("cy") + 'L' + (this.model.get("cx") - 50) + ',' + (this.model.get("cy") + 50) + 'L' + (this.model.get("cx")) + ',' + (this.model.get("cy") + 100) + 'L' + (this.model.get("cx") + 50) + ',' + (this.model.get("cy") + 50) + 'Z');
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), (this.model.get("cy") + 50), this.model.get("value"));
    },
    
    clicked: function() {
        var raphaelGateway = this.el;
      
        console.log("gateway x: " +raphaelGateway.getBBox(false).x + " y "+ raphaelGateway.getBBox(false).y)  
        
        this.model.set({cx: raphaelGateway.getBBox().x});
        this.model.set({cy: raphaelGateway.getBBox().y});
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
            
    initialize: function(modelAttribute){
        this.model = modelAttribute;
 
        for(var i = 2; i <RaphaelObjects.length; i++){
            var objekti = RaphaelObjects[i];
            var objektinNode = objekti.node.el;
            
            objektinNode.dblclick(_.bind(function() { this.editFunc()}, this));
        }
      
    },
    
   startEdit: function(modelAttribute){
       this.setModel(modelAttribute);
        this.render();
    },       
            
    setModel: function(modelAttribute){
        this.model = modelAttribute;
    },        
 
    editFunc: function(){
        console.log("mitä tahansa kliksuteltu");
    },        
            
    render: function(){
       
        var html = Mustache.render($("#edit-Template").html(), this.model);
        $.this.el.html(html);
    }        
    
})
