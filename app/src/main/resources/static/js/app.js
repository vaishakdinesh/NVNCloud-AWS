var nvnCloudApp = angular.module('homeApp' , ['ngRoute','nvnControllers','angularCSS']);

nvnCloudApp.config(['$routeProvider','$locationProvider', function($routeProvider,$locationProvider){
    $routeProvider
        .when('/welcome',{
            templateUrl: 'homebody.html',
            controller: 'homeCtlr'
        })
        .when('/welcome/profile',{
        	templateUrl: 'profile.html',
            controller: 'profileCtlr',
            css: 'css/profile.css' 	
        })
        .otherwise({
        	template:"<h1>hello</h1><script>alert('hello');</script>"
        });
    $locationProvider.html5Mode(true);
}]);
