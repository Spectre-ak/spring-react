import React from 'react';
import ReactDOM from 'react-dom';
import ShowMenu from './showMenu.js'
import ShowPreview from './showPreview.js';
import $ from 'jquery'

function makeAJAXrequest(title,body,date){

	var fd = new FormData();
	fd.append("title",title);
	fd.append("body",body);
	fd.append("date",date);
	$.ajax({
		url: 'LikeApost.php',
		type: 'post',
		data: fd, 
		contentType: false,
		processData: false,
		success: function(response){
			//console.log(response); 
			if(response!=="error"){
				document.getElementById("modalId").click();
				document.getElementById("msgDisplay").innerHTML="Some error occured...";
			}
			else{
				document.getElementById("modalId").click();
				var div=document.createElement("div");
				
				var p=document.createElement("p");
				p.innerHTML="Your article url is below";

				var a=document.createElement("a");
				a.href=response["url"];
				a.innerHTML=response["url"];

				var wrapA=document.createElement("p");wrapA.appendChild(a);

				
				var a2c=document.createElement("a");
				a2c.id="countdownID";

				div.appendChild(p);
				div.appendChild(wrapA);
				//div.appendChild(a2);
				div.appendChild(a2c);

				document.getElementById("msgDisplay").innerHTML="";
				document.getElementById("msgDisplay").appendChild(div);
				
				var counter = 2;
				var interval = setInterval(function() {
				    counter++;
				   	try{
				   		document.getElementById("countdownID").innerHTML="Redirecting in "+parseInt(counter/2)+"s";
				   	}
				   	catch(err){
				   		counter--;
				   	}
				    if (counter === 6) {
				        // Display a login box
				        clearInterval(interval);
				    }
				}, 500);

			}
		},
	});
}

var arr={"start":0,"end":0,"txt":""};
class CreateArticle extends React.Component{
	constructor(props){
		super(props);
		this.showMenu=this.showMenu.bind(this);
		this.showPreview=this.showPreview.bind(this);
		this.uploadArticle=this.uploadArticle.bind(this);
		this.onTextSelect=this.onTextSelect.bind(this);
		this.editText=this.editText.bind(this);
		
	}
	componentDidMount(){
		var aForHref=document.createElement("a");
		aForHref.href="create-article";
		aForHref.addEventListener("click",function(ele) {
			ele.preventDefault();
		});
		aForHref.click();

		var windowHeight=window.innerHeight;
		var taHeight=document.getElementById("bodyID").clientHeight;
		windowHeight=windowHeight-36;
		windowHeight=parseInt(windowHeight/24);
		var rows=windowHeight-11;
		document.getElementById("bodyID").rows=rows;
	}
	render(){

		return(
			<div className="container"><br/>
				<i  id="showMenu" className="fa fa-arrow-left fa-lg" aria-hidden="true" onClick={this.showMenu} style={{color: "rgb(15 11 238)",paddingRight:"10px"}}></i>Back
        		<div className="container" align="center">
					
					<div className="car13d" id="1" style={{backgroundColor:"#131316",marginTop:"14px",marginBottom:"14px"}} >
					  <div className="car23d-boxcvdy" id="11"> 
					    <input type="text" id="titleID"  className="form-control" defaultValue={this.props.title} style={{ textAlign:"center",backgroundColor:"#131316",color:"white",border:"1px solid #00bcd4",borderRadius:"10px"}} placeholder="title..." />
					  </div>
					</div>
					
					
					
				</div>

				<div style={{textAlign:"left",marginBottom:"15px"}}>
					<div className="btn-group" role="group" style={{color:"white"}}>
					  <button type="button" id="btn1" onClick={this.editText} name="bold" className="btn mr-2" style={{color:"white",backgroundColor:"#9C27B0"}} ><i id="btn1" className="fa fa-bold" aria-hidden="true"></i></button>
					  <button type="button" id="btn2" onClick={this.editText} name="italic" className="btn mr-2" style={{color:"white",backgroundColor:"#673AB7"}} ><i id="btn2" className="fa fa-italic" aria-hidden="true"></i></button>
					  <button type="button" id="btn3" onClick={this.editText} name="underline" className="btn mr-2" style={{color:"white",backgroundColor:"#F44336"}} ><i id="btn3" className="fa fa-underline" aria-hidden="true"></i></button>
					  <button type="button" id="btn4" onClick={this.editText} name="big" className="btn mr-2" style={{color:"white",backgroundColor:"#FFC107"}} ><i id="btn4" className="fa fa-expand" aria-hidden="true"></i></button>
					  <button type="button" id="btn5" onClick={this.editText} name="small" className="btn mr-2" style={{color:"white",backgroundColor:"#FF5722"}} ><i id="btn5" className="fa fa-compress" aria-hidden="true"></i></button>
					  
					</div>
				</div>

				<div className="form-group">
				    <textarea onSelect={this.onTextSelect} className="form-control" id="bodyID" defaultValue={this.props.body} placeholder="body..." rows="18" style={{ backgroundColor:"#131316",color:"white",border:"1px solid #ff5722",borderRadius:"10px",height:"100%"}}></textarea>
				</div>
				

				<button type="button" className="btn btn-info btn-lg btn-block" style={{color:"white"}} onClick={this.showPreview}>Preview</button>
				<button type="button" className="btn btn-success btn-lg btn-block" style={{color:"white"}} onClick={this.uploadArticle}>Upload</button>

				
				
			
			
				<button type="button" style={{display:"none"}} id="modalId" className="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
				  
				</button>


				<div style={{backgroundColor:"#131316"}} className="modal fade" id="exampleModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				  <div className="modal-dialog" style={{backgroundColor:"#131316"}} role="document">
				    <div className="modal-content" style={{backgroundColor:"#131316"}}>
				    
				      <div className="modal-body" id="msgDisplay" style={{backgroundColor:"#131316"}}>
				        
				      </div>
				      <div className="modal-footer">
				        <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
				      </div>
				    </div>
				  </div>
				</div>

			</div>
		)
	}

