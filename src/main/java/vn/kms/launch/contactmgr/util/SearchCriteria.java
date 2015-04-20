package vn.kms.launch.contactmgr.util;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Created by trungnguyen on 4/14/15.
 */
public class SearchCriteria {
    private int pageIndex = 1;
    private int pageSize = 100;
    private String sortField;
    private boolean sortAsc;

    public int getPageIndex() {
        return pageIndex;
    }

    @JsonSetter
    public void setPageIndex(int pageIndex) {
        if (pageIndex <= 0) {
            pageIndex = 1;
        }

        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    @JsonSetter
    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }

        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public boolean isSortAsc() {
        return sortAsc;
    }

    public void setSortAsc(boolean sortAsc) {
        this.sortAsc = sortAsc;
    }
}
