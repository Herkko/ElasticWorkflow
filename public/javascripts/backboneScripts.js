/**Copyright 2012 University of Helsinki, Daria Antonova, Herkko Virolainen, Panu Klemola
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.*/

var roundElement = Backbone.Model.extend({
  defaults: {
	name: 'CircleElementX', 
	Value: 'empty',
	type: 'circle',
	coordinateX: 0,
	coordinateY: 0,
	radius: 10 
      },
       Render: function(){
     // code to create raphael objects
     var rCircle = paper.circle(coordinateX, coordinateY, radius);
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
	coordinateX: 0,
	coordinateY: 0,
	radius: 10 
      },
      Render: function(){
        var rCircle = paper.circle(coordinateX, coordinateY, radius);
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
	coordinateX: 0,
	coordinateY: 0,
	radius: 10 
      },
      Render: function(){
        var rCircle = paper.circle(coordinateX, coordinateY, radius);
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
    
    
    