package com.crowdfunding.model;

import javax.persistence.*;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_founder")
    private int idFounder;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date_foundation")
    private String dateFoundation;

    @Column(name = "cash")
    private Long cash;

    public Long getCash() {
        return cash;
    }

    public void setCash(Long cash) {
        this.cash = cash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFounder() {
        return idFounder;
    }

    public void setIdFounder(int idFounder) {
        this.idFounder = idFounder;
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

    public String getDateFoundation() {
        return dateFoundation;
    }

    public void setDateFoundation(String dateFoundation) {
        this.dateFoundation = dateFoundation;
    }
}
