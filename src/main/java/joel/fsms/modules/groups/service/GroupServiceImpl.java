package joel.fsms.modules.groups.service;

import joel.fsms.modules.chat.domain.ChatRequest;
import joel.fsms.modules.chat.service.ChatServiceImpl;
import joel.fsms.modules.groups.domain.CreateGroupRequest;
import joel.fsms.modules.groups.domain.Group;
import joel.fsms.modules.groups.domain.GroupMapper;
import joel.fsms.modules.groups.domain.UpdateGroupRequest;
import joel.fsms.modules.groups.persistence.GroupRepository;
import joel.fsms.modules.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;
    private final ChatServiceImpl chatService;

    @Override
    public Group create(CreateGroupRequest request) {

        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setName(request.getName());
        chatRequest.setMembers(new ArrayList<>());
        Group group = GroupMapper.INSTANCE.toEntity(request);
        group.setChat(chatService.create(chatRequest));
        return repository.save(group);
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
    public List<Group> findAll() {
        return repository.findAll();
    }

    @Override
    public void join(Long id) {
        User loggedUser = loggedUser();
        Group group = findById(id);
        Set<User> userSet = group.getUsers();
        userSet.add(loggedUser);
        group.setUsers(userSet);
        repository.save(group);
        chatService.addMember(group.getChat().getId(), List.of(loggedUser.getId()));
    }

    private User loggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
