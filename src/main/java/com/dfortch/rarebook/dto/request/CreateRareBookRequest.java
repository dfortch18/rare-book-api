package com.dfortch.rarebook.dto.request;

import com.dfortch.rarebook.validation.NonExistentBookIsbn;
import com.dfortch.rarebook.validation.ValidBookCondition;
import com.dfortch.rarebook.validation.ValidBookRarity;
import com.dfortch.rarebook.validation.ExistentCategorySlug;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateRareBookRequest(@NotBlank String title,
                                    @NotBlank String author,
                                    @NotBlank @ExistentCategorySlug String categorySlug,
                                    @NotNull @Positive Integer publicationYear,
                                    @NotBlank @NonExistentBookIsbn String isbn,
                                    @ValidBookCondition @NotBlank String condition,
                                    @ValidBookRarity @NotBlank String rarity,
                                    String description,
                                    @NotNull @Positive Double price,
                                    @NotNull List<CreateEditionRequest> editions) {
}
