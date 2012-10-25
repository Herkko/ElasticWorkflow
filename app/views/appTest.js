// testaus file

	var ActivityElement = Backbone.Model.extend({
		
		render: function(element) {
	      this.set({element: RaphaelElement.rect(element.cx, element.cy, 60, 40, 2)});
		  var activity =  RaphaelElement.rect(element.cx, element.cy, 60, 40, 2);
		  var color = Raphael.getColor();
	      activity.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
	      activity.drag(move, dragger, up);    
	  }
	});
	
	var StartElement = Backbone.Model.extend({
	
	  
	  render: function(element) {
	      this.set({element: RaphaelElement.circle(element.cx, element.cy, 20)});
	      var start = RaphaelElement.circle(element.cx, element.cy, 20);

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
	})


	var AppViewTest = Backbone.View.extend({
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
			var lHtml = startElementsView.render();
			var kHtml = activityElementsView.render();// .el;
			
			
		// $('#elements').html(lHtml);
		},

		// fetch the list of elements and do a render method
		initialize: function(){
			var lOptions = {};
			lOptions.success = this.render;
			ActivityElements.fetch(lOptions);
			StartElements.fetch(lOptions);
			
		},
		
		handleClick: function() {
		  console.log("Something was clicked");
		},
		
		handleChange: function() {
		  console.log("Something was changed");
		}
	});

	
	// Iterate through all the elements, render template for each element and
	// return a list of templates
	var ElementsView = Backbone.View.extend({
		// template: _.template($('#elementList_template').html()),
		render: function(eventName) {
			_.each(this.model.models, function(element){
				// var lTemplate = this.template(element.toJSON());
				// $(this.el).append(lTemplate);
			    element.render(element.toJSON());
			}, this);
			return this;
		}
	});
	

	
	
	
	// url defines where you can get list of json elements
;