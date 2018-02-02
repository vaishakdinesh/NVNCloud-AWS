/**
 *1. Angular controller to check duplicate user name and mobile for user registration
 *2. For Player and Ground Crew registration
 */

var app = angular.module('loginApp', []);


app.controller('loginCtlr', ['$scope','userEmailCheck', 'registerUser', 'loginUser' , function($scope, userEmailCheck, registerUser, loginUser) {

    /* Method to clear success message for player */
    $scope.clearSuccessMsg = function(){
        $scope.newUserMessage = "";
    };


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
            $scope.loginErrorMsg = result;
        });
    };

}]);


/* service to register user  */
app.service('loginUser', [ '$http', function($http) {

    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";

    this.doLogin = function(useremail, password, cb) {
        var data = 'useremail=' +useremail + '&password=' + password;
        $http({
            url : 'register-user',
            method : 'POST',
            data : data
        }).then(function(response) {
            cb("");
        }, function(reason){
            cb("Invalid Credentials");
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

    this.isDuplicateUserEmail = function(name, cb) {
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
