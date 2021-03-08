import React from 'react';
import ReactDOM from 'react-dom';
import CreateArticle from './createArticle.js' ;
import  ShowArticles from './showArticles.js';
 



class ShowMenu extends React.Component{
	constructor(props){
		super(props);
		this.onHover=this.onHover.bind(this);
		this.onOut=this.onOut.bind(this);
		this.onclciked=this.onclciked.bind(this);
	}
	render(){
		return(
			<div className="container" align="center">
			<br/><br/>
			<h4>Welcome</h4>
				<br/><br/>
				<br/><br/>
				<div className="card" id="1" style={{backgroundColor:"#27272a",width: "15rem",maxWidth:"100%"}} onMouseOver={this.onHover} onClick={this.onclciked}onMouseOut={this.onOut} >
				  <div className="card-body" id="11"> 
				    Write an article.
				  </div>
				</div>

				<br/>
				<br/><br/><br/>
				<div className="card" id="2" style={{backgroundColor:"#27272a",width: "15rem",maxWidth:"100%"}} onMouseOver={this.onHover} onClick={this.onclciked} onMouseOut={this.onOut}>
				  <div className="card-body" id="22">
				    See articles.
				  </div>
				</div>

			</div>
		)
	}
	onHover(ele){
		var id=ele["target"]["id"];
		if(id==="1" || id==="11"){
			document.getElementById("1").style.backgroundColor="rgb(108 58 242)";
		}
		else{
			document.getElementById("2").style.backgroundColor="rgb(46 193 82)";
		}
	}
	onOut(ele){
		var id=ele["target"]["id"];
		if(id==="1" || id==="11"){
			document.getElementById("1").style.backgroundColor="#27272a";
		}
		else{
			document.getElementById("2").style.backgroundColor="#27272a";
		}
	}
	onclciked(ele){
		if(ele["target"]["id"]=="1" || ele["target"]["id"]=="11")
			ReactDOM.render(<CreateArticle title={this.props.title} body={this.props.body}/>,document.getElementById("root"));
		else
			ReactDOM.render(<ShowArticles/>,document.getElementById("root"));
	}
}
export default ShowMenu;
