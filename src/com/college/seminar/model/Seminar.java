
package com.college.seminar.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Seminar {

    private String seminarId;
    private String title;
    private String topic;
    private String category;
    private String speakerId;
    private LocalDate seminarDate;
    private LocalTime seminarTime;
    private String venue;
    private int capacity;

    public Seminar(String seminarId, String title, String topic,
                   String category, String speakerId,
                   LocalDate seminarDate, LocalTime seminarTime,
                   String venue, int capacity) {
        this.seminarId = seminarId;
        this.title = title;
        this.topic = topic;
        this.category = category;
        this.speakerId = speakerId;
        this.seminarDate = seminarDate;
        this.seminarTime = seminarTime;
        this.venue = venue;
        this.capacity = capacity;
    }

    public String getSeminarId() {
        return seminarId;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public String getCategory() {
        return category;
    }

    public String getSpeakerId() {
        return speakerId;
    }

    public LocalDate getSeminarDate() {
        return seminarDate;
    }

    public LocalTime getSeminarTime() {
        return seminarTime;
    }

    public String getVenue() {
        return venue;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSpeakerId(String speakerId) {
        this.speakerId = speakerId;
    }

    public void setSeminarDate(LocalDate seminarDate) {
        this.seminarDate = seminarDate;
    }

    public void setSeminarTime(LocalTime seminarTime) {
        this.seminarTime = seminarTime;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
