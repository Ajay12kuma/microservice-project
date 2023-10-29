package com.user.service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "micro_user_t")
@Data
public class User {
    @Id
    @Column(name = "id")
    private String userId;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "abaout")
    private String abaout;
    @Transient
    private List<Rating> ratings;
}
