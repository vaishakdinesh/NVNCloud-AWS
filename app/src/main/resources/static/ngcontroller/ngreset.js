var app = angular.module('resetApp', []);

app.controller('resetCtlr', ['$scope', 'resetpassword',function($scope,resetpassword) {
	$scope.resetPassword = function(){
		$scope.loginErrorMsg = "";
		if(validatePassword()){
			resetpassword.resetUserPassword($scope.password,function(){
				$scope.newUserMessage = "your password has been reset !"
			});
		}else{
			$scope.loginErrorMsg = "passwords don't match";
		}	
	}
	
	function validatePassword(){
		if($scope.password == $scope.confirmpassword){
			return true;
		}else{
			return false;
		}
	}
}]);

app.service('resetpassword', [ '$http', '$location', function($http, $location) {

    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";

    this.resetUserPassword = function(password,cb) {
        var data = 'password=' +password;
        $http({
            url : 'resetpassword',
            method : 'POST',
            data : data
        }).then(function(response) {
        	cb(response);
        }, function(reason){
            cb(false);
        });

    };

}]);