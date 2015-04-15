package vn.kms.launch.contactmgr.util;

import java.util.List;

public class SearchResult<T> {
    private final SearchCriteria searchCriteria;
    private final List<T> items;
    private final int totalItems;

    public SearchResult(SearchCriteria searchCriteria, List<T> items, int totalItems) {
        this.searchCriteria = searchCriteria;
        this.totalItems = totalItems;
        this.items = items;
    }

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public List<T> getItems() {
        return items;
    }

    public int getTotalItems() {
        return totalItems;
    }
}
