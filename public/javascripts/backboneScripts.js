var roundElement = Backbone.Model.extend({
  defaults: {
	name: 'CircleElementX', 
	Value: 'empty',
	type: 'circle',
	cx: 0,
	cy: 0,
	r: 10 
      },
       Render: function(){
     // code to create raphael objects
     var rCircle = paper.circle(cx, cy, r);
    }, Update: function(){
     //hakee JSON ja muodostaa modelin       
    }
	
  });

var relationElement = Backbone.Model.extend({
    defaults: {
	  name: 'LineElement',
	  value: 'empty',
	  type: 'line',
	  startPointX: 0,
	  startPointY: 0,
	  endPointX: 0,
	  endPointY: 0
    },
    Render: function(){
     // code to create raphael objects 
      var rLine = paper.path("M"+startPointX+" "+startPointY+"L"+endPointX+" "+endPointY)
    },
    update: function(){
      //hakee JSON ja muodostaa modelin 
    }
});

var startElement = roundElement.extend({
  defaults: {
	name: 'CircleElementX', 
	Value: 'empty',
	type: 'circle',
	cx: 0,
	cy: 0,
	r: 10 
      },
      Render: function(){
        var rCircle = paper.circle(cx, cy, r);
        rCircle.attr("fill", "#585858 ");
        rCircle.attr("stroke", "#080808 ");
      }, Update: function(){
        //hakee JSON ja muodostaa modelin 
      }
});

var endElement = roundElement.extend({
  defaults: {
	name: 'CircleElementX', 
	Value: 'empty',
	type: 'circle',
	cx: 0,
	cy: 0,
	r: 10 
      },
      Render: function(){
        var rCircle = paper.circle(cx, cy, r);
      }, Update: function(){
        //hakee JSON ja muodostaa modelin 
      }
});

var actionElement = Backbone.Model.extend({
  defaults: {
    name: 'RectElement',
    Value: 'empty',
    type: 'rect',
    cx: 0,
    cy: 0,
    width: 0,
    height: 0,
    r: 0
  	  },
  	  Render: function() {
  	    var c = paper.rect(cx, cy, width, height, r);
  	  }
})

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
    
    
    