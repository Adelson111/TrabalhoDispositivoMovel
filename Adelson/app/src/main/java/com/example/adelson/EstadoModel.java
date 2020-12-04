package com.example.adelson;

import java.util.Date;

public class EstadoModel {

    private String uf;
    private String state;
    private String cases;
    private String deaths;
    private String suspects;
    private String refuses;
    private Date datetime;

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getSuspects() {
        return suspects;
    }

    public void setSuspects(String suspects) {
        this.suspects = suspects;
    }

    public String getRefuses() {
        return refuses;
    }

    public void setRefuses(String refuses) {
        this.refuses = refuses;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
