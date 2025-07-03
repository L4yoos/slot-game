package com.example.slot.domain.port.out;

import com.example.slot.application.dto.SpinResultDTO;

import java.util.List;

public interface SlotMachinePort {
    SpinResultDTO spin(int bet, String accessToken);
    List<SpinResultDTO> autospin(int bet, int spin, String accessToken);
}
