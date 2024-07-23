package com.MMS.Member_Monitoring_System.models;

import jakarta.persistence.*;

@Entity
@Table(name= "area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_number")
    private Long areaNumber;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "area_address")
    private String areaAddress;

    @Column(name = "contact")
    private String contact;

    @Column(name = "status")
    private String status;

    // getters and setters


    public Long getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(Long areaNumber) {
        this.areaNumber = areaNumber;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaAddress() {
        return areaAddress;
    }

    public void setAreaAddress(String areaAddress) {
        this.areaAddress = areaAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
