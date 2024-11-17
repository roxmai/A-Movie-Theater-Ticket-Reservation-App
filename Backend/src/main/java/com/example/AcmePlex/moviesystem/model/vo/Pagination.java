package com.example.AcmePlex.moviesystem.model.vo;

public class Pagination {
    private int currentPage;
    private int pageSize;
    private int totalItems;
    private int totalPages;

    public Pagination(int page, int pageSize, int totalItems) {
        this.currentPage = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = Math.max(1, (int)Math.ceil((double)totalItems / pageSize));
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
