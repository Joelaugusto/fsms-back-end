package joel.fsms.modules.users.service;

import joel.fsms.modules.address.service.AddressServiceImpl;
import joel.fsms.modules.users.domain.*;
import joel.fsms.modules.users.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressServiceImpl addressService;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"USER_NOT_FOUND"));
    }

    @Override
    public User update(Long id, UserRequest userRequest) {
        User user = findById(id);
        UserMapper.INSTANCE.copyProprieties(userRequest, user);
        return userRepository.save(user);
    }

    @Override
    public User create(UserRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new ResponseStatusException(HttpStatus.FOUND, "O Email já é usado!");
        }

        if(userRepository.existsByPhone(userRequest.getPhone())){
            throw new ResponseStatusException(HttpStatus.FOUND, "O numero de telefone já é usado!");
        }

        User user = UserMapper.INSTANCE.toEntity(userRequest);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setAddress(addressService.save(userRequest.getAddress()));
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"USER_NOT_FOUND");
        }

    }

    @Override
    public Page<User> findAll(Pageable pageable, UserQuery userQuery) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Map<String, Boolean> verifyIfExists(UserUniqueConstraints uniqueConstraints) {
        return Map.of("email", userRepository.existsByEmail(uniqueConstraints.getEmail()),
                "phone", userRepository.existsByPhone(uniqueConstraints.getPhone()));
    }
}
