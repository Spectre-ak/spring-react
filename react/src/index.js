import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import ShowMenu from './components/showMenu.js'
import CreateArticle from './components/createArticle.js'
import ShowArticles from './components/showArticles.js'
import $ from 'jquery';

var lastBack=false;
window.addEventListener('popstate', function(event) {
  if(lastBack)return;
    try{
      document.getElementById("showMenu").click();
    }
    catch(exception){
      try{
        document.getElementById("returnBack").click();
      }
      catch(exception){
        window.history.back();
        lastBack=true;
      }
    }
   return;
   
}, false);

//cheking if cookie is present if not then creating it
$.ajax({
  url: '/checkCookieSettings',
    //url: 'http://localhost:8080/checkCookieSettings',
    type: 'get',
    contentType: false,
    processData: false,
    success: function(response){
      //console.log(response);
    },
});


ReactDOM.render(<ShowMenu/>, document.getElementById('root'));


document.getElementById("1").addEventListener("click",function(){
  ReactDOM.render(<CreateArticle/>,document.getElementById("root"));
  
  document.getElementById("showMenu").addEventListener("click",function(){
    ReactDOM.render(<ShowMenu/>,document.getElementById("root"));
  });

});



document.getElementById("2").addEventListener("click",function(){
  ReactDOM.render(<ShowArticles/>,document.getElementById("root"));
  
  document.getElementById("showMenu").addEventListener("click",function(){
    ReactDOM.render(<ShowArticles/>,document.getElementById("root"));
  });
});

