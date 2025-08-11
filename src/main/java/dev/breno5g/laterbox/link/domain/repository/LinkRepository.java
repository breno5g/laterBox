package dev.breno5g.laterbox.link.domain.repository;

import dev.breno5g.laterbox.link.domain.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, UUID> {
    Optional<Link> findByUrl(String url);
    Boolean existsByUrlAndUserId(String url, UUID userId);
    List<Link> findAllByUserId(UUID id);
    Optional<Link> findByUserId(UUID user_id);
    Integer deleteByIdAndUserId(UUID id, UUID userId);
}
