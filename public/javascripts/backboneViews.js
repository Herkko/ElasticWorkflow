var AppView = Backbone.View.extend({
    el: "elements",
});



var ActivityView = Backbone.View.extend({


    initialize: function() {
        this.render();
        this.raphaelActivity.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelActivity;
        var color = "#000";
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.raphaelActivity.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.raphaelActivity.drag(move, dragger, up);
        this.raphaelText.drag(move, dragger, up);

        this.el = this.raphaelActivity.node;
        $(this.el).click(_.bind(function() {
            this.click()
        }, this));

        this.raphaelActivity.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = this.raphaelActivity;
    },
    render: function() {
        this.raphaelActivity = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), 100, 60, 4);
        this.raphaelText = RaphaelElement.text((this.model.get("cx") + 50), (this.model.get("cy") + 30), this.model.get("value"));

    },
    click: function() {
        var raphaelActivity = this.el;

        this.model.set({cx: raphaelActivity.getAttribute("x")});
        this.model.set({cy: raphaelActivity.getAttribute("y")});
        this.model.updateModel();
    }

});

var StartsView = Backbone.View.extend({


    initialize: function() {
        this.render();
        this.raphaelStart.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelStart;
        var color = "red";
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.raphaelStart.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.raphaelStart.drag(move, dragger, up);
        this.raphaelText.drag(move, dragger, up);
        this.el = this.raphaelStart.node;

        this.raphaelStart.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = this.raphaelStart;

        $(this.el).click(_.bind(function() {
            this.click()
        }, this));

    },
    render: function() {


        this.raphaelStart = RaphaelElement.circle(this.model.get("cx"), this.model.get("cy"), 20);
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value"));
        
//		  raphaelText.drag(moveText, startText);

//        var set = RaphaelElement.set();
//        set.push(start);
//        set.push(raphaelText);
//
//        this.el = set.node;
//  	  $(this.el).click(_.bind(function(){this.click()}, this));

//        var ox = 0;
//        var oy = 0;
//        var dragging = false;
//
//        set.mousedown(function(event) {
//
//            ox = event.screenX;
//            oy = event.screenY;
//            set.attr({
//                opacity: .5
//            });
//            dragging = true;
//        });
//
//        $(document).mousemove(function(event) {
//            if (dragging) {
//                set.translate(event.screenX - ox, event.screenY - oy);
//                ox = event.screenX;
//                oy = event.screenY;
//                for (var i = connections.length; i--; ) {
//                    RaphaelElement.connection(connections[i]);
//                }
//                RaphaelElement.safari();
//            }
//        });
//
//        set.mouseup(function(event) {
//            dragging = false;
//            set.attr({
//                opacity: 1
//            });
//        });

    },
    click: function() {
        var raphaelStart = this.el;

        this.model.set({cx: raphaelStart.getAttribute("x")});
        this.model.set({cy: raphaelStart.getAttribute("y")});
        this.model.updateModel();
    }

});

var endsView = Backbone.View.extend({


    initialize: function() {
        this.render();
        this.raphaelEnd.pair = this.raphaelText;
        this.raphaelText.pair = this.raphaelEnd;
        var color = "red";
        this.raphaelEnd.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.raphaelEnd.drag(move, dragger, up);
        this.raphaelText.drag(move, dragger, up);
        this.el = this.raphaelEnd.node;
        this.raphaelEnd.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = this.raphaelEnd;

        $(this.el).click(_.bind(function() {
            this.click()
        }, this));

    },
            
    render: function() {

        this.raphaelEnd = RaphaelElement.circle(this.model.get("cx"), this.model.get("cy"), 20);
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value"));
        // raphaelText.drag(moveText, startText);

//        var set = RaphaelElement.set();
//        set.push(start);
//        set.push(this.raphaelText);
//
//        var ox = 0;
//        var oy = 0;
//        var dragging = false;
//
//        set.mousedown(function(event) {
//            ox = event.screenX;
//            oy = event.screenY;
//            set.attr({
//                opacity: .5
//            });
//            dragging = true;
//        });
//
//        $(document).mousemove(function(event) {
//            if (dragging) {
//                set.translate(event.screenX - ox, event.screenY - oy);
//                ox = event.screenX;
//                oy = event.screenY;
//                for (var i = connections.length; i--; ) {
//                    RaphaelElement.connection(connections[i]);
//                }
//                RaphaelElement.safari();
//            }
//        });
//
//        set.mouseup(function(event) {
//            dragging = false;
//            set.attr({
//                opacity: 1
//            });
//        });



    },
    
    click: function() {
        var raphaelStart = this.el;

        this.model.set({cx: raphaelStart.getAttribute("x")});
        this.model.set({cy: raphaelStart.getAttribute("y")});
        this.model.updateModel();
    }


})

var swimlanesView = Backbone.View.extend({

    initialize: function(){
        this.render();
        this.swimlane.namebox = this.swimlaneNameBox;
        this.swimlane.nametext = this.swimlaneNameText;
        this.swimlane.attr({stroke: "#000", "stroke-width": 2});
        this.swimlane.drag(resize_move, resize_start);
    },


    render: function(element) {

        this.swimlane = RaphaelElement.rect(this.model.get("cx"), this.model.cy, 500, 300, 1);
        this.swimlaneNameBox = RaphaelElement.rect(this.model.get("cx"), this.model.cy, 25, 300, 1);
        this.swimlaneNameText = RaphaelElement.text(this.model.get("cx"), this.model.cy, this.model.value).attr({fill: "#000000", "font-size": 18}).transform('t12,' + 300 / 2 + 'r270');

        this.swimlane.toBack();
        this.el = this.swimlane.node;

    },
})

var gatewayView = Backbone.View.extend({

    render: function() {
        this.Raphaelgateway = RaphaelElement.path('M' + this.model.get("cx") + ',' + this.model.get("cy") + 'L' + (this.model.get("cx") - 50) + ',' + (this.model.get("cy") + 50) + 'L' + (this.model.get("cx")) + ',' + (this.model.get("cy") + 100) + 'L' + (this.model.get("cx") + 50) + ',' + (this.model.get("cy") + 50) + 'Z');
        this.raphaelText = RaphaelElement.text(this.model.get("cx"), (this.model.get("cy") + 50), this.model.get("value"));
        this.Raphaelgateway.pair = this.raphaelText;
        this.raphaelText.pair = this.Raphaelgateway;
        this.raphaelText.attr({fill: '#383838', "font-size": 16, cursor: "move"});
        this.Raphaelgateway.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = this.Raphaelgateway;

        var color = Raphael.getColor();
        this.Raphaelgateway.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.Raphaelgateway.drag(movePath, dragger, up);
        this.raphaelText.drag(movePath, dragger, up);

    }
})

var relationView = Backbone.View.extend({


    render: function() {
        startPointId = this.model.get("startId");
        EndPointId = this.model.get("endId");

        var startPointti = RaphaelObjects[startPointId];
        var endPointti = RaphaelObjects[EndPointId];

        connections.push(RaphaelElement.connection(startPointti, endPointti, "#000"));


    }

})


