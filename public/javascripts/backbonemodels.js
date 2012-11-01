
var ActivityElement = Backbone.Model.extend({


    render: function(element) {
        var activity = RaphaelElement.rect(element.cx, element.cy, 100, 60, 4);
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
        start.attr({fill: color, stroke: color, "fill-opacity": 1, "stroke-width": 2, cursor: "move"});
        start.drag(move, dragger, upStart);
    }
});

var EndElement = Backbone.Model.extend({

    render: function(element) {
        var end = RaphaelElement.circle(element.cx, element.cy, 20);
        this.set({element: end});

        var color = Raphael.getColor();
        end.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        end.drag(move, dragger, up);
    }
});

var SwimlaneElement = Backbone.Model.extend({

    render: function(element) {
        var swimlane = RaphaelElement.rect(element.cx, element.cy, 500, 300, 1);
        this.set({element: swimlane});
        swimlane.attr({fill: red, stroke: black, "stroke-width": 2});
        swimlane.toBack();
    }
});

var GatewayElement = Backbone.Model.extend({
	
	
	render: function(element) {
		
		//var gateway = RaphaelElement.rect(element.cx, element.cy, 60, 60, 1);
		var gateway = RaphaelElement.path('M' + element.cx + ',' + element.cy + 'L' + (element.cx-50) + ',' + (element.cy+50) + 'L' + (element.cx) + ',' + (element.cy+100) + 'L' + (element.cx+50) + ',' + (element.cy+50) + 'Z');
		this.set({element: gateway});
		//var gbbox = Raphael.pathBBox('M' + element.cx + ',' + element.cy + 'L' + (element.cx-50) + ',' + (element.cy+50) + 'L' + (element.cx) + ',' + (element.cy+100) + 'L' + (element.cx+50) + ',' + (element.cy+50) + 'Z');
		var color = Raphael.getColor();
		gateway.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
		gateway.drag(movePath, dragger, up);
		//gateway.animate({transform:"r45"},10);
		
		
	    
	}
});

    

var ActivityList = Backbone.Collection.extend({
    model: ActivityElement,
    url: '/json/activity'
});

var StartList = Backbone.Collection.extend({
    model: StartElement,
    url: '/json/start'
});

var EndList = Backbone.Collection.extend({
    model: EndElement,
    url: '/json/end'
});

var SwimlaneList = Backbone.Collection.extend({
    model: SwimlaneElement,
    url: '/json/swimlane'
});

var GatewayList = Backbone.Collection.extend({
	model: GatewayElement,
	url: '/json/gateway'
});



