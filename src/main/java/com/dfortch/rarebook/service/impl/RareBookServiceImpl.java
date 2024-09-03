package com.dfortch.rarebook.service.impl;

import com.dfortch.rarebook.common.exception.RecordConflictException;
import com.dfortch.rarebook.common.exception.RecordNotFoundException;
import com.dfortch.rarebook.dto.entity.RareBookDTO;
import com.dfortch.rarebook.dto.request.CreateRareBookRequest;
import com.dfortch.rarebook.dto.request.GetRareBooksFilterOptions;
import com.dfortch.rarebook.dto.request.UpdateEditionRequest;
import com.dfortch.rarebook.dto.request.UpdateRareBookRequest;
import com.dfortch.rarebook.mapper.RareBookMapper;
import com.dfortch.rarebook.persistence.entity.Category;
import com.dfortch.rarebook.persistence.entity.Edition;
import com.dfortch.rarebook.persistence.entity.RareBook;
import com.dfortch.rarebook.persistence.repository.CategoryRepository;
import com.dfortch.rarebook.persistence.repository.EditionRepository;
import com.dfortch.rarebook.persistence.repository.RareBookRepository;
import com.dfortch.rarebook.service.RareBookService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class RareBookServiceImpl implements RareBookService {

    private final RareBookMapper rareBookMapper;

    private final CategoryRepository categoryRepository;

    private final EditionRepository editionRepository;

    private final RareBookRepository rareBookRepository;

    @Override
    public Page<RareBookDTO> getBooks(Pageable pageable, GetRareBooksFilterOptions filterOptions) {
        Page<RareBook> bookPage;
        if (filterOptions != null) {
            bookPage = rareBookRepository.findAll((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.hasText(filterOptions.categorySlug())) {
                    predicates.add(criteriaBuilder.equal(root.get("category").get("slug"), filterOptions.categorySlug()));
                }

                if (filterOptions.condition() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("condition"), filterOptions.condition()));
                }

                if (filterOptions.rarity() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("rarity"), filterOptions.rarity()));
                }

                if (predicates.isEmpty()) {
                    return criteriaBuilder.conjunction();
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }, pageable);
        } else {
            bookPage = rareBookRepository.findAll(pageable);
        }
        return bookPage.map(rareBookMapper::mapToDTO);
    }

    @Override
    public RareBookDTO getBookById(Long id) {
        RareBook book = rareBookRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(RareBook.class));

        return rareBookMapper.mapToDTO(book);
    }

    @Override
    @Transactional
    public Long createBook(CreateRareBookRequest requestDto) {
        Category category = categoryRepository.findBySlug(requestDto.categorySlug())
                .orElseThrow(() -> {
                    Map<String, Object> fields = new TreeMap<>();
                    fields.put("slug", requestDto.categorySlug());
                    return new RecordNotFoundException(Category.class, fields);
                });

        RareBook.RareBookBuilder bookBuilder = RareBook.builder()
                .title(requestDto.title())
                .author(requestDto.author())
                .category(category)
                .publicationYear(Year.of(requestDto.publicationYear()))
                .condition(RareBook.Condition.valueOf(requestDto.condition()))
                .rarity(RareBook.Rarity.valueOf(requestDto.rarity()))
                .price(requestDto.price());

        if (requestDto.isbn() != null && !requestDto.isbn().trim().isEmpty()) {
            bookBuilder.isbn(requestDto.isbn());
        }

        if (requestDto.description() != null && !requestDto.description().trim().isEmpty()) {
            bookBuilder.description(requestDto.description());
        }

        RareBook book = bookBuilder.build();

        RareBook savedBook = rareBookRepository.save(book);

        List<Edition> editions = requestDto.editions().stream().map(editionRequest -> {
            Edition.EditionBuilder editionBuilder = Edition.builder()
                    .editionNumber(editionRequest.editionNumber())
                    .publicationYear(Year.of(editionRequest.publicationYear()))
                    .rareBook(savedBook);

            if (editionRequest.notes() != null && !editionRequest.notes().trim().isEmpty()) {
                editionBuilder.notes(editionRequest.notes().trim());
            }

            return editionBuilder.build();
        }).toList();

        editionRepository.saveAll(editions);

        return savedBook.getId();
    }

    @Override
    @Transactional
    public void updateBook(Long id, UpdateRareBookRequest requestDto) {
        RareBook existingBook = rareBookRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(RareBook.class));

        existingBook.setTitle(requestDto.title());
        existingBook.setAuthor(requestDto.author());
        existingBook.setPublicationYear(Year.of(requestDto.publicationYear()));
        existingBook.setCondition(RareBook.Condition.valueOf(requestDto.condition()));
        existingBook.setRarity(RareBook.Rarity.valueOf(requestDto.rarity()));
        existingBook.setPrice(requestDto.price());

        if (rareBookRepository.existsByIsbnAndIdNot(requestDto.isbn(), id)) {
            Map<String, String> fieldErrors = new TreeMap<>();
            fieldErrors.put("isbn", "field already used by other book");
            throw new RecordConflictException(RareBook.class, fieldErrors);
        }

        existingBook.setIsbn(requestDto.isbn());

        if (requestDto.isbn() != null && !requestDto.isbn().trim().isEmpty()) {
            existingBook.setIsbn(requestDto.isbn());
        }

        if (requestDto.description() != null && !requestDto.description().trim().isEmpty()) {
            existingBook.setDescription(requestDto.description());
        }

        rareBookRepository.save(existingBook);

        if (requestDto.editions() != null) {
            for (UpdateEditionRequest editionRequest : requestDto.editions()) {
                Edition edition = getEditionFromUpdateRequest(existingBook, editionRequest);
                editionRepository.save(edition);
            }

            List<Long> providedEditionIds = requestDto.editions().stream()
                    .map(UpdateEditionRequest::id)
                    .toList();
            editionRepository.deleteByRareBookAndIdNotIn(existingBook, providedEditionIds);
        }
    }

    @Override
    public void deleteBook(Long id) {
        boolean bookExists = rareBookRepository.existsById(id);

        if (!bookExists) {
            throw new RecordNotFoundException(RareBook.class);
        }

        rareBookRepository.deleteById(id);
    }

    private Edition getEditionFromUpdateRequest(RareBook existingBook, UpdateEditionRequest editionRequest) {
        Edition edition;
        if (editionRequest.id() != null) {
            edition = editionRepository.findByRareBookAndId(existingBook, editionRequest.id())
                    .orElseThrow(() -> {
                        Map<String, Object> fields = new TreeMap<>();
                        fields.put("rareBookId", existingBook.getId());
                        fields.put("id", editionRequest.id());
                        return new RecordNotFoundException(Edition.class, fields);
                    });
            edition.setEditionNumber(editionRequest.editionNumber());
            edition.setPublicationYear(Year.of(editionRequest.publicationYear()));

            if (editionRequest.notes() == null) {
                edition.setNotes(null);
            } else if (!editionRequest.notes().trim().isEmpty()) {
                edition.setNotes(editionRequest.notes().trim());
            }
        } else {
            Edition.EditionBuilder editionBuilder = Edition.builder()
                    .editionNumber(editionRequest.editionNumber())
                    .publicationYear(Year.of(editionRequest.publicationYear()))
                    .rareBook(existingBook);

            if (editionRequest.notes() != null && !editionRequest.notes().trim().isEmpty()) {
                editionBuilder.notes(editionRequest.notes().trim());
            }

            edition = editionBuilder.build();
        }
        return edition;
    }
}
