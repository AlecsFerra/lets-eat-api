package it.alecsferra.letseat.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 60, max = 60) // BCrypt Size
    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column
    private Type role;

    public enum Type {
        USER,
        RESTAURANT_OWNER,
        ADMIN
    }

}