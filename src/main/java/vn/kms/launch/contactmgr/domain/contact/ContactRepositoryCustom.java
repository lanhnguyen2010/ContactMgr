package vn.kms.launch.contactmgr.domain.contact;

import java.util.List;

import vn.kms.launch.contactmgr.domain.Itemized;
import vn.kms.launch.contactmgr.util.SearchResult;

public interface ContactRepositoryCustom {
    SearchResult<Contact> searchByCriteria(ContactSearchCriteria criteria);

    List<Itemized> getCompanyNames();

}
