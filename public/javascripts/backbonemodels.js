
var activity = Backbone.Model.extend({
	idAttribute: "relationId",
	
	defaults: {
		value: "Activity"
	},
	
	url: function() {
		return "activity/" + this.get("relationId");
	},
	
	updateLocation: function(element){
		var activityRaphaelElement = this.render(element);
		element.set({cx: activityRaphaelElement.attr("x")});
		element.set({cy: activityRaphaelElement.attr("y")});
	},
	
    render: function(element) {
    	if (!element) return raphaelActivity;
    	var i = 10;
    	var raphaelActivity = RaphaelElement.rect(element.cx, element.cy, 100, 60, 4);
        //this.set({element: raphaelActivity});
        var color = Raphael.getColor();
        raphaelActivity.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        raphaelActivity.drag(move, dragger, up);
        
        function get(){
        	return raphaelActivity;
        }
        
        return raphaelActivity;
    },
    
    
});

var start = Backbone.Model.extend({

    render: function(element) {
        var start = RaphaelElement.circle(element.cx, element.cy, 20);
        this.set({element: start});

        var color = Raphael.getColor();
        start.attr({fill: color, stroke: color, "fill-opacity": 1, "stroke-width": 2, cursor: "move"});
        start.drag(move, dragger, upStart);
    }
});

var relation = Backbone.Model.extend({

    render: function(element) {
        var relation = connections.push(RaphaelElement.connection(
        		findBackboneModelById(this.get("startId")), 	// TARVITAAN JOKU JOSTA L�YTYY KYSEISELL� ID:LL� VARUSTETTU ELEMENTTI
        		findBackboneModelById(this.get("endId")),
        		"#000")
        );
        this.set({element: relation});

        var color = Raphael.getColor();
    }
});

var end = Backbone.Model.extend({

    render: function(element) {
        var end = RaphaelElement.circle(element.cx, element.cy, 20);
        this.set({element: end});

        var color = Raphael.getColor();
        end.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        end.drag(move, dragger, up);
    }
});

var swimlane = Backbone.Model.extend({

    render: function(element) {
        var swimlane = RaphaelElement.rect(element.cx, element.cy, 500, 300, 1);
        this.set({element: swimlane});
        swimlane.attr({stroke: "#000", "stroke-width": 2});
        swimlane.toBack();
    }
});

var gateway = Backbone.Model.extend({
	
	
	render: function(element) {
		
		var gateway = RaphaelElement.path('M' + element.cx + ',' + element.cy + 'L' + (element.cx-50) + ',' + (element.cy+50) + 'L' + (element.cx) + ',' + (element.cy+100) + 'L' + (element.cx+50) + ',' + (element.cy+50) + 'Z');
		this.set({element: gateway});
		var color = Raphael.getColor();
		gateway.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
		gateway.drag(movePath, dragger, up);
		   
	}
});


    
var AllElementsList = Backbone.Collection.extend({
	model: start,
                
	url: '/element'
});
    
var ActivityList = Backbone.Collection.extend({
    model: activity,
    url: '/activity'
});

var StartList = Backbone.Collection.extend({
    model: start,
    url: '/start'
});

var EndList = Backbone.Collection.extend({
    model: end,
    url: '/end'
});

var SwimlaneList = Backbone.Collection.extend({
    model: swimlane,
    url: '/swimlane'
});

var GatewayList = Backbone.Collection.extend({
	model: gateway,
	url: '/gateway'
});

var RelationList = Backbone.Collection.extend({
	model: relation,
	url: '/relation'
});

