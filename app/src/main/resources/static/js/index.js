var app = angular.module('homeApp', []);

app.controller('logoutCtlr', ['$scope','logoutUser' , function($scope,logoutUser){
    $("#date").html( new Date());
    /* Method to logout */
    $scope.logoutuser = function(){
        logoutUser.doLogout();
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
