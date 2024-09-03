package com.dfortch.rarebook.dto.request;

import com.dfortch.rarebook.validation.NonExistentCategorySlug;
import com.dfortch.rarebook.validation.ValidSlug;
import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(@NotBlank String name,
                                    String description,
                                    @ValidSlug @NonExistentCategorySlug String slug) {
}
