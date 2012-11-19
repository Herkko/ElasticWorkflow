

var App;
var RaphaelElement;


var ActivityElements = new ActivityList;
var StartElements = new StartList;
var EndElements = new EndList;
var SwimlaneElements = new SwimlaneList;
var GatewayElements = new GatewayList;
var RelationElements = new RelationList;
RaphaelObjects = [];




window.onload = function() {

    connections = [];
    
    RaphaelElement = Raphael(10, 120, "100%", "100%");
    App = new AppView;
    setUpApplication();
    
  
   
};
    

function setUpApplication(){
    function renderActivities() {
     
       for(var i=0; i<ActivityElements.length; i++){
        activityElements = new ActivityView({model: ActivityElements.models[i]});
        activityElements.render();
        };     
        
   }
    
  function renderStarts(){
        for(var i=0; i<StartElements.length; i++){
        startsViewElement = new StartsView({model: StartElements.models[i]});
        startsViewElement.render();
        };  
      
  }  
  
  function renderEnds(){
        for(var i=0; i<EndElements.length; i++){
        endsViewElement = new endsView({model: EndElements.models[i]});
        endsViewElement.render();
        };  
  }
  
  function renderSwimlanes(){
       for(var i=0; i<SwimlaneElements.length; i++){
        swimlanesElement = new swimlanesView({model: SwimlaneElements.models[i]});
        swimlanesElement.render();
        };  
  }
  
  
  function renderGateways(){
       for(var i=0; i<GatewayElements.length; i++){
        gatewayElement = new gatewayView({model: GatewayElements.models[i]});
        gatewayElement.render();
        };  
  }
  
  function renderRelations(){
      
      
      for(var i=0; i<RelationElements.length; i++){
        relationViewElement = new relationView({model: RelationElements.models[i]});

        relationViewElement.render();
        }; 
      
      
  }
  
    $.when(ActivityElements.fetch({success: renderActivities}),    
    StartElements.fetch({success: renderStarts}),
    EndElements.fetch({success: renderEnds}),
    SwimlaneElements.fetch({success: renderSwimlanes}),
    GatewayElements.fetch({success: renderGateways}))
    .then(function() {
        RelationElements.fetch({success: renderRelations}); 
    });
 
}


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
	
    console.log("Elements hopefully saved, now refresh page.");
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

function newActivity() {
    activity = new activity();
    view = new ActivityView({model: activity});
    activity.save();
    activityElements.push(activity);
};
