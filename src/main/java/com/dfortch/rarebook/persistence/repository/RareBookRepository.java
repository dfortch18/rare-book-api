package com.dfortch.rarebook.persistence.repository;

import com.dfortch.rarebook.persistence.entity.RareBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RareBookRepository extends JpaRepository<RareBook, Long>, JpaSpecificationExecutor<RareBook> {

    List<RareBook> findTop10ByOrderByPriceAsc();

    List<RareBook> findTop10ByOrderByPriceDesc();

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);
}
