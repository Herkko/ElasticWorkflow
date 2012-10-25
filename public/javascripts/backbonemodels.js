	var ActivityElement = Backbone.Model.extend({
		
	render: function(element) { 
	    var activity =  RaphaelElement.rect(element.cx, element.cy, 60, 40, 2);
	    this.set({element: activity});
		  
	    var color = Raphael.getColor();
	    activity.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
	    activity.drag(move, dragger, up);    
	    }
	});
	
	var StartElement = Backbone.Model.extend({
	    render: function(element) {
		var start = RaphaelElement.circle(element.cx, element.cy, 20);
		this.set({element: start});
	      
		var color = Raphael.getColor();
		start.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
		start.drag(move, dragger, up);
		}
	});

	
	var RelationElement = Backbone.Model.extend({
	  
	  
	});
	
	
	
	
	// url defines where you can get list of json elements
	var ActivityList = Backbone.Collection.extend({
		model: ActivityElement,
		url: '/json/activity'
	});

	var StartList = Backbone.Collection.extend({
		model: StartElement,
		url: '/json/start'
	});