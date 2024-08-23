package com.takalobazar.admin.domain.APIResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.takalobazar.admin.domain.Category;
import com.takalobazar.admin.domain.Report;

import java.util.Date;
import java.util.List;

public class ReportDetailResponse {

    private ObjectDetail object;
    private List<Report> reports;
    private int totalPages;
    private int currentPage;

    // Getters and Setters

    public ObjectDetail getObject() {
        return object;
    }

    public void setObject(ObjectDetail object) {
        this.object = object;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
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

    public static class ObjectDetail {
        private int id;
        private String name;
        private String description;
        private String image;
        private String status;
        private User user;
        private Category category;
        private Date created_at;
        private Date deleted_At;
        private Date updated_at;
        private Integer user_id;
        private Integer category_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Date created_at) {
            this.created_at = created_at;
        }

        public Date getDeleted_At() {
            return deleted_At;
        }

        public void setDeleted_At(Date deleted_At) {
            this.deleted_At = deleted_At;
        }

        public Date getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(Date updated_at) {
            this.updated_at = updated_at;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getCategory_id() {
            return category_id;
        }

        public void setCategory_id(Integer category_id) {
            this.category_id = category_id;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class User {
            private int id;
            private String username;
            private String email;

            // Getters and Setters

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }
    }
}
