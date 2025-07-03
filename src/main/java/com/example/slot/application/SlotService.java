package com.example.slot.application;

import com.example.slot.domain.port.out.SlotMachinePort;
import com.example.slot.application.dto.SpinResultDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SlotService {

    private final SlotMachinePort slotMachine;

    public SpinResultDTO play(int bet, String accessToken) {
        return slotMachine.spin(bet, accessToken);
    }

    public List<SpinResultDTO> autoSpin(int bet, int spinCount, String accessToken) {
        return slotMachine.autospin(bet, spinCount, accessToken);
    }
}
