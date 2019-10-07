package fr.plil.sio.persistence.jpa;

import fr.plil.sio.persistence.api.Right;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RightRepository extends JpaRepository<Right, Long> {

    List<Right> findByName(String name);
}
