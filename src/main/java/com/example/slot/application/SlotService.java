package com.example.slot.application;

import com.example.slot.domain.SlotMachinePort;
import com.example.slot.domain.SpinResultDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SlotService {

    private final SlotMachinePort slotMachine;

    public SpinResultDTO play(int bet) {
        if (bet <= 0) {
            throw new IllegalArgumentException("Bet must be greater than zero");
        }
        return slotMachine.spin(bet);
    }

    public List<SpinResultDTO> autoSpin(int bet, int spinCount) {
        if (bet <= 0) {
            throw new IllegalArgumentException("Bet must be greater than zero");
        }
        if (spinCount <= 0) {
            throw new IllegalArgumentException("Spin count must be greater than zero");
        }
        return slotMachine.autospin(bet, spinCount);
    }
}
