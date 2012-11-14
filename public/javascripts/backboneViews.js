var AppView = Backbone.View.extend({
    el: "elements",
    /*
     events: {
     'click .clickable': 'handleClick',
     'change': 'handleChange'
     },
     */
    // fetch the list of elements and do a render method
    
    //modifikaatio!!
    
    
    
    initialize: function() {

        var activitySuccess = function() {
            var activityElementsView = new ElementsView({model: ActivityElements});
            activityElementsView.render();
        }

        var startSuccess = function() {
            var startElementsView = new ElementsView({model: StartElements});
            startElementsView.render();
        }

        var endSuccess = function() {
            var endElementsView = new ElementsView({model: EndElements});
            endElementsView.render();
        }

        var swimlaneSuccess = function() {
            var swimlaneElementsView = new ElementsView({model: SwimlaneElements});
            swimlaneElementsView.render();

  }
        
        var gatewaySuccess = function(){
			var gatewayElementsView = new ElementsView({model:GatewayElements});
			gatewayElementsView.render();
		}
		
		var relationSuccess = function() {
			var relationElementsView = new ElementsView({model:RelationElements});
			relationElementsView.render();
		}
		
		$.when(ActivityElements.fetch({success: activitySuccess}),
		StartElements.fetch({success: startSuccess}),
		EndElements.fetch({success: endSuccess}),
	    SwimlaneElements.fetch({success: swimlaneSuccess}),
		GatewayElements.fetch({success: gatewaySuccess})).then(function() {
		    RelationElements.fetch({success: relationSuccess});

	
		});
	}
   

});

var ElementsView = Backbone.View.extend({

    render: function(eventName) {
        _.each(this.model.models, function(element) {
            element.render(element.toJSON());e
        }, this);
        return this;
    }
});

var z = 0;