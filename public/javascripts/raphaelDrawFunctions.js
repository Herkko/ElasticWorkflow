Raphael.fn.connection = function(obj1, obj2, line, bg) {

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
    //path.attr({stroke:'#FF0000', 'stroke-width': 2 ,'arrow-end': 'classic-wide-long'});

    if (line && line.line) {
        line.bg && line.bg.attr({path: path});
        line.line.attr({path: path});
    } else {
        var color = typeof line == "string" ? line : "#000";
        return {
            bg: bg && bg.split && this.path(path).attr({stroke: bg.split("|")[0], fill: "none", "stroke-width": bg.split("|")[1] || 3}),
            line: this.path(path).attr({stroke: color, 'stroke-width': 2, fill: "none", 'arrow-end': 'block-wide-long'}),
            from: obj1,
            to: obj2
        };
    }
/*
	handle = RaphaelElement.circle(x4,y4,5).attr({
    	fill: "black",
    	cursor: "pointer",
    	"fill-opacity": 0,
    	"stroke-width": 10,
    	stroke: "transparent"
	});
    $(handle.node).mouseup(_.bind(function() { 
    	console.log(":)");
    
	}, this));
	var startRelation = function () {
  		this.cx = this.attr("cx"),
 		this.cy = this.attr("cy");
	},

	moveRelation = function (dx, dy) {
   		var X = this.cx + dx,
    	Y = this.cy + dy;
   		this.attr({cx: X, cy: Y});
  		// pathArray[1][1] = X;
  		// pathArray[1][2] = Y;
   		var pathArray = ["M", x1.toFixed(3), y1.toFixed(3), "C", x2, y2, x3, y3, X, Y].join(",");
   		line.line.attr({path: pathArray});
	},

	upRelation = function () {
   		this.dx = this.dy = 0;
	};

	handle.drag(moveRelation, startRelation, upRelation);
    */        
 	
};

/*
Raphael.fn.removeConnection = function (firstObjectId) {

    for (var i = 0; i < connections.length; i++) {
    console.log(connections[i].from.modelId +":" +connections[i].to.modelId + "  "+ connections[i].line+" "+firstObjectId);
    
    
        if (connections[i].from.id == firstObjectId || connections[i].to.id == firstObjectId) {
                connections[i].line.remove();
                connections.splice(i, 1);            
        }
    }
};
*/




dragger = function() {
        // Original coords for main element
	if (this.type != "text") {
		this.ox = this.type == "rect" ? this.attr("x") : this.attr("cx");
		this.oy = this.type == "rect" ? this.attr("y") : this.attr("cy");
		//this.animate({"fill-opacity": .3}, 500);
	} else {
		this.ox = this.type == "ellipse" ? this.attr("cx") : this.attr("x");
        this.oy = this.type == "ellipse" ? this.attr("cy") : this.attr("y");
	}
  
  
		// Original coords for pair element
	if (this.pair.type != "text") {
		this.pair.ox = this.pair.type == "rect" ? this.pair.attr("x") : this.pair.attr("cx");
		this.pair.oy = this.pair.type == "rect" ? this.pair.attr("y") : this.pair.attr("cy");
	//	this.pair.animate({"fill-opacity": .3}, 500); 
	} else {
		this.pair.ox = this.pair.type == "ellipse" ? this.pair.attr("cx") : this.pair.attr("x");
        this.pair.oy = this.pair.type == "ellipse" ? this.pair.attr("cy") : this.pair.attr("y");
	}
};

move = function(dx, dy) {
		// Move main element
	if (this.type != "text") {
		var att = this.type == "rect" ? {x: (this.ox + dx), y: this.oy + dy} : {cx: this.ox + dx, cy: this.oy + dy};
		this.attr(att);
	} else {
		var att = this.type == "rect" ? {cx: this.ox + dx, cy: this.oy + dy} : {x: this.ox + dx, y: this.oy + dy};
		this.attr(att);
	}
		// Move paired element
	if (this.pair.type != "text") {
		var att = this.pair.type == "rect" ? {x: this.pair.ox + dx, y: this.pair.oy + dy} : {cx: this.pair.ox + dx, cy: this.pair.oy + dy};
		this.pair.attr(att);
	} else {
		var att = this.pair.type == "rect" ? {cx: this.pair.ox + dx, cy: this.pair.oy + dy} : {x: this.pair.ox + dx, y: this.pair.oy + dy};
		this.pair.attr(att);
	}
	
	for (var i = connections.length; i--; ) {
	    RaphaelElement.connection(connections[i]);
	}
	RaphaelElement.safari();
};

rstart = function () {
   
        this.ox = this.attr("x");
        this.oy = this.attr("y");        
        
        this.box.ow = this.box.attr("width");
        this.box.oh = this.box.attr("height");
        
        this.nameBox.oh = this.nameBox.attr("height");     
      
};

rmove = function (dx, dy) {
    var min_height = 280;
    var max_height = 310;

    this.attr({x: this.ox + dx});
    this.box.attr({width: this.box.ow + dx});
        
    if((this.box.attr("height") > min_height || (this.oy + dy) >= this.attr("y")) && 
       (this.box.attr("height") < max_height || (this.oy + dy) <= this.attr("y"))) {
        this.attr({y: this.oy + dy});
        this.box.attr({ height: this.box.oh + dy});
        this.nameBox.attr({height: this.box.oh + dy});
    } 
};   

    
up = function() {
    	// Fade original element on mouse up
   // if (this.type != "text") this.animate({"fill-opacity": 0}, 500);

    	// Fade paired element on mouse up
    //if (this.pair.type != "text") this.pair.animate({"fill-opacity": 0}, 500); 
};

upStart = function() {
    this.animate({"fill-opacity": 1}, 500);
};

startPath = function() {
    this.ox = this.type == "rect" ? this.attr("x") : this.attr("cx");
    this.oy = this.type == "rect" ? this.attr("y") : this.attr("cy");
    this.animate({"fill-opacity": .3}, 500);
},

movePath = function(dx, dy) {
		// Move main element
	if (this.type != "text") {
		var trans_x = (dx) - this.ox;
		var trans_y = (dy) - this.oy;

		this.translate(trans_x, trans_y);
		this.ox = dx;
		this.oy = dy;	     
	} else {
		var att = this.type == "rect" ? {cx: this.ox + dx, cy: this.oy + dy} : {x: this.ox + dx, y: this.oy + dy};
		this.attr(att);
	}

		// Move paired element
	if (this.pair.type != "text") {
		var trans_x = (dx) - this.pair.ox;
		var trans_y = (dy) - this.pair.oy;

		this.pair.translate(trans_x, trans_y);
		this.pair.ox = dx;
		this.pair.oy = dy;
	} else {
		var att = this.pair.type == "rect" ? {x: this.pair.ox + dx, cy: this.pair.oy + dy} : {x: this.pair.ox + dx, y: this.pair.oy + dy};
		this.pair.attr(att);
	}


	for (var i = connections.length; i--; ) {
		RaphaelElement.connection(connections[i]);
	}
},

upPath = function() {
    // nothing special
};

activityUp = function(){
   this.animate({"fill-opacity": 0}, 500);
};
