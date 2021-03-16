import React from 'react';
import ReactDOM from 'react-dom';
import ShowMenu from './showMenu.js'
import $ from 'jquery';

function NoResult(props) {
	return(
		<div style={{textAlign:"center"}}>
			<p><i>{props.msg}</i></p>
		</div>
	)
}

function Loading() {
	return(
		<div className="loader">
		</div>
	)
}


function Article(props){
	
	return(
		<div className="card" style={{backgroundColor:"#131316"}}>
			<div style={{width: "100%",whiteSpace: "nowrap",overflow: "hidden",textOverflow:"ellipsis",backgroundColor:"#131316"}}>
				{props.title}
			</div>
			<p>
				<a href={"http://localhost:8080/article?a="+props.data} target="_blank" rel="noreferrer">See <i className="fa fa-arrow-right" aria-hidden="true"></i></a>
			
			</p>

		</div>
	)
}

//list for storing articles
var list=[];
var articlesObjects=[];
function getArticlesList(){
	var fd = new FormData();
	$.ajax({
		url: '/getArticles',
		type: 'post',
		data: fd, 
		contentType: false,
		processData: false,
		success: function(response){
			//console.log(response);
			list=response;
			for(var i=0;i<list.length;i++){
				articlesObjects[i]=<Article title={list[i].title} data={list[i].data} key={i}/>
			}
			ReactDOM.render(articlesObjects,document.getElementById("list"));
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
	        try{
 				ReactDOM.render(<NoResult msg={"Some error occurred"}/>,document.getElementById("list"));
	        }
	        catch(err){
	        	//console.log(err);
	        }
	    },
		fail: function(xhr, textStatus, errorThrown){
	        try{
	        	ReactDOM.render(<NoResult msg={"Server down"}/>,document.getElementById("list"));
	        }
	        catch(err){
	        	//console.log(err);
	        }
	    }
	});
}

class ShowArticles extends React.Component{
	constructor(props){
		super(props);
		this.onSearch=this.onSearch.bind(this);
		this.showMenu=this.showMenu.bind(this);
	}
	componentDidMount(){
		window.history.pushState('',"",'/show-articles/');
		ReactDOM.render(<Loading/>,document.getElementById("list"));
		getArticlesList();
	}
	componentWillUnmount(){
		window.history.pushState('',null,'/');
	}
	render(){
		return(
			<div className="container"><br/>
				<i id="showMenu" className="fa fa-arrow-left fa-lg" aria-hidden="true" onClick={this.showMenu} style={{color: "rgb(15 11 238)",paddingRight:"10px"}}></i>Back
        		<br/><br/>
				<div style={{ marginLeft:"auto",textAlign: "left" }} id="searchdiv">
			      <div className="input-group"  style={{padding: "4px"}} >
			          <input className="form-control border-secondary py-2" id="searchID" type="search" onChange={this.onSearch} placeholder="Search..." style={{borderRadius:"40px",border:"2px solid red",backgroundColor:"#131316",color:"white"}}/>
			      </div>
			    </div>
			    <br/>
			    
				<div id="list" style={{color:"white"}}></div>
        	</div>
		)
	}
	showMenu(ele){
		ReactDOM.render(<ShowMenu cont={this.props.cont} url={this.props.url} title={this.props.title} body={this.props.body}/>,document.getElementById("root"));
	}
	onSearch(ele){

		//console.log(ele.target.value)
		var key=ele.target.value;
		if(key===""){
			ReactDOM.render(articlesObjects,document.getElementById("list"));
			return;
		}
		else
			ReactDOM.render(<Loading/>,document.getElementById("list"));
		
		key=key.toLowerCase();
		var results=[];
		for(var i=0;i<list.length;i++){
			var item=list[i].title;
			item=item.toLowerCase();
			if(item.includes(key) || key.includes(item)){
				results.push(<Article key={i} title={list[i].title} data={list[i].data}/>);
			}
		}
		//console.log(results);
		if(results.length===0)
			ReactDOM.render(<NoResult msg={"No results found"}/>,document.getElementById("list"));
		else
			ReactDOM.render(results,document.getElementById("list"));
	}

}
export default ShowArticles;
