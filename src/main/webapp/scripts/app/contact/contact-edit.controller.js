'use strict';

angular.module('contactmgrApp')
    .controller('EditContactController', function($scope, ContactService) {
        $scope.contact;
        
        $scope.companies;
        
        $scope.selectedCompany = null;
        
        $scope.$watch('contact.work.companyId', function(result){
            for(var c in $scope.companies){
                if($scope.companies[c].id === $scope.contact.work.companyId){
                    $scope.contact.work.company = $scope.companies[c];
                    $scope.selectedCompany = $scope.companies[c];
                    console.log( $scope.contact.work.company);
                    break;
                }
            }
        });
        
        $
        
        $scope.countries;
        
        
        // save a contact
        $scope.saveContact = function(){
            console.log("Contact: " + $scope.contact.firstName);
            ContactService.createContact($scope.contact)
                .success(function(data, status, headers, config) {
                    // the contact is saved
                    console.log("Saved contact!");
                    window.location = '#contact';
                }).error(function(data, status, headers, config) {
                // has error
                    console.log("Error: " + status);
                });
         };
         
         $scope.getCompanies = function(){
             ContactService.getAllCompanies()
                 .success(function(data, status, headers, config) {
                     $scope.companies = data;
                 });
         };
         $scope.getCompanies();
         
         $scope.getCountries = function(){
             ContactService.getCountries()
                 .success(function(data, status, headers, config) {
                 $scope.countries = data;
             });
         }
         $scope.getCountries();
         
         $scope.hasSelectedCompany = function(){
             return ($scope.selectedCompany != null);
         };
         
         $scope.saveCompany = function(){
             if($scope.hasSelectedCompany()){
            	 if($scope.selectedCompany.id >0){
            		// Update a existing company
                     ContactService.updateCompany($scope.selectedCompany)
                     .success(function(data, status, headers, config) {
                         console.log("Saved company!");
                         console.log(data);
                         
                         // close dialog
                         $('#companyInfoModal').modal('toggle');
                     })
                     .error(function(data, status, headers, config) {
                         // has error
                         console.log("Error: " + status);
                     });
            	 } else{
		             ContactService.createCompany($scope.selectedCompany)
		                 .success(function(data, status, headers, config) {
		                     console.log("Crated company!");
		                     console.log(data);
		                     
		                     // reload list of companies
		                     $scope.getCompanies();
		                     
		                     // close dialog
		                     $('#companyInfoModal').modal('toggle');
		                     console.log('New company: ' + $scope.contact.work.companyId);
		                 })
		                 .error(function(data, status, headers, config) {
		                     // has error
		                     console.log("Error: " + status);
		                     
		                 });
		         }
            	
	         }
         };
         
         $scope.openDialogCreateCompany = function(){
             $scope.selectedCompany = null;
             $('#companyInfoModal').modal('show');
         };
         
         $scope.openDialogUpdateCompany = function(){
             $scope.selectedCompany = $scope.contact.work.company;
             $('#companyInfoModal').modal('show');
         };
    });