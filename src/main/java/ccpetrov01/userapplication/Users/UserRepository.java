package ccpetrov01.userapplication.Users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u WHERE " +
            "LOWER(u.firstname) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.lastname) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "ORDER BY u.lastname ASC, u.dob ASC")
    Page<UserEntity> searchUsers(@Param("term") String term , Pageable pageable);
}
