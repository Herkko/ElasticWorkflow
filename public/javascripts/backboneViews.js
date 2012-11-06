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
            connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(ActivityElements.at(2).get("element"), EndElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(ActivityElements.at(0).get("element"), GatewayElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(1).get("element"), "#000"));
            connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(2).get("element"), "#000"));
        }

        var startSuccess = function() {
            var startElementsView = new ElementsView({model: StartElements});
            startElementsView.render();
            connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(ActivityElements.at(2).get("element"), EndElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(ActivityElements.at(0).get("element"), GatewayElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(1).get("element"), "#000"));
            connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(2).get("element"), "#000"));
        }

        var endSuccess = function() {
            var endElementsView = new ElementsView({model: EndElements});
            endElementsView.render();
            connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(ActivityElements.at(2).get("element"), EndElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(ActivityElements.at(0).get("element"), GatewayElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(1).get("element"), "#000"));
            connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(2).get("element"), "#000"));
        }

        var swimlaneSuccess = function() {
            var swimlaneElementsView = new ElementsView({model: SwimlaneElements});
            swimlaneElementsView.render();
            connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(ActivityElements.at(2).get("element"), EndElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(ActivityElements.at(0).get("element"), GatewayElements.at(0).get("element"), "#000"));
            connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(1).get("element"), "#000"));
            connections.push(RaphaelElement.connection(GatewayElements.at(0).get("element"), ActivityElements.at(2).get("element"), "#000"));
        }
        
        var gatewaySuccess = function(){
			var gatewayElementsView = new ElementsView({model:GatewayElements});
			gatewayElementsView.render();
		}

        ActivityElements.fetch({
            success: activitySuccess
        });

        StartElements.fetch({
            success: startSuccess
        });

        EndElements.fetch({
            success: endSuccess
        });

        SwimlaneElements.fetch({
            success: swimlaneSuccess
        });
        
        GatewayElements.fetch({
			success: gatewaySuccess
		});
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