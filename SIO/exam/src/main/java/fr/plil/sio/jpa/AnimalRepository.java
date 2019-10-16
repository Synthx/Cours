package fr.plil.sio.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Animal findByName(String name);
}
