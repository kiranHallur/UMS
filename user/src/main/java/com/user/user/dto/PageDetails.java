package com.user.user.dto;

import java.util.List;

public class PageDetails {

    private List<PropertyUserDto> users;
    private boolean isLast;
    private boolean isFirst;
    private int pageNumber;
    private int totalPages;
    private int totalElements;

    public List<PropertyUserDto> getUsers() {
        return users;
    }

    public void setUsers(List<PropertyUserDto> users) {
        this.users = users;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
