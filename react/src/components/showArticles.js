import React from 'react';
import ReactDOM from 'react-dom';
import ShowMenu from './showMenu.js'

class ShowArticles extends React.Component{
	constructor(props){
		super(props);
		this.showMenu=this.showMenu.bind(this);
	}
	render(){
		return(
			<div className="container"><br/>
				<i id="showMenu" className="fa fa-arrow-left fa-lg" aria-hidden="true" onClick={this.showMenu} style={{color: "rgb(15 11 238)",paddingRight:"10px"}}></i>Back
        		
				
        	</div>
		)
	}
	showMenu(ele){
		ReactDOM.render(<ShowMenu/>,document.getElementById("root"));
	}
}
export default ShowArticles;