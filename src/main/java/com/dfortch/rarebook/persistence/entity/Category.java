package com.dfortch.rarebook.persistence.entity;

import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "rareBooks")
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(unique = true, nullable = false, length = 100)
    private String slug;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RareBook> rareBooks;

    @Column(name = "is_initial")
    private boolean initial;
}
