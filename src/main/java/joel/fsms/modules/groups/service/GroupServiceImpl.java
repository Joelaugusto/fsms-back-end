package joel.fsms.modules.groups.service;

import joel.fsms.modules.chat.domain.ChatRequest;
import joel.fsms.modules.chat.service.ChatServiceImpl;
import joel.fsms.modules.groups.domain.CreateGroupRequest;
import joel.fsms.modules.groups.domain.Group;
import joel.fsms.modules.groups.domain.GroupMapper;
import joel.fsms.modules.groups.domain.UpdateGroupRequest;
import joel.fsms.modules.groups.persistence.GroupRepository;
import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;
    private final ChatServiceImpl chatService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Group create(CreateGroupRequest request) {

        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setName(request.getName());
        chatRequest.setMembers(new ArrayList<>());


        Group group = GroupMapper.INSTANCE.toEntity(request);

        User user = loggedUser();
        HashSet hashSet = new HashSet();
        hashSet.add(user);
        group.setUsers(hashSet);
        group.setChat(chatService.create(chatRequest));

        Group savedGroup = repository.save(group);
        Set<Group> groups = user.getGroups();
        groups.add(savedGroup);
        user.setGroups(groups);
        userRepository.save(user);
        return savedGroup;
    }



    @Override
    public Group update(UpdateGroupRequest request, Long id) {
        Group group = findById(id);
        GroupMapper.INSTANCE.copyProprieties(request,group);
        return repository.save(group);
    }

    @Override
    public Group findById(Long id) {
        return repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "GROUP NOT FOUND"));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Group> findAllMyGroups() {
        return repository.findByUsers_IdOrderByCreatedAtDesc(loggedUser().getId());
    }

    @Override
    public Page<Group> findOtherGroups(Pageable pageable) {
        return repository.findByUsers_IdIsNotOrderByCreatedAtDesc(loggedUser().getId(), pageable);
    }


    @Override
    @Transactional
    public void join(Long id) {
        User loggedUser = loggedUser();
        Group group = findById(id);
        Set<User> userSet = group.getUsers();
        userSet.add(loggedUser);
        group.setUsers(userSet);

        Group savedGroup = repository.save(group);
        Set<Group> groups = loggedUser.getGroups();
        groups.add(savedGroup);
        loggedUser.setGroups(groups);
        userRepository.save(loggedUser);
        chatService.addMember(group.getChat().getId(), List.of(loggedUser.getId()));
    }

    private User loggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
