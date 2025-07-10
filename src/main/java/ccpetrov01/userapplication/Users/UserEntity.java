package ccpetrov01.userapplication.Users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_generator"
    )
    @SequenceGenerator(
            name="user_generator",
            sequenceName = "user_generator",
            allocationSize = 1
    )
    private Integer id;
    @NotBlank(message = "firstname cannot be blank")
    private String firstname;
    @NotBlank(message = "lastname cannot be blank")
    private String lastname;
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email is invalid")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "phonenumber cannot be blank")
    @Column(unique = true)
    private String phonenumber;
    @NotNull(message = "dob cannot be null")
    private LocalDate dob;
    @NotBlank(message = "password cannot be blank")
    @Size(min = 6)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String token;
}
