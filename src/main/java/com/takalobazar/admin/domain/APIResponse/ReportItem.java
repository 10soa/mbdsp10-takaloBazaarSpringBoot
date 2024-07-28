package com.takalobazar.admin.domain.APIResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportItem {

    private int object_id;
    private String reportCount;
    private String object_name;
    @JsonProperty("Object")
    private ObjectDetails objectDetails;

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public String getReportCount() {
        return reportCount;
    }

    public void setReportCount(String reportCount) {
        this.reportCount = reportCount;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public ObjectDetails getObjectDetails() {
        return objectDetails;
    }

    public void setObjectDetails(ObjectDetails objectDetails) {
        this.objectDetails = objectDetails;
    }

    public static class ObjectDetails {
        private int id;
        private String name;

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
    }
}
