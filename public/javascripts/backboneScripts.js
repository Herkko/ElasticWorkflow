var roundElement = Backbone.Model.extend({
  defaults: {
	name: 'CircleElementX', 
	Value: 'empty',
	type: 'circle'  
	cx: 0,
	cy: 0,
	r: 10 
      },
       Render: function(){
     // code to create raphael objects
     var rCircle = paper.circle(cx, cy,r);
    }, Update: function(){
     //hakee JSON ja muodostaa modelin 
      
    }
	
  });

var relationElement = Backbone.Model.extend({
    defaults: {
	  name: 'LineElement',
	  value: 'empty',
	  type: 'line',
	  startPoint: 0,
	  endPoint: 0,
    },
    Render: function(){
     // code to create raphael objects 
    },
    update: function(){
      
    }
});


var Lines = Backbone.Collection.extend({
    model: Line
});

var Circles = Backbone.Collection.extend({
    model: Circle
});

var Elements = Backbone.Collection.extend({
    Collection: Circles,
    Collection: Lines
});
    
    
    