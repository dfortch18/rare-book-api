package com.dfortch.rarebook.persistence.entity;

import java.time.Year;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "t_rare_books")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RareBook {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String title;

    private String author;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Year publicationYear;

    @OneToMany(mappedBy = "rareBook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Edition> editions;

    @Column(unique = true)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_condition")
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;

    @Column(name = "is_initial")
    private boolean initial;

    public enum Condition {
        NEW,
        USED,
        BROKEN
    }

    public enum Rarity {
        COMMON,
        RARE,
        VERY_RARE
    }
}
