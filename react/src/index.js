import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import ShowMenu from './components/showMenu.js'
import CreateArticle from './components/createArticle.js'


ReactDOM.render(<ShowMenu/>, document.getElementById('root'));


document.getElementById("1").addEventListener("click",function(){
  ReactDOM.render(<CreateArticle/>,document.getElementById("root"));
  
  document.getElementById("showMenu").addEventListener("click",function(){
    ReactDOM.render(<ShowMenu/>,document.getElementById("root"));
  });

});



document.getElementById("2").addEventListener("click",function(){
  alert("see articles");
});
