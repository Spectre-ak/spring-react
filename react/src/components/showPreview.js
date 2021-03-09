import React from 'react';
import ReactDOM from 'react-dom';

import CreateArticle from './createArticle.js' ;



class ShowPreview extends React.Component{
	constructor(props){
		super(props);
		this.returnBack=this.returnBack.bind(this);
		
	}
	componentDidMount(){
		window.history.pushState('/create-article/',null,'/preview');

		if(this.props.body!="")
			document.getElementById("body").innerHTML=this.props.body;
		if(this.props.title!="")
			document.getElementById("title").innerHTML=this.props.title;

	}
	render(){
		return(
			<div className="container"><br/>
				<i  id="returnBack" className="fa fa-arrow-left fa-lg" aria-hidden="true" onClick={this.returnBack} style={{color: "rgb(15 11 238)",paddingRight:"10px"}}></i>Edit
        		<div className="container" align="center">
					<br/>
					<div className="card" id="1" style={{backgroundColor:"#131316"}} >
					  <div className="card-body" id="11"> 
					  	<h4 id="title"><i>no-title</i></h4>
					  </div>
					</div>
					<br/><br/>
				</div>
				<p>{this.props.date}</p>
				<div style={{color:"white",whiteSpace: "pre-wrap"}} id="body">
					<i>no-body</i>
				</div>
			
			</div>
		)
	}

	returnBack(){
		//document.getElementById("sh").innerHTML=this.props.body;
		ReactDOM.render(<CreateArticle title={this.props.title} body={this.props.body}/>,document.getElementById("root"));
	}
	
}

export default ShowPreview;