package com.dfortch.rarebook.persistence.repository;

import com.dfortch.rarebook.persistence.entity.Edition;
import com.dfortch.rarebook.persistence.entity.RareBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EditionRepository extends JpaRepository<Edition, Long> {

    Optional<Edition> findByRareBookAndId(RareBook rareBook, Long id);

    void deleteByRareBookAndIdNotIn(RareBook rareBook, Iterable<Long> ids);
}
