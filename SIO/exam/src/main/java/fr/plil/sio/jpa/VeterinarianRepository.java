package fr.plil.sio.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

    /**
     * Return an veterinarian instance with specified name.
     *
     * @param name the name of the veterinarian to return.
     * @return the veterinarian instance or null if not found
     */
    Veterinarian findByName(String name);
}
