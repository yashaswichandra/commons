package common.repository;


import common.entity.MainCommunicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommunicationRepository extends JpaRepository<MainCommunicationEntity, Long> {

    Optional<List<MainCommunicationEntity>> findBycommunicationFrom(long Id);

    Optional<List<MainCommunicationEntity>> findBycommunicationTo(long Id);
    Optional<MainCommunicationEntity> findBycommunicationToAndCommunicationFrom(long to, long from);

}
