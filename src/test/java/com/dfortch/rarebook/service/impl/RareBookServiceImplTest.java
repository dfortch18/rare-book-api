package com.dfortch.rarebook.service.impl;

import com.dfortch.rarebook.common.exception.RecordConflictException;
import com.dfortch.rarebook.common.exception.RecordNotFoundException;
import com.dfortch.rarebook.dto.entity.RareBookDTO;
import com.dfortch.rarebook.dto.request.*;
import com.dfortch.rarebook.mapper.RareBookMapper;
import com.dfortch.rarebook.persistence.entity.Category;
import com.dfortch.rarebook.persistence.entity.Edition;
import com.dfortch.rarebook.persistence.entity.RareBook;
import com.dfortch.rarebook.persistence.repository.CategoryRepository;
import com.dfortch.rarebook.persistence.repository.EditionRepository;
import com.dfortch.rarebook.persistence.repository.RareBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RareBookServiceImplTest {

    @Mock
    private RareBookRepository rareBookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EditionRepository editionRepository;

    @Mock
    private RareBookMapper rareBookMapper;

    @InjectMocks
    private RareBookServiceImpl rareBookService;

    private RareBook rareBook;

    private Category category;

    private CreateRareBookRequest createRequest;

    private UpdateRareBookRequest updateRequest;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setSlug("fiction");
        category.setName("Fiction");

        rareBook = RareBook.builder()
                .id(1L)
                .title("Book Title")
                .author("Author Name")
                .category(category)
                .publicationYear(Year.of(2000))
                .condition(RareBook.Condition.NEW)
                .rarity(RareBook.Rarity.RARE)
                .price(150.0)
                .build();

        createRequest = new CreateRareBookRequest(
                "Book Title",
                "Author Name",
                "fiction",
                2000,
                "1234567890",
                "NEW",
                "RARE",
                "Description",
                150.0,
                Collections.singletonList(new CreateEditionRequest(1, 2000, "First edition"))
        );

        updateRequest = new UpdateRareBookRequest(
                "Updated Title",
                "Updated Author",
                "fiction",
                2001,
                "0987654321",
                "USED",
                "COMMON",
                "Updated description",
                120.0,
                List.of(new UpdateEditionRequest(null, 1, 2001, "Notes"))
        );
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        GetRareBooksFilterOptions filterOptions = new GetRareBooksFilterOptions("history", "NEW", "COMMON");

        Page<RareBook> bookPage = new PageImpl<>(List.of(rareBook));

        when(rareBookRepository.findAll((Specification<RareBook>) any(), eq(pageable))).thenReturn(bookPage);
        when(rareBookMapper.mapToDTO(rareBook)).thenReturn(new RareBookDTO());

        Page<RareBookDTO> result = rareBookService.getBooks(pageable, filterOptions);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetBookById_Success() {
        Long bookId = 1L;

        RareBookDTO rareBookDTO = new RareBookDTO();
        rareBookDTO.setId(rareBook.getId());

        when(rareBookRepository.findById(bookId)).thenReturn(Optional.of(rareBook));
        when(rareBookMapper.mapToDTO(rareBook)).thenReturn(rareBookDTO);

        RareBookDTO result = rareBookService.getBookById(bookId);

        assertNotNull(result);
    }

    @Test
    void testGetBookById_NotFound() {
        Long bookId = 1L;
        when(rareBookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> rareBookService.getBookById(bookId));
    }

    @Test
    void testCreateBook() {
        when(categoryRepository.findBySlug(any())).thenReturn(Optional.of(category));
        when(rareBookRepository.save(any(RareBook.class))).thenReturn(rareBook);

        Long bookId = rareBookService.createBook(createRequest);

        assertNotNull(bookId);
    }

    @Test
    void testUpdateBook_Success() {
        when(rareBookRepository.findById(anyLong())).thenReturn(Optional.of(rareBook));
        when(editionRepository.save(any(Edition.class))).thenReturn(new Edition());

        rareBookService.updateBook(1L, updateRequest);

        verify(rareBookRepository).save(argThat(book ->
                book.getTitle().equals("Updated Title") &&
                        book.getAuthor().equals("Updated Author") &&
                        book.getPublicationYear().equals(Year.of(2001)) &&
                        book.getIsbn().equals("0987654321") &&
                        book.getCondition().equals(RareBook.Condition.USED) &&
                        book.getRarity().equals(RareBook.Rarity.COMMON) &&
                        book.getDescription().equals("Updated description") &&
                        book.getPrice().equals(120.0)
        ));
    }

    @Test
    void testUpdateBook_ExistentIsbn() {
        when(rareBookRepository.findById(anyLong())).thenReturn(Optional.of(rareBook));
        when(rareBookRepository.existsByIsbnAndIdNot(updateRequest.isbn(), 1L)).thenReturn(true);

        assertThrows(RecordConflictException.class, () -> rareBookService.updateBook(1L, updateRequest));
    }

    @Test
    void testDeleteBook_Success() {
        when(rareBookRepository.existsById(anyLong())).thenReturn(true);

        rareBookService.deleteBook(1L);

        verify(rareBookRepository).existsById(1L);
        verify(rareBookRepository).deleteById(1L);
    }

    @Test
    void testDeleteBook_BookNotFound() {
        when(rareBookRepository.existsById(anyLong())).thenReturn(false);

       assertThrows(RecordNotFoundException.class, () -> rareBookService.deleteBook(1L));
    }
}
