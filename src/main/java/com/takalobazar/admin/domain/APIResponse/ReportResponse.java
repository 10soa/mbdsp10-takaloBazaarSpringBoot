package com.takalobazar.admin.domain.APIResponse;

import java.util.List;

public class ReportResponse {

    private List<ReportItem> reports;
    private int totalItems;
    private int totalPages;
    private int currentPage;

    public List<ReportItem> getReports() {
        return reports;
    }

    public void setReports(List<ReportItem> reports) {
        this.reports = reports;
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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
