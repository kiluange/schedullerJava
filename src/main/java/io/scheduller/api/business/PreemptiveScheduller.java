package io.scheduller.api.business;

import io.scheduller.api.dto.ProcessDTO;

import java.util.List;

public interface PreemptiveScheduller extends Scheduller {

    void calculatingOverHead();

    void calculatingPreemptiveTurnaroundTime(List<ProcessDTO> process);
}
