package com.kts.taxify.services.simulations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindActiveProcess {
    private final SimulationService simulationService;

    public Process execute(UUID id) {
        return simulationService.getActiveProcesses().get(id).getProcess();
    }
}
