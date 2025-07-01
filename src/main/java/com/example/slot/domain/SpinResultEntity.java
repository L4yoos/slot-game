package com.example.slot.domain;

import com.example.auth.adapters.out.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "spin_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpinResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "spin_symbols", joinColumns = @JoinColumn(name = "spin_result_id"))
    @Column(name = "symbol")
    private List<String> symbols;

    private boolean win;
    private int payout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}