package it.alecsferra.letseat.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @NotBlank
    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Owner
    private User owner;

    @NotNull
    @Column
    private String description;

    @NotNull
    @NotBlank
    @Column
    private String address;

    @NotNull
    @Column
    @Digits(integer = 10, fraction = 6)
    private Double lat;

    @NotNull
    @Column
    @Digits(integer = 10, fraction = 6)
    private Double lng;

}

@Constraint(validatedBy = OwnerValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@interface Owner {
    String message() default "Invalid owner";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class OwnerValidator implements ConstraintValidator<Owner, User> {

    @Override
    public void initialize(Owner contactNumber) {}

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getRole() == User.Type.RESTAURANT_OWNER;
    }

}