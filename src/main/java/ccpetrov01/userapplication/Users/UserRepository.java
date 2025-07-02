package ccpetrov01.userapplication.Users;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Integer, UserEntity> {
}
