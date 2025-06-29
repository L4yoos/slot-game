package com.example.slot.domain;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SlotMachineFacade implements SlotMachinePort {

    private final SlotMachine slotMachine;

    public SlotMachineFacade(Random random) {
        RandomSymbolPort symbolGenerator = new RandomSymbolGenerator(random);
        this.slotMachine = new SlotMachine(symbolGenerator);
    }

    @Override
    public SpinResultDTO spin(int bet) {
        SpinResult result = slotMachine.spin(bet);
        return new SpinResultDTO(
                result.getSymbols(),
                result.isWin(),
                result.getPayout()
        );
    }

    @Override
    public List<SpinResultDTO> autospin(int bet, int spinCount) {
        List<SpinResult> results = slotMachine.autoSpin(bet, spinCount);
        return results.stream()
                .map(result -> new SpinResultDTO(
                        result.getSymbols(),
                        result.isWin(),
                        result.getPayout()
                ))
                .collect(Collectors.toList());
    }
}
