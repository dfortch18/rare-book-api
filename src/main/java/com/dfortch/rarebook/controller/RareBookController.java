package com.dfortch.rarebook.controller;

import com.dfortch.rarebook.dto.entity.RareBookDTO;
import com.dfortch.rarebook.dto.request.CreateRareBookRequest;
import com.dfortch.rarebook.dto.request.GetRareBooksFilterOptions;
import com.dfortch.rarebook.dto.request.UpdateRareBookRequest;
import com.dfortch.rarebook.dto.response.MessageResponse;
import com.dfortch.rarebook.dto.response.RecordCreatedResponse;
import com.dfortch.rarebook.service.RareBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.dfortch.rarebook.RareBookApiApplicationConstants.API_VERSION;

@RestController
@RequestMapping("/api/" + API_VERSION + "/books")
@RequiredArgsConstructor
public class RareBookController {

    private final RareBookService rareBookService;

    @GetMapping
    public Page<RareBookDTO> getBooks(@Valid @ModelAttribute GetRareBooksFilterOptions filterOptions, Pageable pageable) {
        return rareBookService.getBooks(pageable, filterOptions);
    }

    @GetMapping("/{id}")
    public RareBookDTO getBookById(@PathVariable Long id) {
        return rareBookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecordCreatedResponse createBook(@Valid @RequestBody CreateRareBookRequest requestDto) {
        Long bookId = rareBookService.createBook(requestDto);
        return new RecordCreatedResponse("Book created successfully", bookId);
    }

    @PutMapping("/{id}")
    public MessageResponse updateBook(@PathVariable Long id, @Valid @RequestBody UpdateRareBookRequest requestDto) {
        rareBookService.updateBook(id, requestDto);
        return new MessageResponse("Book updated successfully");
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteBook(@PathVariable Long id) {
        rareBookService.deleteBook(id);
        return new MessageResponse("Book deleted successfully");
    }
}
