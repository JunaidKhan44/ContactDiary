console.log("working")
//alert("hello")

const toggleSidebar=() =>{

    if ( $(".sidebar").is(":visible")){


        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%");

    }else{
        
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","20%");
    }

};

const search=() =>{

    let query = $("#search-input").val();
    if(query==""){
        $(".search-result").hide();
    }else{
     
  	 const object = {};
      //sending request to server
     let url='http://localhost:8080/search/{query}';
	
      fetch(url)
      .then((response) =>{
          return response.json();
      })
      .then((data) =>{

          let text='<div class="list-group">';

          data.forEach((contact) => {
			
             text += '<a href="/user/';
             
             text += contact.cid;
             
             text += '/contact"'; 
             
             text+= 'class="list-group-item list-group-action">';
             
             text += contact.name;
             
             text += '</a>'; 
          });

          text +='</div>';
            $(".search-result").html(text);
            $(".search-result").show();
      });

    

      
       
    }

}