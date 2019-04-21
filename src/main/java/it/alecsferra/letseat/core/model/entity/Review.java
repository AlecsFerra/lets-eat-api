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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Reviewer
    private User reviewer;

    @NotNull
    @Column
    @Stars
    private Integer stars;

    @NotNull
    @Column
    private String comment;

}

@Constraint(validatedBy = StarValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@interface Stars {
    String message() default "Invalid rating";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class StarValidator implements ConstraintValidator<Stars, Integer> {

    @Override
    public void initialize(Stars contactNumber) {}

    @Override
    public boolean isValid(Integer rating, ConstraintValidatorContext constraintValidatorContext) {
        return rating != null && rating <= 10 && rating >= 0;
    }

}

@Constraint(validatedBy = ReviewerValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@interface Reviewer {
    String message() default "Invalid reviewer";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class ReviewerValidator implements ConstraintValidator<Reviewer, User> {

    @Override
    public void initialize(Reviewer contactNumber) {}

    @Override
    public boolean isValid(User reviewer, ConstraintValidatorContext constraintValidatorContext) {
        return reviewer.getRole() == User.Type.USER;
    }

}
