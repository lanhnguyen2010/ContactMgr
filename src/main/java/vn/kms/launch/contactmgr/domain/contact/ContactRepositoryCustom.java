package vn.kms.launch.contactmgr.domain.contact;

import java.util.List;

import vn.kms.launch.contactmgr.domain.Itemized;
import vn.kms.launch.contactmgr.util.SearchResult;

public interface ContactRepositoryCustom {
    /**
     * Search contact by criteria and company. If companyIds equals null, 
     * the result will contain contacts satisfying the condition of all companies.
     */
    SearchResult<Contact> searchByCriteria(ContactSearchCriteria criteria, List<Integer> companyIds);
    
    /**
     * Get name of companies. If companyIds equals null, this method will return name of all companies on database.
     */
    List<Itemized> getCompanyNames(List<Integer> companyIds);

}
