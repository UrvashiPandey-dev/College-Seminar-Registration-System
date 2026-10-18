
package com.college.seminar.model;

import java.time.LocalDate;

public class Registration {

    private String registrationId;
    private String studentId;
    private String seminarId;
    private LocalDate registrationDate;
    private String status;

    public Registration(String registrationId, String studentId,
                        String seminarId, LocalDate registrationDate,
                        String status) {
        this.registrationId = registrationId;
        this.studentId = studentId;
        this.seminarId = seminarId;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSeminarId() {
        return seminarId;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setSeminarId(String seminarId) {
        this.seminarId = seminarId;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

