package com.example.slot.domain.port.out;

import com.example.slot.domain.Symbol;

import java.util.List;

public interface RandomSymbolPort {
    List<Symbol> generate();
}
