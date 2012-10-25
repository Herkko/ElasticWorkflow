

//funktio viivoille.
   Raphael.fn.connection = function (obj1, obj2, line, bg) {
	    
	  if (obj1.line && obj1.from && obj1.to) {
	      line = obj1;
	      obj1 = line.from;
	      obj2 = line.to;
	      }

	      
	  var bb1 = obj1.getBBox(),
	      bb2 = obj2.getBBox(),
	      
	  // reunapisteiden kohdat haettu p:hen
	  p = [{x: bb1.x + bb1.width / 2, y: bb1.y - 1},
	  {x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + 1},
	  {x: bb1.x - 1, y: bb1.y + bb1.height / 2},
	  {x: bb1.x + bb1.width + 1, y: bb1.y + bb1.height / 2},
	  {x: bb2.x + bb2.width / 2, y: bb2.y - 1},
	  {x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + 1},
	  {x: bb2.x - 1, y: bb2.y + bb2.height / 2},
	  {x: bb2.x + bb2.width + 1, y: bb2.y + bb2.height / 2}],
	 
	  
	  d = {}, dis = [];
	  
	  for (var i = 0; i < 4; i++) {
	    for (var j = 4; j < 8; j++) {
	      var dx = Math.abs(p[i].x - p[j].x),
	      dy = Math.abs(p[i].y - p[j].y);
	      
	      if ((i == j - 4) || (((i != 3 && j != 6) || p[i].x < p[j].x) && ((i != 2 && j != 7) || p[i].x > p[j].x) && ((i != 0 && j != 5) || p[i].y > p[j].y) && ((i != 1 && j != 4) || p[i].y < p[j].y))) {
		dis.push(dx + dy);
		d[dis[dis.length - 1]] = [i, j];
	      }
	    }
	  }
	 
	 
	  if (dis.length == 0) {
	    var res = [0, 4];
	  } else {
	    res = d[Math.min.apply(Math, dis)];
	  }
	  
	  var x1 = p[res[0]].x,
	 
	  y1 = p[res[0]].y,
	  x4 = p[res[1]].x,
	  y4 = p[res[1]].y;
	  dx = Math.max(Math.abs(x1 - x4) / 2, 10);
	  dy = Math.max(Math.abs(y1 - y4) / 2, 10);
	  var x2 = [x1, x1, x1 - dx, x1 + dx][res[0]].toFixed(3),
	  y2 = [y1 - dy, y1 + dy, y1, y1][res[0]].toFixed(3),
	  x3 = [0, 0, 0, 0, x4, x4, x4 - dx, x4 + dx][res[1]].toFixed(3),
	  y3 = [0, 0, 0, 0, y1 + dy, y1 - dy, y4, y4][res[1]].toFixed(3);
	  
	  var path = ["M", x1.toFixed(3), y1.toFixed(3), "C", x2, y2, x3, y3, x4.toFixed(3), y4.toFixed(3)].join(",");
	 
	  if (line && line.line) {
	    line.bg && line.bg.attr({path: path});
	    line.line.attr({path: path});
	  } else {
	    var color = typeof line == "string" ? line : "#000";
	  return {
	    bg: bg && bg.split && this.path(path).attr({stroke: bg.split("|")[0], fill: "none", "stroke-width": bg.split("|")[1] || 3}),
	    line: this.path(path).attr({stroke: color, fill: "none"}),
	    from: obj1,
	    to: obj2
	    };
	  }
}; 
   
   window.onload = function(){
      
     // funktiot palikoiden liikuttamista varten
     
      var dragger = function () {
	this.ox = this.type == "rect" ? this.attr("x") : this.attr("cx");
	this.oy = this.type == "rect" ? this.attr("y") : this.attr("cy");
	this.animate({"fill-opacity": .3}, 500);
	};
	
      var move = function (dx, dy) {
		 var att = this.type == "rect" ? {x: this.ox + dx, y: this.oy + dy} : {cx: this.ox + dx, cy: this.oy + dy};
		 this.attr(att);
		  for (var i = connections.length; i--;) {

		    RaphaelElement.connection(connections[i]);
		  }
		  RaphaelElement.safari();
       }; 
	
     var  up = function () {
	  this.animate({"fill-opacity": 0}, 500);
	}; 
      


	RaphaelElement = Raphael("mainArea", 500, 500);

	
	
	// create json models, fields are automatically derived from json.
			
			
	var ActivityElement = Backbone.Model.extend({
		
		render: function(element) {
		  var activity =  RaphaelElement.rect(element.cx, element.cy, 60, 40, 2);
	      this.set({element: activity});
		  
		  var color = Raphael.getColor();
	      activity.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
	      activity.drag(move, dragger, up);    
	  }
	});
	
	var StartElement = Backbone.Model.extend({
	
	  
	  render: function(element) {

		  var start = RaphaelElement.circle(element.cx, element.cy, 20);
		  this.set({element: start});
		
	    var color = Raphael.getColor();
	    start.attr({fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move"});
	    start.drag(move, dragger, up);
	  }
	});

	
	
	
	// url defines where you can get list of json elements
	var ActivityList = Backbone.Collection.extend({
		model: ActivityElement,
		url: '/json/activity'
	});

	var StartList = Backbone.Collection.extend({
		model: StartElement,
		url: '/json/start'
	});

	// create new instance of ElementList
	var ActivityElements = new ActivityList;
	var StartElements = new StartList;

	
	
	// Iterate through all the elements, render template for each element and
	// return a list of templates
	var ElementsView = Backbone.View.extend({
		// template: _.template($('#elementList_template').html()),
		render: function(eventName) {
			_.each(this.model.models, function(element){
				// var lTemplate = this.template(element.toJSON());
				// $(this.el).append(lTemplate);
			    element.render(element.toJSON());
			}, this);
			return this;
		}
	});

	var AppView = Backbone.View.extend({
		el: "body",
		
		events: {
			'click .clickable': 'handleClick',
			'change': 'handleChange'
		},
		
		// get all element templates and append them to html div with id
		// #elements
		render: function(){
			var activityElementsView = new ElementsView({model:ActivityElements});
			var startElementsView = new ElementsView({model:StartElements});
			startElementsView.render();
			activityElementsView.render();// .el;
			
			
		// $('#elements').html(lHtml);
		},

		// fetch the list of elements and do a render method
		initialize: function(){
			var lOptions = {};
			//lOptions.success = this.render;
			ActivityElements.fetch(lOptions);
			StartElements.fetch(lOptions);
			this.render();
		},
		
		handleClick: function() {
		  console.log("Something was clicked");
		},
		
		handleChange: function() {
		  console.log("Something was changed");
		}
	});

	
	
	var App = new AppView;



	
	connections = [];

    //var testi = StartElements.at(0);
    //var toinentesti = ActivityElements.at(0).get("element");
    
	  // taytyy tehda funktio joka katsoo mitka muodot ovat yhteydessa
		// toisiinsa.
    
    // TAMA TOIMII!!!
    connections.push(RaphaelElement.connection(StartElements.at(0).get("element"), ActivityElements.at(0).get("element"), "#000"));
    // connections.push(r.connection(shapes[1], shapes[2], "#000", "#000|5"));
     // connections.push(r.connection(shapes[1], shapes[3], "#000", "#000"));
      
      
      
    };
