package com.example.slot.domain;

import com.example.auth.domain.port.out.TokenServicePort;
import com.example.auth.domain.port.out.UserRepository;
import com.example.slot.application.dto.SpinResultDTO;
import com.example.slot.domain.port.out.RandomSymbolPort;
import com.example.slot.domain.port.out.SlotMachinePort;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SlotMachineFacade implements SlotMachinePort {

    private final SlotMachine slotMachine;

    public SlotMachineFacade(Random random, TokenServicePort tokenServicePort, UserRepository userRepository) {
        RandomSymbolPort symbolGenerator = new RandomSymbolGenerator(random);
        this.slotMachine = new SlotMachine(symbolGenerator, tokenServicePort, userRepository);
    }

    @Override
    public SpinResultDTO spin(int bet, String accessToken) {
        SpinResult result = slotMachine.spin(bet, accessToken);
        return new SpinResultDTO(
                result.getSymbols(),
                result.isWin(),
                result.getPayout()
        );
    }

    @Override
    public List<SpinResultDTO> autospin(int bet, int spinCount, String accessToken) {
        List<SpinResult> results = slotMachine.autoSpin(bet, spinCount, accessToken);
        return results.stream()
                .map(result -> new SpinResultDTO(
                        result.getSymbols(),
                        result.isWin(),
                        result.getPayout()
                ))
                .collect(Collectors.toList());
    }
}
