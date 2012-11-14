var AppView = Backbone.View.extend({
    el: "elements",
    /*
     events: {
     'click .clickable': 'handleClick',
     'change': 'handleChange'
     },
     */
    // fetch the list of elements and do a render method
    
 
    
    
//    
//    initialize: function() {
//
//        var activitySuccess = function() {
//            var activityElementsView = new ElementsView({model: ActivityElements});
//            activityElementsView.render();
//        }
//
//        var startSuccess = function() {
//            var startElementsView = new ElementsView({model: StartElements});
//            startElementsView.render();
//        }
//
//        var endSuccess = function() {
//            var endElementsView = new ElementsView({model: EndElements});
//            endElementsView.render();
//        }
//
//        var swimlaneSuccess = function() {
//            var swimlaneElementsView = new ElementsView({model: SwimlaneElements});
//            swimlaneElementsView.render();
//
//  }
//        
//        var gatewaySuccess = function(){
//			var gatewayElementsView = new ElementsView({model:GatewayElements});
//			gatewayElementsView.render();
//		}
//		
//        var relationSuccess = function() {
//                var relationElementsView = new ElementsView({model:RelationElements});
//                relationElementsView.render();
//        }
//		
//        $.when(ActivityElements.fetch({success: activitySuccess}),
//        StartElements.fetch({success: startSuccess}),
//        EndElements.fetch({success: endSuccess}),
//    SwimlaneElements.fetch({success: swimlaneSuccess}),
//        GatewayElements.fetch({success: gatewaySuccess})).then(function() {
//            RelationElements.fetch({success: relationSuccess});
//
//	
//		});
//	}
   

});

var ElementsView = Backbone.View.extend({

    render: function(eventName) {
        _.each(this.model.models, function(element) {
            element.render(element.toJSON());
        }, this);
        return this;
    }
});

var ActivityView  = Backbone.View.extend({
    
    
    render: function(element) {
      var BackBoneModeli = this.model;
      
      var raphaelActivity = RaphaelElement.rect(this.model.get("cx"), this.model.cy, 100, 60, 4);
      var color = Raphael.getColor();
      raphaelActivity.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
      raphaelActivity.drag(move, dragger, up);
      this.el = raphaelActivity.node;
     
      $(this.el).click(_.bind(function(){this.click()}, this));
      
        
  
      },
    
    click: function(){
      var raphaelActivity = this.el;

      this.model.set({cx: raphaelActivity.getAttribute("x")});
      this.model.set({cy: raphaelActivity.getAttribute("y")});
      this.model.updateModel();
    }
              
});



var StartsView = Backbone.View.extend({
     
    
    render: function(element) {
      
      
      var raphaelStart = RaphaelElement.rect(this.model.get("cx"), this.model.cy, 100, 60, 4);
      var color = Raphael.getColor();
      raphaelStart.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
      raphaelStart.drag(move, dragger, up);
      this.el = raphaelStart.node;
     
      $(this.el).click(_.bind(function(){this.click()}, this));
      
      },
    
    click: function(){
      var raphaelStart = this.el;

      this.model.set({cx: raphaelStart.getAttribute("x")});
      this.model.set({cy: raphaelStart.getAttribute("y")});
      this.model.updateModel();
    }
    
});






