package com.minhnhat.Quanlysanbong.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Table(name = "stadium")
@Entity
@Data
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String stadiumName;

    @NotBlank
    private String description;

    @OneToMany(mappedBy = "stadium", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<StadiumPrice> stadiumPrice;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(	name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Authority> roles = new HashSet<>();

}
