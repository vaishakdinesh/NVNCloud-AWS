/**
 *1. Angular controller to check duplicate user name for user registration
 *
 */

var app = angular.module('loginApp', []);


app.controller('loginCtlr', ['$scope','userEmailCheck', 'registerUser', 'loginUser' , 'forgot' , function($scope, userEmailCheck, registerUser, loginUser, forgot) {

    /* Method to clear success message for player */
    $scope.clearSuccessMsg = function(){
        $scope.newUserMessage = "";
    };
    
    $scope.forgetPage = function(){
    	forgot.getForgotPasswordPage();
    }


    /* Method to check user email */
    $scope.validateUserEmail = function() {
        if( $scope.useremail != undefined && ($scope.useremail).length > 0){
            userEmailCheck.isDuplicateUserEmail($scope.useremail, function(result) {
                $scope.invlaidUserEmail = result;
            });
        }
    };

    /* Method to add user */
    $scope.addUser = function(){
        registerUser.saveUser($scope.fname, $scope.lname, $scope.useremail, $scope.password, function(result){
            $scope.fname = "";
            $scope.lname = "";
            $scope.useremail = "";
            $scope.password = "";
            $scope.cpassword = "";
            $scope.regform.$setPristine();
            $scope.regform.$setUntouched();
            $scope.newUserMessage = result;
        });
    };

    /* Method to login */
    $scope.loginuser = function(){
        loginUser.doLogin($scope.useremail, $scope.password, function(result){
            $scope.useremail = "";
            $scope.password = "";
            $scope.loginform.$setPristine();
            $scope.loginform.$setUntouched();
            if(result){
                $scope.loginErrorMsg = "";
            } else {
                $scope.loginErrorMsg = result;
            }

        });
    };

}]);


/* service to register user  */
app.service('loginUser', [ '$http', '$location', function($http, $location) {

    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";

    this.doLogin = function(useremail, password, cb) {
        var data = 'useremail=' +useremail + '&password=' + password;
        $http({
            url : 'log-in',
            method : 'POST',
            data : data
        }).then(function(response) {
            window.location = "/welcome";
            cb(true)
        }, function(reason){
            cb(false);
        });

    };

}]);

/* service to register user  */
app.service('registerUser', [ '$http', function($http) {

    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";

    this.saveUser = function(firstname, lastname, useremail, password, cb) {
        var data = 'firstname=' + firstname + '&lastname=' + lastname
            + '&useremail=' +useremail + '&password=' + password;
        $http({
            url : 'register-user',
            method : 'POST',
            data : data
        }).then(function(response) {
            cb(response.data);
        }, function(reason){
            cb("Unable to process your request now. Try later.");
        });

    };

}]);

/* service to check duplicate user email */
app.service('userEmailCheck', [ '$http', function($http) {

    this.isDuplicateUserEmail = function(email, cb) {
        var data = {
            email : email
        }
        $http({
            url : 'email-check',
            method : 'POST',
            params : data
        }).then(function(response) {
            cb(response.data);
        }, function(failure) {
            cb("Unable to validate user email now. Try later.");
        });
    };

} ]);

/* service to check duplicate user email */
app.service('forgot', [ '$http', function($http) {

    this.getForgotPasswordPage = function() {
       $http({
            url : 'resetpassword',
            method : 'GET',
        }).then(function() {
        	window.location = "/resetpassword";
        }, function() {
        });
    };

} ]);
