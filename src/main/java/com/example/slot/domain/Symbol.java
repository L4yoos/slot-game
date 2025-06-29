package com.example.slot.domain;

enum Symbol {
    CHERRY(100),
    LEMON(80),
    ORANGE(60),
    PLUM(40),
    BELL(120),
    BAR(150),
    SEVEN(200);

    private final int payout;

    Symbol(int payout) {
        this.payout = payout;
    }

    public int getPayout() {
        return payout;
    }
}
