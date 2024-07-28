package com.takalobazar.admin.domain.APIResponse;

import com.takalobazar.admin.domain.Category;

import java.util.List;

public class CategoriesResponse {
    private List<Category> categories;
    private Integer totalPages;
    private Integer currentPage;

    // Getters and setters
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
