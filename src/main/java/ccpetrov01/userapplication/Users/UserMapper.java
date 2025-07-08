package ccpetrov01.userapplication.Users;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserMapper {
    public UserEntityViewDto toUserEntityViewDto(UserEntity userentity){
        return new UserEntityViewDto(
                userentity.getFirstname(),
                userentity.getLastname(),
                userentity.getEmail()
        );
    }

    public UserEntity toUserEntityDto(UserEntityDto dto){
        var userentity = new UserEntity();
        userentity.setFirstname(dto.firstname());
        userentity.setLastname(dto.lastname());
        userentity.setEmail(dto.email());
        userentity.setDob(dto.dob());
        userentity.setPassword(dto.password());
        userentity.setPhonenumber(dto.phonenumber());

        return userentity;
    }

    public List<UserEntity> toUserEntityDtolist(List<UserEntityDto> dtoList){
        return dtoList.stream().map(dto -> {
            var userentity = new UserEntity();
            userentity.setFirstname(dto.firstname());
            userentity.setLastname(dto.lastname());
            userentity.setEmail(dto.email());
            userentity.setDob(dto.dob());
            userentity.setPassword(dto.password());
            userentity.setPhonenumber(dto.phonenumber());


            return userentity;
        }).collect(Collectors.toList());
    }


    public LoginViewDto toLoginViewDto(UserEntity userEntity){
        return new LoginViewDto(userEntity.getToken());
    }
}

