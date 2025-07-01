package com.example.slot.domain;

import java.util.List;

public interface SlotMachinePort {
    SpinResultDTO spin(int bet, String accessToken);
    List<SpinResultDTO> autospin(int bet, int spin, String accessToken);
}
