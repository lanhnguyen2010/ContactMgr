'use strict';

angular.module('contactmgrApp')
    .controller('ContactController', function($scope, $http, ngTableParams) {
    	
    	(function init(){
    		//add object contacts in $data;
    		$scope.contacts = [
    		{
    			id:1,
    			name:"ThanhTuong",
    			jobtitle: "Developer",
    			mobile: "0124578956",
    			department: "PDL",
    			email:"abc@gmail.com",
    			company:"KMS Technology"
    		},
    		{
    			id:"2",
    			name:"David",
    			jobtitle: "Developer",
    			mobile: "012488888956",
    			department: "CH",
    			email:"david@gmail.com",
    			company: "KMS Technology"
    		},
    		{
    			"id":"3",
    			"name":"Marry",
    			"jobtitle": "Developer",
    			"mobile": "012457895786",
    			"department": "PDL",
    			"email":"marry@gmail.com",
    			"company":"KMS Technology"
    		},
    		{
    			"id":"4",
    			"name":"Trang",
    			"jobtitle": "Tester",
    			"mobile": "01224578956",
    			"department": "PDL",
    			"email":"trang@gmail.com",
    			"company":"Prudent"
    		},
    		{
    			id:5,
    			name:"ThanhTuong1",
    			jobtitle: "Developer",
    			mobile: "0124578956",
    			department: "PDL",
    			email:"abc@gmail.com",
    			company:"KMS Technology"
    		},
    		{
    			"id":"6",
    			"name":"David1",
    			"jobtitle": "Developer",
    			"mobile": "012488888956",
    			"department": "CH",
    			"email":"david@gmail.com",
    			"company":"KMS Technology"
    		},
    		{
    			"id":"7",
    			"name":"Marry1",
    			"jobtitle": "Developer",
    			"mobile": "012457895786",
    			"department": "PDL",
    			"email":"marry@gmail.com",
    			"company":"KMS Technology"
    		},
    		{
    			"id":"8",
    			"name":"Trang1",
    			"jobtitle": "Tester",
    			"mobile": "01224578956",
    			"department": "PDL",
    			"email":"trang@gmail.com",
    			"company":"Prudent"
    		},

    		{
    			id:9,
    			name:"ThanhTuong2",
    			jobtitle: "Developer",
    			mobile: "0124578956",
    			department: "PDL",
    			email:"abc@gmail.com",
    			company:"KMS Technology"
    		},
    		{
    			"id":"10",
    			"name":"David2",
    			"jobtitle": "Developer",
    			"mobile": "012488888956",
    			"department": "CH",
    			"email":"david@gmail.com",
    			"company":"KMS Technology"
    		},
    		{
    			"id":"11",
    			"name":"Marry2",
    			"jobtitle": "Developer",
    			"mobile": "012457895786",
    			"department": "PDL",
    			"email":"marry@gmail.com",
    			"company":"KMS Technology"
    		},
    		{
    			"id":"12",
    			"name":"Trang2",
    			"jobtitle": "Tester",
    			"mobile": "01224578956",
    			"department": "PDL",
    			"email":"trang@gmail.com",
    			"company":"Prudent"
    		},
    		];

    		$scope.filter = {
    			name: '',
    			mobile: '',
    			jobtitle: '',
    			department:'',
    			email:'',
    			company:'',
    			page:''
    		};
    	})();

    	$scope.searchContacts=function(page){
    		if($scope.isLoading){
    			return;
    		}
    		if(!page){
    			$scope.contacts = [];
    			page = 0;
    		}
    		var PAGE_SIZE = 10;
    		$scope.page = page;
    		$scope.isLoading = true;
    		$http.get('https://www.facebook.com/download/1420742551564857/contact.json')
    		.success(function(items){
    			$scope.hasMoreContacts=(items.length >= PAGE_SIZE);
    			for (var i = 0; i <items.length; i++) {
    				$scope.contacts.push(items[i]);
    			}
    		})
    		
//    		.finally(function($scope){
//    			$scope.isLoading = false;
//    		});
    	}

    	//every pages is 10 pages;
    	$scope.tableParams = new ngTableParams({
    		page: 1,
    		count: 10
    	},{
    		total:$scope.contacts.length,
    		getData: function($defer,params){
    			$defer.resolve($scope.contactPage = $scope.contacts.slice( (params.page() - 1) * params.count() , params.page() * params.count()));
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
    			$scope.checkboxes.checked=(checked == total);
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
            
    	},true);
    	
    	$scope.namecompany = 'KMS Technology';
    });

