package com.dfortch.rarebook.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateEditionRequest(@NotNull @Positive Integer editionNumber,
                                   @NotNull @Positive Integer publicationYear,
                                   String notes) {
}
