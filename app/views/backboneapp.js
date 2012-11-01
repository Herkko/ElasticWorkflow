var RaphaelElement;
		
		$(function(){
			RaphaelElement = Raphael(10, 10, "100%", "100%");
			//create json models, fields are automatically derived from json.
			var ActivityElement = Backbone.Model.extend({
			  render: function(element) {
			    RaphaelElement.rect(element.cx, element.cy, 60, 40, 2);
			  }
			});
			
			var StartElement = Backbone.Model.extend({
			  render: function(element) {
			   RaphaelElement.circle(element.cx, element.cy, 20)
			  }
			});

			//url defines where you can get list of json elements
			var ActivityList = Backbone.Collection.extend({
				model: ActivityElement,
				url: '/json/activity'
			});
			
			
			var StartList = Backbone.Collection.extend({
				model: StartElement,
				url: '/json/start'
			});

			//create new instance of ElementList
			var ActivityElements = new ActivityList;
			var StartElements = new StartList;

			//Iterate through all the elements, render template for each element and return a list of templates
			var ElementsView = Backbone.View.extend({
				//template: _.template($('#elementList_template').html()),
				render: function(eventName) {
					_.each(this.model.models, function(element){
						//var lTemplate = this.template(element.toJSON());
						//$(this.el).append(lTemplate);
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
				
				//get all element templates and append them to html div with id #elements
				render: function(){
					var activityElementsView = new ElementsView({model:ActivityElements});
					var startElementsView = new ElementsView({model:StartElements});
					var lHtml = startElementsView.render();
					var kHtml = activityElementsView.render();//.el;
					
				//	$('#elements').html(lHtml);
				},

				//fetch the list of elements and do a render method
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

			var App = new AppView;
		});