var AppView = Backbone.View.extend({
    el: "elements",
    

});

//var ElementsView = Backbone.View.extend({
//
//    render: function(eventName) {
//        _.each(this.model.models, function(element) {
//            element.render(element.toJSON());
//        }, this);
//        return this;
//    }
//});

var ActivityView = Backbone.View.extend({

        
    initialize: function(){
        this.raphaelActivity = RaphaelElement.rect(this.model.get("cx"), this.model.get("cy"), 100, 60, 4);    
        
    },


    render: function(element) {
       

        
        var color = Raphael.getColor();
        this.raphaelActivity.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        this.raphaelActivity.drag(move, dragger, up);
        this.el = this.raphaelActivity.node;
        
        this.raphaelActivity.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = this.raphaelActivity;
        
     
        $(this.el).click(_.bind(function() {
            this.click()
        }, this));



    },
    click: function() {
        //var raphaelActivity = this.el;

        this.model.set({cx: this.raphaelActivity.getAttribute("x")});
        this.model.set({cy: this.raphaelActivity.getAttribute("y")});
        this.model.updateModel();
    }

});

var StartsView = Backbone.View.extend({


    render: function() {
    //    $(this.el).unbind("click");

        var raphaelStart = RaphaelElement.circle(this.model.get("cx"), this.model.get("cy"), 20);
        var color = Raphael.getColor();
        raphaelStart.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        raphaelStart.drag(move, dragger, up);
        this.el = raphaelStart.node;
        
        raphaelStart.attr({data: this.model.get("id")});
         RaphaelObjects[this.model.get("id")] = raphaelStart;
        
        $(this.el).click(_.bind(function() {
            this.click()
        }, this));



        var raphaelText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value")).attr({fill: '#383838', "font-size": 16, cx: this.model.get("cx"), cy: this.model.get("cy")});
        //raphaelText.drag(moveText, startText);

        var set = RaphaelElement.set();
        set.push(start);
        set.push(raphaelText);

        this.el = set.node;
      //  $(this.el).click(_.bind(function(){this.click()}, this));
        
        var ox = 0;
        var oy = 0;
        var dragging = false;

        set.mousedown(function(event) {
        alert("lala");
            ox = event.screenX;
            oy = event.screenY;
            set.attr({
                opacity: .5
            });
            dragging = true;
        });

        $(document).mousemove(function(event) {
            if (dragging) {
                set.translate(event.screenX - ox, event.screenY - oy);
                ox = event.screenX;
                oy = event.screenY;
                for (var i = connections.length; i--; ) {
                    RaphaelElement.connection(connections[i]);
                }
                RaphaelElement.safari();
            }
        });

        set.mouseup(function(event) {
            dragging = false;
            set.attr({
                opacity: 1
            });
        });



    },
    click: function() {
        var raphaelStart = this.el;

        this.model.set({cx: raphaelStart.getAttribute("x")});
        this.model.set({cy: raphaelStart.getAttribute("y")});
        this.model.updateModel();
    }

});

var endsView = Backbone.View.extend({
    render: function() {


        var raphaelEnd = RaphaelElement.circle(this.model.get("cx"), this.model.get("cy"), 20);
        var color = Raphael.getColor();
        raphaelEnd.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        raphaelEnd.drag(move, dragger, up);
        this.el = raphaelEnd.node;
        raphaelEnd.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = raphaelEnd ;
        
        $(this.el).click(_.bind(function() {
            this.click()
        }, this));


        var raphaelText = RaphaelElement.text(this.model.get("cx"), this.model.get("cy"), this.model.get("value")).attr({fill: '#383838', "font-size": 16, cx: this.model.get("cx"), cy: this.model.get("cy")});
       // raphaelText.drag(moveText, startText);

        var set = RaphaelElement.set();
        set.push(start);
        set.push(raphaelText);

        var ox = 0;
        var oy = 0;
        var dragging = false;

        set.mousedown(function(event) {
            ox = event.screenX;
            oy = event.screenY;
            set.attr({
                opacity: .5
            });
            dragging = true;
        });

        $(document).mousemove(function(event) {
            if (dragging) {
                set.translate(event.screenX - ox, event.screenY - oy);
                ox = event.screenX;
                oy = event.screenY;
                for (var i = connections.length; i--; ) {
                    RaphaelElement.connection(connections[i]);
                }
                RaphaelElement.safari();
            }
        });

        set.mouseup(function(event) {
            dragging = false;
            set.attr({
                opacity: 1
            });
        });



    },
    click: function() {
        var raphaelStart = this.el;

        this.model.set({cx: raphaelStart.getAttribute("x")});
        this.model.set({cy: raphaelStart.getAttribute("y")});
        this.model.updateModel();
    }


})

var swimlanesView = Backbone.View.extend({

    
     render: function(element) {   

        var swimlane = RaphaelElement.rect(this.model.get("cx"), this.model.cy, 500, 300, 1);
        var swimlaneNameBox = RaphaelElement.rect(this.model.get("cx"), this.model.cy, 25, 300, 1);
        var swimlaneNameText = RaphaelElement.text(this.model.get("cx"), this.model.cy, this.model.value).attr({fill: "#000000", "font-size": 18}).transform('t12,' + 300 / 2 + 'r270');

        swimlane.attr({stroke: "#000", "stroke-width": 2});
        swimlane.drag(resize_move, resize_start);

        swimlane.toBack();



        this.el = swimlane.node;


 },
})

var gatewayView = Backbone.View.extend({

    render: function() {
        var Raphaelgateway = RaphaelElement.path('M' + this.model.get("cx") + ',' + this.model.get("cy") + 'L' + (this.model.get("cx") - 50) + ',' + (this.model.get("cy") + 50) + 'L' + (this.model.get("cx")) + ',' + (this.model.get("cy") + 100) + 'L' + (this.model.get("cx") + 50) + ',' + (this.model.get("cy") + 50) + 'Z');
        Raphaelgateway.attr({data: this.model.get("id")});
        RaphaelObjects[this.model.get("id")] = Raphaelgateway;
        
        var color = Raphael.getColor();
        Raphaelgateway.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
        Raphaelgateway.drag(movePath, dragger, up);

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


