'use strict';

angular.module('contactmgrApp')
    .controller('ContactController', function($scope, $http, ngTableParams) {
    	$scope.contacts = [{
    		id: 1,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 2,
    		name: 'Tran Van B',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 3,
    		name: 'Le Thi Hong Van',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 4,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 5,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 6,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 7,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 8,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 9,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 10,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}, {
    		id: 11,
    		name: 'Nguyen Van A',
    		mobile: '0935738212',
    		email: 'a@gmail.com',
    		jobTitle: 'Software Engineer',
    		department: 'R&D',
    		company: 'KMS Technology'
    	}];

    	$scope.searchContacts=function(page){
    		if($scope.isLoading){
    			return;
    		}
    		if(!page){
    			$scope.contacts = [];
    			page = 0;
    		}
    		var PAGE_SIZE = 100;
    		$scope.page = page;
    		$scope.isLoading = true;
    		$http.get('http://localhost:8181/download/1?name=contact.json')
    		.success(function(items){
    			$scope.hasMoreContacts=(items.length >= PAGE_SIZE);
    			for (var i = 0; i < items.length; i ++) {
    				$scope.contacts.push(items[i]);
    			}
    		})
    		.finally(function(){
    			$scope.isLoading = false;
    		});
    	}
    	
    	
    	$scope.tableParams = new ngTableParams({
    		page: 1,
    		count: 5
    	},{
    		total:$scope.contacts.length,
    		getData: function($defer,params){
    			$defer.resolve($scope.contactPage = $scope.contacts.slice( (params.page() - 1) * params.count() , params.page() * params.count()));
    		}
    	});


        $scope.checkboxes = {
    	    'checked': false, 
    	    items: {}
     	};

     	$scope.checkedIds = [];

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
        }, true);
    	
    });

