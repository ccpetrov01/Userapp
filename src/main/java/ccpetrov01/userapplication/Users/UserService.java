package ccpetrov01.userapplication.Users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserEntity> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public void addNewUser(UserEntity userEntity) {

        userRepository.save(userEntity);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("User with ID " + id + " doesn't exist.");
        }
        userRepository.deleteById(id);
    }

    public void addManyUsers(List<UserEntity> userEntityList) {
        userRepository.saveAll(userEntityList);
    }


    public void updateUserData(Integer id, String firstname, String lastname,String email) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("User with ID " + id + " doesn't exist.");
        }
        UserEntity user = new UserEntity();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        userRepository.save(user);

    }

    public Page<UserEntity> searchUsers(String term, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (term == null || term.isBlank()) {
            throw new IllegalArgumentException("Search term cannot be empty.");
        }
        return userRepository.searchUsers(term, pageable);
    }
}

