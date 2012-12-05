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


var workflow = {
    models: {},
    collections: {},
    views: {},
  
    //domainHost: "http://morning-fjord-4117.herokuapp.com",
   domainHost: "http://localhost:9000",
    ENTER: 13,
     
        
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
        workflow.views.editView;
        RaphaelElement = Raphael("mainArea", "100%", "100%");
        App = new workflow.views.AppView;
        setUpApplication();
    },
            
      refresh: function(){
        ActivityElements.fetch({error: function() { console.log(arguments); }});
        
       // StartElements.fetch();
       // EndElements.fetch();
    
        }      
            
  
}

function Colors() {
    this.colors = new Array();
    this.colors["start"] = Raphael.getColor();
    this.colors["end"] = Raphael.getColor();
    this.colors["activity"] = Raphael.getColor();
    this.colors["gateway"] = Raphael.getColor();
    this.colors["swimlane"] = Raphael.getColor();
}

Colors.prototype.get = function(type) {
   return this.colors[type];
}

var colors = new Colors();

$(document).ready(function() {
    workflow.initialize();
   
    
//     setInterval(function() {
//         workflow.refresh(); 
//     }, 3000);
   
    
});


function setUpApplication(){
    
    function renderActivities() {
     
       for(var i=0; i<ActivityElements.length; i++){
        activityViwElement = new workflow.views.ActivityView({model: ActivityElements.models[i]});
      
        };     
        
   }
    
  function renderStarts(){
        for(var i=0; i<StartElements.length; i++){
        startsViewElement = new workflow.views.StartView({model: StartElements.models[i]});
       
        };  
      
  }  
  
  function renderEnds(){
        for(var i=0; i<EndElements.length; i++){
        endsViewElement = new workflow.views.EndView({model: EndElements.models[i]});
        
        };  
  }
  
  function renderSwimlanes(){
       for(var i=0; i<SwimlaneElements.length; i++){
        swimlanesElement = new workflow.views.SwimlaneView({model: SwimlaneElements.models[i]});
        
        };  
  }
  
  
  function renderGateways(){
       for(var i=0; i<GatewayElements.length; i++){
        gatewayElement = new workflow.views.GatewayView({model: GatewayElements.models[i]});
      
        };  
  }
  
  function renderRelations(){
      
      
      for(var i=0; i<RelationElements.length; i++){
        relationViewElement = new workflow.views.RelationView({model: RelationElements.models[i]});

        
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
    
    jQuery('ul.nav li.dropdown').hover(function() {
  		jQuery(this).find('.dropdown-menu').stop(true, true).delay(200).fadeIn();
	}, function() {
  		jQuery(this).find('.dropdown-menu').stop(true, true).delay(200).fadeOut();
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
