var Circle = Backbone.Model.extend({
  defaults: {
	name: 'CircleElementX', 
	Value: 'empty',
	type: 'circle'  
	cx: 0,
	cy: 0,
	r: 10 
      }
	
  });

var Line = Backbone.Model.extend({
    defaults: {
	  name: 'LineElement',
	  value: 'empty',
	  type: 'line',
	  startPoint: 0,
	  endPoint: 0,
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
    
    
    