package ccpetrov01.userapplication.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    void updateEmailbyId(Integer id , String email);
    void updateFirstnameandLastname(Integer id , String firstname , String lastname);
}
