package com.dfortch.rarebook.dto.request;

import com.dfortch.rarebook.validation.ExistentCategorySlug;
import com.dfortch.rarebook.validation.ValidBookCondition;
import com.dfortch.rarebook.validation.ValidBookRarity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record UpdateRareBookRequest(@NotBlank String title,
                                    @NotBlank String author,
                                    @NotBlank @ExistentCategorySlug String categorySlug,
                                    @NotNull @Positive Integer publicationYear,
                                    @NotBlank String isbn,
                                    @ValidBookCondition @NotBlank String condition,
                                    @NotBlank @ValidBookRarity String rarity,
                                    String description,
                                    @NotNull @Positive Double price,
                                    @NotNull List<UpdateEditionRequest> editions) {
}
