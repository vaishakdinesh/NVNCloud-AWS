var app = angular.module('forgotApp', []);

app.controller('forgotCtlr', ['$scope', 'resetLink',function($scope,resetLink) {
	$scope.resetLink = function(){
		resetLink.resetPassword($scope.useremail,function(){
			 $scope.useremail = "";
	         $scope.resetform.$setPristine();
	         $scope.resetform.$setUntouched();
		});
	}
}]);

/* service to send reset password link  */
app.service('resetLink', [ '$http', '$location', function($http, $location) {

    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";

    this.resetPassword = function(useremail,cb) {
        var data = 'useremail=' +useremail;
        $http({
            url : 'reset',
            method : 'POST',
            data : data
        }).then(function(response) {
        	cb();
        }, function(reason){
            cb(false);
        });

    };

}]);