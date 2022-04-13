package joel.fsms.modules.groups.service;

import joel.fsms.modules.groups.domain.Group;
import joel.fsms.modules.groups.domain.CreateGroupRequest;
import joel.fsms.modules.groups.domain.UpdateGroupRequest;

import java.util.List;

public interface GroupService {

    Group create(CreateGroupRequest request);
    Group update(UpdateGroupRequest request, Long id);
    Group findById(Long id);
    void delete(Long id);
    List<Group> findAll();
    void join(Long id);
}
