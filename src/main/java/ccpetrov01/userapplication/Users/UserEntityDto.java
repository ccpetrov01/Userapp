package ccpetrov01.userapplication.Users;

import java.time.LocalDate;

public record UserEntityDto(
        String firstname,
        String lastname,
        String email,
        String phonenumber,
        String password,
        LocalDate dob,
        String role
) {
}
