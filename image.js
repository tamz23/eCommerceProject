
var a;
var CurrentImageCounter = 0;
var CurrentHotelCounter = 0;
var foldName;
var imageCollection;
var saveImageCount = 1;
var ignoredItemCount = 1;

$(document).ready(function(){

	
	document.onkeydown = function(e) {
	    switch (e.keyCode) {
	        case 37:
	            prev();
	            break;
	        /*case 38:
	            alert('up');
	            break;*/
	        case 39:
	            next();
	            break;
	        case 13:
	        	saveFoundImage('found');
	            break;
	        case 80:
	        	saveFoundImage('partFound');
	        	break;
	        case 83:
	        	ignored();
	        	break;	
	    }
	};
	
	
	$.ajax({
		url: 'http://localhost:8080/School/student/accessImages',
		method: "GET",
		contentType: "application/json",
		//data: JSON.stringify(studentJSON),
		success : function(response){
			//alert(response);
			
			a = response;
			
			
			getImages(response[0]);
			
		},
		error: function(e){
			console.log(e);
			//alert('Error in saving');
		}
	});
	
});	

function getImages(foldername){

	foldName  = foldername;
	//alert(foldername);
	
	$.ajax({
		url: 'http://localhost:8080/School/student/accessIma/'+foldName,
		method: "GET",
		contentType: "application/json",
		//data: JSON.stringify(studentJSON),
		success : function(response){
			
			
			if(response.scenario == "multiProd"){
				$('#folderName').text(foldName);
				
				$('#multiProd').empty();
				for(var i=0;i<response.files.length;i++){
					
					$('#addrOnFile').text(response.fileAddress);
					$('#multiProd').append('<li id="'+response.files[i].folderName+'" style="cursor:pointer" onclick="getSubfolderInfo(id)" >'+response.files[i].addressInfo+'</li>');
				}
				$('#images').empty();	
			}
			else if( response.scenario == "singleProd"){
				imageCollection = response.files;
				
				$('#multiProd').empty();
				$('#images').empty();
				$('#images').append('<img alt="" src="../img/output/'+foldName+'/'+imageCollection[0]+'" display="block" ></img>');
				$('#folderName').text(foldName);
				$('#fileName').text(imageCollection[0]);
				$('#addrOnFile').text(response.fileAddress);
			}
			else{
				
			}
			
			console.log(response);
		},
		error: function(e){
			console.log(e);
			//alert('Error in saving');
		}
	});
	
}


var num = 0;
function next(){
	
	if(CurrentImageCounter<imageCollection.length-1){
		++CurrentImageCounter;
		
		
		
		$('#images').empty();
		$('#images').append('<img alt="" src="../img/output/'+foldName+'/'+imageCollection[CurrentImageCounter]+'" display="block" ></img>');
		$('#fileName').text(imageCollection[CurrentImageCounter]);
	}
	else{
		alert("Scroll left to view images backward");
	}


	
}

function prev(){
	
	if(CurrentImageCounter > 0){
		
		--CurrentImageCounter;
		
		$('#images').empty();
		$('#images').append('<img alt="" src="../img/output/'+foldName+'/'+imageCollection[CurrentImageCounter]+'" display="block" ></img>');
		$('#fileName').text(imageCollection[CurrentImageCounter]);
	}
	else {
		alert("Scroll right to view images forward");
	}

}

function nextHotel(){
	
	
	CurrentImageCounter = 0;
	
	if(CurrentHotelCounter<a.length-1){
		++CurrentHotelCounter;
		getImages(a[CurrentHotelCounter]);
	}
	else{
		alert("This is one last hotel in the queue");
	}
	
	
}

function ignored(){
	
	
	$.ajax({
		url: 'http://localhost:8080/School/student/trackIgnoredItem/'+ foldName +'/'+ ignoredItemCount,
		method: "POST",
		contentType: "application/json",
		//data: JSON.stringify(studentJSON),
		success : function(response){
			
			console.log(response);
			
			if(response = "saved"){
				
				ignoredItemCount++;
				
				CurrentImageCounter = 0;
				
				if(CurrentHotelCounter<a.length-1){
					++CurrentHotelCounter;
					getImages(a[CurrentHotelCounter]);
				}
				else{
					alert("This is one last hotel in the queue");
				}
				
			}
			
		},
		error: function(e){
			console.log(e);
			//alert('Error in saving');
		}
	});
	
	
	
}



function saveFoundImage(type){
	
	if(type=="partFound"){
		
		if($('#reasonPartImage').val()== 0){
			alert('choose a reason');
			$('#reasonPartImage').focus();
			return;
		}
		
	}
	
	if($('#extn').val() == ''){
		alert('enter the extension');
		$('#extn').focus();
		return;
	}
	
	
	var folName = foldName;
	var selectedImage = imageCollection[CurrentImageCounter];
	//alert(selectedImage);
	var saveName = foldName+"\."+$('#extn').val();
	alert(saveName);
	var reason=$('#reasonPartImage').val();
	
	$.ajax({
		url: 'http://localhost:8080/School/student/saveimage/'+ type +'/'+ folName+'/'+selectedImage+"/"+saveName+"/"+saveImageCount+"/"+reason,
		method: "POST",
		contentType: "application/json",
		//data: JSON.stringify(studentJSON),
		success : function(response){
			
			console.log(response);
			
			if(response = "saved"){
				nextHotel();
				saveImageCount++;
				$('#extn').val('');
				$('#reasonPartImage').val('0');
				alert("Saved and next hotel loaded ")
				
			}
			
		},
		error: function(e){
			console.log(e);
			//alert('Error in saving');
		}
	});
	
}

function getSubfolderInfo(id){

	
	if(id=="main"){
	
		
		$.ajax({
			url: 'http://localhost:8080/School/student/accessImageFromMain/'+foldName,
			method: "GET",
			contentType: "application/json",
			//data: JSON.stringify(studentJSON),
			success : function(response){
				
				imageCollection = response;
				$('#multiProd').empty();
				$('#images').empty();
				$('#images').append('<img alt="" src="../img/output/'+foldName+'/'+imageCollection[0]+'" display="block" ></img>');
				$('#folderName').text(foldName);
				$('#fileName').text(imageCollection[0]);
				
				console.log(response);
			},
			error: function(e){
				console.log(e);
				//alert('Error in saving');
			}
		});

		
	}
	
	
	else {
		
		var key = foldName+'/'+id;
		$.ajax({
			url: 'http://localhost:8080/School/student/accessIma/'+key,
			method: "GET",
			contentType: "application/json",
			//data: JSON.stringify(studentJSON),
			success : function(response){
				
				imageCollection = response;
				$('#multiProd').empty();
				$('#images').empty();
				$('#images').append('<img alt="" src="../img/output/'+foldName+'/'+imageCollection[0]+'" display="block" ></img>');
				$('#folderName').text(foldName);
				$('#fileName').text(imageCollection[0]);
				
				console.log(response);
			},
			error: function(e){
				console.log(e);
				//alert('Error in saving');
			}
		});

		
	}
		
	
}


