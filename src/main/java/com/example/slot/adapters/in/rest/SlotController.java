package com.example.slot.adapters.in.rest;

import com.example.auth.domain.port.out.CookieServicePort;
import com.example.slot.application.SlotService;
import com.example.slot.application.dto.AutoSpinRequest;
import com.example.slot.application.dto.SpinRequest;
import com.example.slot.application.dto.SpinResultDTO;
import jakarta.servlet.http.HttpServletRequest;
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
    private final CookieServicePort cookieServicePort;

    @PostMapping("/spin")
    public SpinResultDTO spin(@RequestBody SpinRequest request, HttpServletRequest httpRequest) {
        if (request.getBet() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Bet must be greater than zero"
            );
        }

        String accessToken = cookieServicePort.getAccessToken(httpRequest);
        return slotService.play(request.getBet(), accessToken);
    }

    @PostMapping("/autospin")
    public List<SpinResultDTO> autoSpin(@RequestBody AutoSpinRequest request, HttpServletRequest httpRequest) {
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
        String accessToken = cookieServicePort.getAccessToken(httpRequest);
        return slotService.autoSpin(request.getBet(), request.getSpin(), accessToken);
    }
}