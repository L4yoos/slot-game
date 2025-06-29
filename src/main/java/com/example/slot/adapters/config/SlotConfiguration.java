package com.example.slot.adapters.config;

import com.example.slot.application.SlotService;
import com.example.slot.domain.SlotMachineFacade;
import com.example.slot.domain.SlotMachinePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class SlotConfiguration {

    @Bean
    public SlotMachinePort slotMachinePort() {
        return new SlotMachineFacade(new Random());
    }

    @Bean
    public SlotService slotService(SlotMachinePort slotMachinePort) {
        return new SlotService(slotMachinePort);
    }
}