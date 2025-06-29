package com.example.slot.adapters.web;

import com.example.slot.application.SlotService;
import com.example.slot.domain.SpinResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/slot")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

    @PostMapping("/spin")
    public SpinResultDTO spin(@RequestBody SpinRequest request) {
        if (request.getBet() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Bet must be greater than zero"
            );
        }
        return slotService.play(request.getBet());
    }

    @PostMapping("/autospin")
    public List<SpinResultDTO> autoSpin(@RequestBody AutoSpinRequest request) {
        if (request.getBet() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Bet must be greater than zero"
            );
        }
        if (request.getSpin() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Spin must be greater than zero"
            );
        }
        return slotService.autoSpin(request.getBet(), request.getSpin());
    }
}