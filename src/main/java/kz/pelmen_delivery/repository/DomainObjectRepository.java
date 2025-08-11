package kz.pelmen_delivery.repository;

import kz.pelmen_delivery.model.entity.DomainObject;
import kz.pelmen_delivery.model.entity.DomainUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainObjectRepository extends JpaRepository<DomainObject, Long> {

    List<DomainObject> findAllByUser(DomainUser user);
}
