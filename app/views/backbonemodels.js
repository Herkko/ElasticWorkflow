			
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

	
	
	
	var ActivityList = Backbone.Collection.extend({
		model: ActivityElement,
		url: '/json/activity'
	});

	var StartList = Backbone.Collection.extend({
		model: StartElement,
		url: '/json/start'
	});


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
		
		// get all element templates and append them to html div with id
		// #elements
		render: function(){
			var activityElementsView = new ElementsView({model:ActivityElements});
			var startElementsView = new ElementsView({model:StartElements});
			startElementsView.render();
			activityElementsView.render();
		
		},

		// fetch the list of elements and do a render method
		initialize: function(){
			var lOptions = {};
			ActivityElements.fetch(lOptions);
			StartElements.fetch(lOptions);
			this.render();
		},
		
		handleClick: function() {
		  console.log("Something was clicked");
		},
		
		handleChange: function() {
		  console.log("Something was changed");
		}
	});

	