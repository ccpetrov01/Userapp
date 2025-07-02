package ccpetrov01.userapplication.Users;

import java.time.LocalDate;

public record UseEntityDto(
 String firstname,
 String lastname,
 String email,
 LocalDate dob
) {
}
