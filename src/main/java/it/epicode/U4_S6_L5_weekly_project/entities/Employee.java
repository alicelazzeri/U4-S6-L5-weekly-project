package it.epicode.U4_S6_L5_weekly_project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees", indexes = {@Index(name = "id_email_unique", columnList = "email", unique = true)})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")

public class Employee {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String avatar;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Device> devices = new ArrayList<>();
}
