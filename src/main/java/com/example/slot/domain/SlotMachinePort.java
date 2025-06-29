package com.example.slot.domain;

import java.util.List;

public interface SlotMachinePort {
    SpinResultDTO spin(int bet);
    List<SpinResultDTO> autospin(int bet, int spin);
}
