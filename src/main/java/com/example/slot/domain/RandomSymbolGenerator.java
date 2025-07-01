package com.example.slot.domain;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class RandomSymbolGenerator implements RandomSymbolPort {

    private final Random random;

    @Override
    public List<Symbol> generate() {
        List<Symbol> result = new ArrayList<>();
        Symbol[] values = Symbol.values();

        for (int i = 0; i < 3; i++) {
            result.add(values[random.nextInt(values.length)]);
        }

        return result;
    }
}

