package io.scheduller.api.business;

import io.scheduller.api.dto.ProcessDTO;
import io.scheduller.api.dto.SchedullerDTO;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class SJF implements Scheduller {

    private List<ProcessDTO> processList;

    private SchedullerDTO schedullerDTO;

    public SJF(SchedullerDTO sch) {

        this.schedullerDTO = sch;

        this.processList = sch.getProcessList();

        processList.sort(Comparator.comparing(ProcessDTO::getArrival).thenComparing(ProcessDTO::getBurstTime));

        this.calculatingAverageWaitingTime();

        this.calculatingAverageTurnaroundTime();

    }

    @Override
    public void calculatingAverageTurnaroundTime() {
        AtomicReference<Double> turnaroundTime = new AtomicReference<>((double) 0);
        this.processList.forEach(p -> {
            turnaroundTime.updateAndGet(v -> v + p.getBurstTime() + p.getWaitTime());
            p.setTurnaroundTime(p.getBurstTime() + p.getWaitTime());
        });
        this.schedullerDTO.setTurnaround(turnaroundTime.get() / processList.size());
    }

    //executar primeiro
    @Override
    public void calculatingAverageWaitingTime() {
        AtomicReference<Double> waitingTime = new AtomicReference<>((double) 0);
        this.processList.forEach(p -> {
            p.setWaitTime(waitingTime.get() - p.getArrival());
            waitingTime.updateAndGet(v -> v + p.getBurstTime() - p.getArrival());
        });
        this.schedullerDTO.setWaitingTime(waitingTime.get() / processList.size());
    }

    @Override
    public SchedullerDTO resolveScheduller() {

        return schedullerDTO;

    }


}
