package com.carpooling.backend.model;

import com.carpooling.backend.service.MyGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Data
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = MyGenerator.generatorName)
    @GenericGenerator(name = MyGenerator.generatorName, strategy = "com.carpooling.backend.service.MyGenerator")
    private String userId;

    private String name;
    private String password;
    private String email;
    private long phoneNumber;
    private float rating;
}
