package com.dfortch.rarebook.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Year;

@Entity
@Table(name = "t_editions")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "rareBook")
@Builder
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer editionNumber;

    @ManyToOne
    @JoinColumn(name = "rare_book_id", nullable = false)
    private RareBook rareBook;

    @Column(nullable = false)
    private Year publicationYear;

    private String notes;
}
