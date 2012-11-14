var AppView = Backbone.View.extend({
    el: "elements",
    /*
     events: {
     'click .clickable': 'handleClick',
     'change': 'handleChange'
     },
     */
    // fetch the list of elements and do a render method
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
<<<<<<< HEAD
        }

        var gatewaySuccess = function() {
            var gatewayElementsView = new ElementsView({model: GatewayElements});
            gatewayElementsView.render();
        }

        $.when(ActivityElements.fetch({success: activitySuccess}),
                StartElements.fetch({success: startSuccess}),
                EndElements.fetch({success: endSuccess}),
                SwimlaneElements.fetch({success: swimlaneSuccess}),
                GatewayElements.fetch({success: gatewaySuccess})).then(function() {
                    connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
                    connFections.push(RaphaelElement.connection(ActivityElements.at(2).get("element"), EndElements.at(0).get("element"), "#000"));
                    connections.push(RaphaelElement.connection(ActivityElements.at(0).get("element"), GatewayElements.at(0).get("element"), "#000"));
                    connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(1).get("element"), "#000"));
                    connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(2).get("element"), "#000"));

        });
=======
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
		 //	connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), EndElements.at(0).get("element"), "#000"));
            //connections.push(RaphaelElement.connection(ActivityElements.at(2).get("element"), EndElements.at(0).get("element"), "#000"));
            //connections.push(RaphaelElement.connection(ActivityElements.at(0).get("element"), GatewayElements.at(0).get("element"), "#000"));
            //connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(1).get("element"), "#000"));
            //connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(2).get("element"), "#000"));
	
		});
	}
    /*
    , <- MUISTA
    
    handleClick: function() {
        alert("Klikkaus");
>>>>>>> b6f1e5fc2761bd31eeac682d3bcc6ae8b9844942
    },
    
   init: function() {
        $.get('elements/all.json', function(data) {
            StartElements.reset(data['Start']),
            RelationElements.reset(['relations']),
            EndElements.reset(data['End']),
            SwimlaneElements.reset(['swimlane']),
            GatewayElements.reset(['gateway']),
            ActivityElements.reset(['Activity'])
            RelationElements.makeConnections();
            // when all resetted make relations and render.
            
            
        })
    }
    /*
     , <- MUISTA
     
     handleClick: function() {
     alert("Klikkaus");
     },
     handleChange: function() {
     alert("Muutos");
     }
     */

});

var ElementsView = Backbone.View.extend({

    render: function(eventName) {
        _.each(this.model.models, function(element) {
            element.render(element.toJSON());
        }, this);
        return this;
    }
});