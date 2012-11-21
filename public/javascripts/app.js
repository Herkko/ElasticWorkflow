
var workflow = {
    models: {},
    collections: {},
    views: {},
    
     
           
            
        
    initialize: function(){
        //all collections
        ActivityElements = new workflow.collections.ActivityList();
        StartElements= new workflow.collections.StartList();
        EndElements= new workflow.collections.EndList();
        SwimlaneElements= new workflow.collections.SwimlaneList();
        GatewayElements= new workflow.collections.GatewayList();
        RelationElements= new workflow.collections.RelationList();
        RaphaelObjects= []; 
        connections = [];

        RaphaelElement = Raphael(10, 120, "100%", "100%");
        App = new workflow.views.AppView;
        setUpApplication();
    },
            
      refresh: function(){
        ActivityElements.fetch();

       // StartElements.fetch();
       // EndElements.fetch();
    
        }      
            
  
}

  
$(document).ready(function() {
    workflow.initialize();
   
    
//     setInterval(function() {
//         workflow.refresh(); 
//     }, 3000);
   
    
});


function setUpApplication(){
    
    function renderActivities() {
     
       for(var i=0; i<ActivityElements.length; i++){
        activityElements = new workflow.views.ActivityView({model: ActivityElements.models[i]});
      
        };     
        
   }
    
  function renderStarts(){
        for(var i=0; i<StartElements.length; i++){
        startsViewElement = new workflow.views.StartsView({model: StartElements.models[i]});
       
        };  
      
  }  
  
  function renderEnds(){
        for(var i=0; i<EndElements.length; i++){
        endsViewElement = new workflow.views.endsView({model: EndElements.models[i]});
        
        };  
  }
  
  function renderSwimlanes(){
       for(var i=0; i<SwimlaneElements.length; i++){
        swimlanesElement = new workflow.views.swimlanesView({model: SwimlaneElements.models[i]});
        
        };  
  }
  
  
  function renderGateways(){
       for(var i=0; i<GatewayElements.length; i++){
        gatewayElement = new workflow.views.gatewayView({model: GatewayElements.models[i]});
      
        };  
  }
  
  function renderRelations(){
      
      
      for(var i=0; i<RelationElements.length; i++){
        relationViewElement = new workflow.views.relationView({model: RelationElements.models[i]});

        
        }; 
      
      
  }
  
    $.when(ActivityElements.fetch({success: renderActivities}),    
    StartElements.fetch({success: renderStarts}),
    EndElements.fetch({success: renderEnds}),
    SwimlaneElements.fetch({success: renderSwimlanes}),
    GatewayElements.fetch({success: renderGateways}))
    .then(function() {
        RelationElements.fetch({success: renderRelations}); 
        //var EditTemplate = new EditElementsView();
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
