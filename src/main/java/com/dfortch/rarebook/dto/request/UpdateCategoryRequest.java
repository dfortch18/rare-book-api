package com.dfortch.rarebook.dto.request;

import com.dfortch.rarebook.validation.ValidSlug;
import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequest(@NotBlank String name,
                                    String description,
                                    @ValidSlug String slug) {
}
