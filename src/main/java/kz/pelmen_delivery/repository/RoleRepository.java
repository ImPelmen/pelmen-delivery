package kz.pelmen_delivery.repository;

import kz.pelmen_delivery.model.entity.Role;
import kz.pelmen_delivery.model.enums.RoleTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleTitle name);
}
