package com.takalobazar.admin.domain;

import java.util.Date;

public class Exchange {

    private Integer id;
    private Integer proposerUserId;
    private Integer receiverUserId;
    private String status;
    private String note;
    private Date appointmentDate;
    private String meetingPlace;
    private Date createdAt;
    private Date updatedAt;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProposerUserId() {
        return proposerUserId;
    }

    public void setProposerUserId(Integer proposerUserId) {
        this.proposerUserId = proposerUserId;
    }

    public Integer getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(Integer receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
