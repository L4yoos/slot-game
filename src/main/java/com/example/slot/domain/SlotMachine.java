package com.example.slot.domain;

import com.example.auth.domain.model.AuthToken;
import com.example.auth.domain.model.User;
import com.example.auth.domain.model.UserId;
import com.example.auth.domain.port.out.TokenServicePort;
import com.example.auth.domain.port.out.UserRepository;
import com.example.auth.domain.service.UserService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SlotMachine {

    private final RandomSymbolPort symbolGenerator;
    private final TokenServicePort tokenServicePort;
    private final UserRepository userRepository;

    public SpinResult spin(int betAmount, String accessToken) {
        if (betAmount <= 0) {
            throw new IllegalArgumentException("Bet must be greater than zero");
        }

        AuthToken authToken = new AuthToken(accessToken);

        UserId userId = tokenServicePort.extractUserIdFromAccessToken(authToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getBalance() == null || user.getBalance().compareTo(BigDecimal.valueOf(betAmount)) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        user.setBalance(user.getBalance().subtract(BigDecimal.valueOf(betAmount)));

        List<Symbol> symbols = symbolGenerator.generate();

        boolean win = symbols.stream().distinct().count() == 1;
        int payout = 0;

        if (win) {
            Symbol symbol = symbols.get(0);
            payout = symbol.getPayout() * betAmount;
            user.setBalance(user.getBalance().add(BigDecimal.valueOf(payout)));
        }

        userRepository.save(user);

        return new SpinResult(symbols, win, payout);
    }

    public List<SpinResult> autoSpin(int bet, int spinCount, String accessToken) {
        if (bet <= 0) {
            throw new IllegalArgumentException("Bet must be greater than zero");
        }
        if (spinCount <= 0) {
            throw new IllegalArgumentException("Spin count must be greater than zero");
        }
        //TODO make better valid in spin and Autospin for 3 layers (domain, app and adapters)
        List<SpinResult> results = new ArrayList<>();
        for (int i = 0; i < spinCount; i++) {
            SpinResult spinResult = spin(bet, accessToken);
            results.add(spinResult);
        }
        return results;
    }
}
