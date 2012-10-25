	var dragger = function () {
    	  this.ox = this.type == "rect" ? this.attr("x") : this.attr("cx");
    	  this.oy = this.type == "rect" ? this.attr("y") : this.attr("cy");
    	  this.animate({"fill-opacity": .3}, 500);
      };
	
      var move = function (dx, dy) {
		 var att = this.type == "rect" ? {x: this.ox + dx, y: this.oy + dy} : {cx: this.ox + dx, cy: this.oy + dy};
		 this.attr(att);
		  for (var i = connections.length; i--;) {
		    RaphaelElement.connection(connections[i]);
		  }
		  RaphaelElement.safari();
       }; 
	
       var  up = function () {
    	   this.animate({"fill-opacity": 0}, 500);
       };
       
       var  upStart = function () {
    	   this.animate({"fill-opacity": 1}, 500);
       };

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
	
	var ActivityElements = new ActivityList;
	var StartElements = new StartList;
	var EndElements = new EndList;

	// Iterate through all the elements, render template for each element and
	// return a list of templates
	var ElementsView = Backbone.View.extend({
		
		render: function(eventName) {
			_.each(this.model.models, function(element){
			    element.render(element.toJSON());
			}, this);
			return this;
		}
	});

	var AppView = Backbone.View.extend({
		el: "body",
		
		events: {
			'click .clickable': 'handleClick',
			'change': 'handleChange'
		},
		

		// fetch the list of elements and do a render method
		initialize: function(){
			var lOptions = {};
			
			var activitySuccess = function(){
				var activityElementsView = new ElementsView({model:ActivityElements});
				activityElementsView.render();
			}
			
			var startSuccess = function(){
				var startElementsView = new ElementsView({model:StartElements});
				startElementsView.render();
			}
			
			var endSuccess = function(){
				var endElementsView = new ElementsView({model:EndElements});
				endElementsView.render();
			}
			
			ActivityElements.fetch({
				success : activitySuccess
			});
			
			StartElements.fetch({
				success: startSuccess 
			});
			
			EndElements.fetch({
				success: endSuccess 
			});
		},
		
		handleClick: function() {
		  console.log("Something was clicked");
		},
		
		handleChange: function() {
		  console.log("Something was changed");
		}
	});

	