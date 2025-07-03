package com.example.slot.domain;

public enum Symbol {
    APPLE(20),
    BLUEBERRY(40),
    ORANGE(60),
    RASPBERRY(80),
    GRAPE(100),
    CHERRY(120),
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
