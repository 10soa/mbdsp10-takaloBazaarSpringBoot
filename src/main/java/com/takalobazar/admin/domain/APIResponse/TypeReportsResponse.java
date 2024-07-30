package com.takalobazar.admin.domain.APIResponse;
import com.takalobazar.admin.domain.TypeReport;
import java.util.List;

public class TypeReportsResponse {
    private List<TypeReport> typeReports;
    private Integer totalPages;
    private Integer currentPage;

    // Getters and setters
    public List<TypeReport> getTypeReports() {
        return typeReports;
    }

    public void setTypeReports(List<TypeReport> typeReports) {
        this.typeReports = typeReports;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
