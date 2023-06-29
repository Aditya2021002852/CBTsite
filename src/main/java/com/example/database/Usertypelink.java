package com.example.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usertypelinks")
public class Usertypelink {
    @Id
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "id", nullable = false, length = 10)
    private String id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}