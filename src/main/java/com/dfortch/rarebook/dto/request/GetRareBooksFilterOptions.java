package com.dfortch.rarebook.dto.request;

import com.dfortch.rarebook.validation.ExistentCategorySlug;
import com.dfortch.rarebook.validation.ValidBookCondition;
import com.dfortch.rarebook.validation.ValidBookRarity;

public record GetRareBooksFilterOptions(@ExistentCategorySlug(allowNull = true) String categorySlug,
                                        @ValidBookRarity(allowNull = true) String rarity,
                                        @ValidBookCondition(allowNull = true) String condition) {
}
