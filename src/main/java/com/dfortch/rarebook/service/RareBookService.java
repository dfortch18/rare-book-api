package com.dfortch.rarebook.service;

import com.dfortch.rarebook.dto.entity.RareBookDTO;
import com.dfortch.rarebook.dto.request.CreateRareBookRequest;
import com.dfortch.rarebook.dto.request.GetRareBooksFilterOptions;
import com.dfortch.rarebook.dto.request.UpdateRareBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RareBookService {

    Page<RareBookDTO> getBooks(Pageable pageable, GetRareBooksFilterOptions filterOptions);

    RareBookDTO getBookById(Long id);

    Long createBook(CreateRareBookRequest requestDto);

    void updateBook(Long id, UpdateRareBookRequest requestDto);

    void deleteBook(Long id);
}