	showMenu(ele){
		ReactDOM.render(<ShowMenu title={document.getElementById("titleID").value} body={document.getElementById("bodyID").value}/>,document.getElementById("root"));
	}

	showPreview(){
		var date=new Date();
		var dateAr=date.toString().split(" ");
		date=dateAr[0]+" "+dateAr[1]+" "+dateAr[2]+" "+dateAr[3];
		ReactDOM.render(<ShowPreview date={date} title={document.getElementById("titleID").value} body={document.getElementById("bodyID").value}/>,document.getElementById("root"));
	}

	uploadArticle(){

	var date=new Date();
	var dateAr=date.toString().split(" ");
	date=dateAr[0]+" "+dateAr[1]+" "+dateAr[2]+" "+dateAr[3];
	
	if(document.getElementById("titleID").value===""){
		document.getElementById("modalId").click();
		document.getElementById("msgDisplay").innerHTML="Empty title";

		return;
	}
	if(document.getElementById("bodyID").value===""){
		document.getElementById("modalId").click();
		document.getElementById("msgDisplay").innerHTML="Empty body";

		return;
	}

	
	makeAJAXrequest(document.getElementById("titleID"),document.getElementById("bodyID"),date);
	
	}

	onTextSelect(ele){
		let textarea = ele.target;
    	let selection = textarea.value.substring(textarea.selectionStart, textarea.selectionEnd);
    	arr["start"]=-1;
    	arr["end"]=-1;
    	arr["txt"]="";
    	
    	if(selection==="")return;

    	
    	arr["start"]=textarea.selectionStart;
    	arr["end"]=textarea.selectionEnd;
    	arr["txt"]=textarea.value.substring(arr["start"],arr["end"]);
	}


	editText(ele){
		var textarea = document.getElementById("bodyID");
		
		if(arr["txt"]==="" || arr["start"]===-1 || arr["end"]===-1)return;

		var leftTextOfSelection=textarea.value.substring(0,arr["start"]);
    	var rightTextOfSeletion=textarea.value.substring(arr["end"],textarea.value.length);
    	

    	if(ele["target"]["id"]==="btn1"){
    		var newText=leftTextOfSelection+"<b>"+arr["txt"]+"</b>"+rightTextOfSeletion;
			textarea.value=newText;
		}
    	else if(ele["target"]["id"]==="btn2"){
    		 newText=leftTextOfSelection+"<i>"+arr["txt"]+"</i>"+rightTextOfSeletion;
			textarea.value=newText;
    	}
    	else if(ele["target"]["id"]==="btn3"){
    		 newText=leftTextOfSelection+"<u>"+arr["txt"]+"</u>"+rightTextOfSeletion;
			textarea.value=newText;
    	}
    	else if(ele["target"]["id"]==="btn4"){
    		 newText=leftTextOfSelection+"<big>"+arr["txt"]+"</big>"+rightTextOfSeletion;
			textarea.value=newText;
    	}
    	else if(ele["target"]["id"]==="btn5"){
    		 newText=leftTextOfSelection+"<small>"+arr["txt"]+"</small>"+rightTextOfSeletion;
			textarea.value=newText;
    	}
    	arr["txt"]="";
		arr["start"]=-1;
		arr["end"]=-1;
	}

	
}

export default CreateArticle;