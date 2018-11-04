var app = angular.module('app', ['ui.grid','ui.grid.pagination']);

app.controller('FileSystemController', ['$scope','FileSystemService','$http', function ($scope,FileSystemService,$http) {

    $scope.dataContent = {};
    $scope.getAllFiles = function(){
            FileSystemService.getAllFiles().success(function(data){
                $scope.dataContent.files = data;
                console.log($scope.dataContent);
            }).error(function(data){
            });
   };
     $scope.downloadFile = function(fileName){
     var a = document.createElement("a");
     document.body.appendChild(a);
        $http({
			method: 'GET',
			url: '/downloadFile?fileName='+fileName,
			responseType: 'arraybuffer',
			cache: false
		}).then(function successCallback(response) {
            var file = new Blob([response.data], {type: 'application/pdf'});
            downloadFiles(a, fileName, file);
		}, function errorCallback(response) {
			bootbox.alert({
				message: response.errorMessage,
				closeButton: false,
				className: "modal-form-alert",
			});
		});
      };
        function downloadFiles(element, fileName, file) {
            if (window.navigator.msSaveOrOpenBlob) {
                element.onclick = function() {
                    window.navigator.msSaveOrOpenBlob(file, fileName);
                };
                element.click();
            } else {
                var fileURL = URL.createObjectURL(file);
                element.href = fileURL;
                element.download = fileName;
                element.click();
            }
        }
}]);

app.service('FileSystemService',['$http', function ($http) {

    function getAllFiles() {
        return $http({
           url: '/getAllFiles',
           method: "GET",
           headers: {
            'Content-type': 'application/json',
            "Pragma": "no-cache",
            "Expires": -1,
            "Cache-Control": "no-cache"
           }
           });
    }

    function downloadFile(key) {
        return  $http({
          method: 'GET',
          url: '/downloadFile?fileName='+key,
          headers: {
                      'Content-type': 'application/json',
                      "Pragma": "no-cache",
                      "Expires": -1,
                      "Cache-Control": "no-cache"
                     }
        });
    }

    return {
    	getAllFiles:getAllFiles,
    	downloadFile:downloadFile,
    };
	
}]);
