

var App;
var RaphaelElement;


var ActivityElements = new ActivityList;
var StartElements = new StartList;
var EndElements = new EndList;
var SwimlaneElements = new SwimlaneList;
var GatewayElements = new GatewayList;
var RelationElements = new RelationList;


var AllElements = new AllElementsList;


window.onload = function() {

    connections = [];


    RaphaelElement = Raphael(10, 120, "100%", "100%");


    App = new AppView;
    
   
    
  function RenderActivities() {
     
       for(var i=0; i<ActivityElements.length; i++){
        activityElementsView = new ActivityView({model: ActivityElements.models[i]});
        activityElementsView.render();
        };     
        
   }
    
    
    
    ActivityElements.fetch({success: RenderActivities});    
    
    
    
  
   
   
};
    
    




function updateall(){

	for (var i=0; i<ActivityElements.length; i++) {
		ActivityElements.at(i).save();

	}
	for (var i=0; i<StartElements.length; i++) {
		StartElements.at(i).save();
	}
	for (var i=0; i<EndElements.length; i++) {
		EndElements.at(i).save();
	}
	for (var i=0; i<GatewayElements.length; i++) {
		GatewayElements.at(i).save();
	}
	
    alert("Elements hopefully saved, now refresh page.");
} 

function changeText(id, text) {
	var elem = getBackboneModelById(2);
	elem.set({value: "Rawr2"});
	elem.save();
}

function getBackboneModelById(id) {
	    if(ActivityElements.get(id) != null) return ActivityElements.get(id);
	    else if(StartElements.get(id) != null) return StartElements.get(id);
	    else if(EndElements.get(id) != null) return EndElements.get(id);
	    else if(SwimlaneElements.get(id) != null) return SwimlaneElements.get(id);
	    else if(GatewayElements.get(id) != null) return GatewayElements.get(id);
}
