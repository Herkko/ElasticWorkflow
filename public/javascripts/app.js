

var App;
var RaphaelElement;

var ActivityElements = new ActivityList;
var StartElements = new StartList;
var EndElements = new EndList;
var SwimlaneElements = new SwimlaneList;

window.onload = function() {

    connections = [];

    RaphaelElement = Raphael(10, 10, "100%", "100%");
    var swimlane2 = RaphaelElement.rect(20, 20, 700, 230, 1);


    App = new AppView;

<<<<<<< HEAD
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
		
		var ActivityElements = new ActivityList;
		var StartElements = new StartList;
		var EndElements = new EndList;
		var SwimlaneElements = new SwimlaneList;

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
				
				var activitySuccess = function(){
					var activityElementsView = new ElementsView({model:ActivityElements});
					activityElementsView.render();
					connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
					connections.push(RaphaelElement.connection(EndElements.at(0).get("element"), ActivityElements.at(2).get("element"), "#000"));
					connections.push(RaphaelElement.connection(ActivityElements.at(0).get("element"), ActivityElements.at(1).get("element"), "#000"));
					connections.push(RaphaelElement.connection(ActivityElements.at(1).get("element"), ActivityElements.at(2).get("element"), "#000"));
				}
				
				var startSuccess = function(){
					var startElementsView = new ElementsView({model:StartElements});
					startElementsView.render();
					connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
					connections.push(RaphaelElement.connection(EndElements.at(0).get("element"), ActivityElements.at(2).get("element"), "#000"));
					connections.push(RaphaelElement.connection(ActivityElements.at(0).get("element"), ActivityElements.at(1).get("element"), "#000"));
					connections.push(RaphaelElement.connection(ActivityElements.at(1).get("element"), ActivityElements.at(2).get("element"), "#000"));
				}
				
				var endSuccess = function(){
					var endElementsView = new ElementsView({model:EndElements});
					endElementsView.render();
					connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
					connections.push(RaphaelElement.connection(EndElements.at(0).get("element"), ActivityElements.at(2).get("element"), "#000"));
					connections.push(RaphaelElement.connection(ActivityElements.at(0).get("element"), ActivityElements.at(1).get("element"), "#000"));
					connections.push(RaphaelElement.connection(ActivityElements.at(1).get("element"), ActivityElements.at(2).get("element"), "#000"));
				}
				/*
				var swimlaneSuccess = function(){
					var swimlaneElementsView = new ElementsView({model:EndElements});
					swimlaneElementsView.render();
					connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
					connections.push(RaphaelElement.connection(EndElements.at(0).get("element"), ActivityElements.at(2).get("element"), "#000"));
					connections.push(RaphaelElement.connection(ActivityElements.at(0).get("element"), ActivityElements.at(1).get("element"), "#000"));
					connections.push(RaphaelElement.connection(ActivityElements.at(1).get("element"), ActivityElements.at(2).get("element"), "#000"));
				}
				*/
				
				ActivityElements.fetch({
					success : activitySuccess
				});
				
				StartElements.fetch({
					success: startSuccess 
				});
				
				EndElements.fetch({
					success: endSuccess 
				});
				
				/*SwimlaneElements.fetch({
					success: swimlaneSuccess
				});*/
			},
			
			handleClick: function() {
			  console.log("Something was clicked");
			},
			
			handleChange: function() {
			  console.log("Something was changed");
			}
			
				
		});
		var App = new AppView;
		
			
	 
=======
>>>>>>> Herkko
};

