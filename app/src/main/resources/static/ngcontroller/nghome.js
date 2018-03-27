var app = angular.module('nvnControllers', ['ngRoute']);

app.controller('homeCtlr', ['$scope', function($scope){
    $("#date").html( new Date());
}]);

app.controller('profileCtlr', ['$scope','getLoggedInUser',
    'updateAboutMe','fileUpload', 'fileDelete',
    function($scope,getLoggedInUser,updateAboutMe,fileUpload,fileDelete){
        $scope.user = null;
        getLoggedInUser.getUser(function(data){
            $scope.user=data;
            if(data=="" || data=='undefined'){
                $scope.about="";
            }else{
                $scope.about=$scope.user.userProfile.aboutMe;
            }
        });
        $("#date").html( new Date());

        $scope.edit = function(){
            $("#intro").prop( "disabled", false );
            $("#intro").removeClass("textarea");
            $(".save").removeClass("hiding");
        }

        $scope.disable = function(){
            $("#intro").prop( "disabled", true );
            $("#intro").addClass("textarea");
            $(".save").addClass("hiding");
        }

        $scope.update = function(){
            updateAboutMe.updateInfo($scope.user.userId,$scope.about)
            $scope.disable();
        }

        $scope.openModal = function(event){
            $("#picType").val("profile");
            var modal = document.getElementById('picModal');
            var img = document.getElementById(event.target.id);
            var modalImg = document.getElementById("img01");
            var captionText = document.getElementById("caption");

            modal.style.display = "block";
            modalImg.src = img.src;
            captionText.innerHTML = "change pic";
        }

        var span = document.getElementsByClassName("closeWindow")[0];
        span.onclick = function() {
            var modal = document.getElementById('picModal');
            modal.style.display = "none";
        }

        $("#caption").click(function () {
            $("#uploadfile").click();
        });
        
        $scope.safeApply = function(fn) {
        	  var phase = this.$root.$$phase;
        	  if(phase == '$apply' || phase == '$digest') {
        	    if(fn && (typeof(fn) === 'function')) {
        	      fn();
        	    }
        	  } else {
        	    this.$apply(fn);
        	  }
        };
        
        $scope.uploadFile = function(){
            if($scope.user != null && $scope.user != "null" && $scope.user != 'undefined'){
                var file = $scope.myFile;
                console.log('file is ' );
                console.dir(file);
                var uploadUrl = "/profile/update/picture/"+$scope.user.userId;
                fileUpload.uploadFileToUrl(file, uploadUrl,function(res){
                	$scope.safeApply(function(){
                		$scope.user.userProfile.profilePicUrl=res.data.profilePicUrl;
                	});
                	$.unblockUI();
                	$(".closeWindow").click();
                	//location.reload();
                });
                $scope.block();
            }
        };

        $scope.deleteFile = function(){
            fileDelete.deletePic($scope.user.userId);
            location.reload();
        }
        
        $scope.block = function(){
        	    $.blockUI({ css: { 
                    border: 'none', 
                    padding: '15px', 
                    backgroundColor: '#000', 
                    '-webkit-border-radius': '10px', 
                    '-moz-border-radius': '10px', 
                    opacity: .5, 
                    color: '#fff' 
                } }); 
        }

    }]);

app.controller('logoutCtlr', ['$scope','logoutUser' , function($scope,logoutUser){
    //$("#date").html( new Date());
    /* Method to logout */
    $scope.logoutuser = function(){
        logoutUser.doLogout();
    };
}]);

/* Service to get logged in user*/
app.service('getLoggedInUser', [ '$http', function($http) {

    this.getUser = function(cb) {
        $http({
            method: 'GET',
            url: '/getUser'
        }).then(function(response){
            cb(response.data);
        }, function(reason){

        });
    };
}]);

/* Service to log an user out */
app.service('logoutUser', [ '$http', '$location', function($http, $location) {

    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";

    this.doLogout = function() {
        $http({
            url : 'logout',
            method : 'GET'
        }).then(function(response){
            window.location = "/";
        }, function(reason){

        });

    };

}]);

/* Service to update about me */
app.service('updateAboutMe', [ '$http', function($http) {

    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";

    this.updateInfo = function(id,data) {
        var data="about="+data;
        $http({
            url : '/profile/update/'+id,
            method : 'POST',
            data: data
        }).then(function(response){
            return response.data;
        }, function(reason){

        });
    };
}]);


app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

/* Service to upload profile pics */
app.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl,cb){
        var fd = new FormData();
        fd.append('file', file);
        $http({
            method : 'POST',
            contentType: false,
            processData: false,
            cache: false,
            enctype: 'multipart/form-data',
            url:uploadUrl,
            data:fd,
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function(data){
        	cb(data);
        },function(){
        });
    }
}]);

app.directive('customOnChange', function() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var onChangeHandler = scope.$eval(attrs.customOnChange);
            element.bind('change', onChangeHandler);
        }
    };
});

app.service('fileDelete', ['$http', function ($http) {
    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";

    this.deletePic = function(id) {
        $http({
            url : '/profile/delete/picture/'+id,
            method : 'GET',
        }).then(function(response){
            return response.data;
        }, function(reason){

        });
    };
}]);

