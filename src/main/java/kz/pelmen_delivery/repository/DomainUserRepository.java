package kz.pelmen_delivery.repository;

import kz.pelmen_delivery.model.entity.DomainUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainUserRepository extends JpaRepository<DomainUser, Long> {

    Optional<DomainUser> findByEmail(String email);
}
