package vn.kms.launch.contactmgr.domain.contact;

import vn.kms.launch.contactmgr.domain.Itemized;
import vn.kms.launch.contactmgr.util.SearchResult;

import java.util.List;

public interface ContactRepositoryCustom {
    SearchResult<Contact> searchByCriteria(ContactSearchCriteria criteria);

    List<Itemized> getCompanyNames();

}
