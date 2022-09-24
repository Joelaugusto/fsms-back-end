package joel.fsms.modules.groups.persistence;

import joel.fsms.modules.groups.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g inner join g.users users where users.id = ?1")
    List<Group> findByUsers_IdOrderByCreatedAtDesc(Long id);

//    @Query("select g from Group g inner join g.users users where users.id <> ?1 order by g.createdAt DESC")
    @Query("select g from Group g where g.id not in (select g.id from Group g inner join g.users users where users.id = ?1) order by g.createdAt DESC")
    Page<Group> findByUsers_IdIsNotOrderByCreatedAtDesc(Long id, Pageable pageable);

    @Query("select g.id from Group g inner join g.users users where users.id = ?1")
    List<Long> findIdByUserId(Long id);


//    Page<Group> findByUsers_IdIsNotOrderByCreatedAtDesc(Long id, Pageable pageable);







}
