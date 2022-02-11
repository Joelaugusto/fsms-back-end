package joel.fsms.modules.users.service;

import joel.fsms.modules.address.service.AddressServiceImpl;
import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.domain.UserMapper;
import joel.fsms.modules.users.domain.UserQuery;
import joel.fsms.modules.users.domain.UserRequest;
import joel.fsms.modules.users.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        User user = UserMapper.INSTANCE.toEntity(userRequest);
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
}
