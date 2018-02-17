$(document).ready(function(){
	var email="";
	function getEmail() {
        var person = prompt("Please enter the email of the profile you want to see", "Harry Potter");
        if (person != null) {
        	email = person;
        }
    }
	getEmail();
	var data="email="+email;
	$.ajax({
		  type: "GET",
		  url : "/getuserprofile/",
		  data:data
	}).success(function(response){
		$("#username").html(response.firstName+" "+response.lastName);
		$("#intro").html(response.userProfile.aboutMe);
		if(response.userProfile.profilePicUrl != ""){
			$("#myImg").attr("src",response.userProfile.profilePicUrl);
		}else{
			$("#myImg").attr("src","images/default.jpg" );
		}
	}).error(function(){
		alert("failure");
	});
})