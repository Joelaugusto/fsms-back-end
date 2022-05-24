package joel.fsms.modules.groups.service;

import joel.fsms.modules.groups.domain.Group;
import joel.fsms.modules.groups.domain.CreateGroupRequest;
import joel.fsms.modules.groups.domain.UpdateGroupRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupService {

    Group create(CreateGroupRequest request);
    Group update(UpdateGroupRequest request, Long id);
    Group findById(Long id);
    void delete(Long id);
    List<Group> findAllMyGroups();
    Page<Group> findOtherGroups(Pageable pageable);
    void join(Long id);
}
