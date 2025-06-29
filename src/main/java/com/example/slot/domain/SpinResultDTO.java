package com.example.slot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SpinResultDTO {
    private final List<Symbol> symbols;
    private final boolean win;
    private final int payout;
}
