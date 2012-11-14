var AppRouter = Backbone.Router.extend({
    
    routes: {
        "": "EditMode"
    },
   
    
    EditMode: function(){
        console.log("routerissa");
        alert("routerissa");
    },
    
});