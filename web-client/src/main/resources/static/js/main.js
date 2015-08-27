/**
 * 
 */
angular.module('main', []).controller('home', function($scope, $http) {
	$http.get('/locations/').success(function(data) {
		$scope.greeting = data;
	})
});