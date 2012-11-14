
var activity = Backbone.Model.extend({
	
    render: function(element) {
    	if (!element) return raphaelActivity;
    	var i = 10;
    	var raphaelActivity = RaphaelElement.rect(element.cx, element.cy, 100, 60, 4);
        this.set({element: raphaelActivity});
        
        var raphaelText = RaphaelElement.text(element.cx + 50, element.cy + 30, element.value).attr({fill: '#383838', "font-size": 16});
       // this.set({text: raphaelText});
       // raphaelText.drag(move, dragger, up);
        
        var color = Raphael.getColor();
        raphaelActivity.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        raphaelActivity.drag(move, dragger, up);
        
        function get(){
        	return raphaelActivity;
        }
        
        return raphaelActivity;
    },
    
    save: function(attributes, options) {
      var elem = this.get("element");
      this.set({cx: elem.attr("x")});
	  this.set({cy: elem.attr("y")});
      var that = this;
      var attrs = ["element"];
      _.each(attrs, function(attr){ 
        that.unset(attr);
      });
      Backbone.Model.prototype.save.call(this, attributes, options);
      this.set({element: elem});
    }
    

});

var start = Backbone.Model.extend({

    render: function(element) {
        var start = RaphaelElement.circle(element.cx, element.cy, 20);
        this.set({element: start});
        var color = Raphael.getColor();
        start.attr({fill: color, stroke: color, "fill-opacity": 1, "stroke-width": 2, cursor: "move"});
        //start.drag(move, dragger, upStart);

		var raphaelText = RaphaelElement.text(element.cx, element.cy, element.value).attr({fill: '#383838', "font-size": 16, cx: element.cx, cy: element.cy});
		//raphaelText.drag(moveText, startText);
		
		var set = RaphaelElement.set();
		set.push(start);
		set.push(raphaelText);

    	var ox = 0;
    	var oy = 0;
    	var dragging = false;
    	
    	set.mousedown(function(event) {
    		ox = event.screenX;
   	 		oy = event.screenY;
   	 		set.attr({
        		opacity: .5
    		});
    		dragging = true;	
		});

    	$(document).mousemove(function(event) {
    		if (dragging) {
        		set.translate(event.screenX - ox, event.screenY - oy);
       	 		ox = event.screenX;
        		oy = event.screenY;
        		for (var i = connections.length; i--; ) {
            		RaphaelElement.connection(connections[i]);
        		}
        		RaphaelElement.safari();
    		}
		});

    	set.mouseup(function(event) {
    		dragging = false;
    		set.attr({
        		opacity: 1
    		});
		});
         

    },
    
    save: function(attributes, options) {
      var elem = this.get("element");
      this.set({cx: elem.attr("cx")});
	  this.set({cy: elem.attr("cy")});
      var that = this;
      var attrs = ["element"];
      _.each(attrs, function(attr){ 
        that.unset(attr);
      });
      Backbone.Model.prototype.save.call(this, attributes, options);
      this.set({element: elem});
    }
});

var relation = Backbone.Model.extend({

    render: function(element) {
	  function getBackboneModelById(id) {
	    if(ActivityElements.get(id) != null) return ActivityElements.get(id);
	    else if(StartElements.get(id) != null) return StartElements.get(id);
	    else if(EndElements.get(id) != null) return EndElements.get(id);
	    else if(SwimlaneElements.get(id) != null) return SwimlaneElements.get(id);
	    else if(GatewayElements.get(id) != null) return GatewayElements.get(id);
	  };
	
       var relation = connections.push(RaphaelElement.connection(
       		getBackboneModelById(this.get("startId")).get("element"), 	
        	getBackboneModelById(this.get("endId")).get("element"),
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
    },
    
    save: function(attributes, options) {
      var elem = this.get("element");
      this.set({cx: elem.attr("cx")});
	  this.set({cy: elem.attr("cy")});
      var that = this;
      var attrs = ["element"];
      _.each(attrs, function(attr){ 
        that.unset(attr);
      });
      Backbone.Model.prototype.save.call(this, attributes, options);
      this.set({element: elem});
    }
});

var swimlane = Backbone.Model.extend({

    render: function(element) {
        var swimlane = RaphaelElement.rect(element.cx, element.cy, 500, 300, 1);
        var swimlaneNameBox = RaphaelElement.rect(element.cx, element.cy, 25, 300, 1);
        var swimlaneNameText = RaphaelElement.text(element.cx, element.cy, element.value).attr({fill: "#000000", "font-size": 18}).transform('t12,150r270');      
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
		   
	},
	
	save: function(attributes, options) {
      var elem = this.get("element");
      this.set({cx: elem.getBBox().x + elem.getBBox().width/2});
	  this.set({cy: elem.getBBox().y});
      var that = this;
      var attrs = ["element"];
      _.each(attrs, function(attr){ 
        that.unset(attr);
      });
      Backbone.Model.prototype.save.call(this, attributes, options);
      this.set({element: elem});
    }
});

var element = Backbone.Model.extend({

});

    
var AllElementsList = Backbone.Collection.extend({

	model: element,
	url: 'http://morning-fjord-4117.herokuapp.com/element'
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
        
       render: function(){
         for (var i=0; i<this.length; i++){
            var relation = this.get(i);
               
               
            connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
         }
       },         
                
                
       makeRelations: function(){
               
       }
});

function post_to_url(path, params, method) {
    method = method || "post"; // Set method to post by default, if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
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

function newActivity() {
	post_to_url(activity);
}
