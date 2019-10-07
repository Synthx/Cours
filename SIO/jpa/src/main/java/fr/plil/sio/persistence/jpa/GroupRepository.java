package fr.plil.sio.persistence.jpa;

import fr.plil.sio.persistence.api.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByName(String name);
}
