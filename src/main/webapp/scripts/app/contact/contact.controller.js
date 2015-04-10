'use strict';

angular.module('contactmgrApp')
    .controller('ContactController', function($scope, $http, ngTableParams) {
    	
    	$scope.contacts = [];
    	
    	$scope.filter = {
    			name: '',
    			mobile: '',
    			jobtitle: '',
    			department:'',
    			email:'',
    			company:'',
    			page:''
    		};
    	
    	$scope.page = 1;
    	
    	
    	$scope.searchContacts=function(paging){
    		if($scope.isLoading){
    			return;
    		}
    		var PAGE_SIZE = 10;
    		$scope.isLoading = true;
    		
    		var data= {contact: $scope.filter, page: $scope.page, pagesize: PAGE_SIZE};
    		var jsonData = JSON.stringify(data);	
    		console.log(jsonData);
    		$http.post('api/contacts/search?data=' + jsonData)
    		.success(function(data){
    			$scope.contacts = [];
    			var items = data['contact'];
    			$scope.total = data['total'];
    			$scope.contacts = items;
    			$scope.isLoading = false;    		
    			
    			if (!paging) {
    				$scope.tableParams.reload();
    			}
    		}).
    		error(function(data, status, headers, config) {
    			//
    		});
    	}
    	
    	$scope.tableParams = new ngTableParams({
    		page: 1,
    		count: 10
    	},{
    		counts: [],
    		total: $scope.total,
    		getData: function($defer,params){
    			$scope.page = params.page();
    			$defer.resolve($scope.contacts);
    			$scope.searchContacts(true);
    		}
    	});
    	
     	$scope.checkboxes = {'checked':false, items: {} };

    	//watch for check all checkboxes
    	$scope.$watch('checkboxes.checked', function(value){
    		angular.forEach($scope.contactPage,function(item){
    			if(angular.isDefined(item.id)){
    				$scope.checkboxes.items[item.id] = value;
    			}
    		});
    	});

    	//watch for data checkboxes
    	$scope.$watch('checkboxes.items',function(values){
    		if(!$scope.contactPage){
    			return;
    		}
    		var checked = 0, unchecked = 0, total = $scope.contactPage.length;
    		angular.forEach($scope.contactPage, function(item) {
    			checked += ($scope.checkboxes.items[item.id]) || 0;
    			unchecked += (!$scope.checkboxes.items[item.id]) || 0;
    		});
    		if((unchecked == 0)||(checked == 0)){
    			$scope.checkboxes.checked =(checked == total);
    		}
    		// grayed checkboxes
    		angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));

            // Create checked id list
            $scope.checkedIds = [];
            for (var item in $scope.checkboxes.items) {
            	if ($scope.checkboxes.items[item]) {
            		$scope.checkedIds.push(parseInt(item));
            	}
            }
        }, true);
    	
    });

