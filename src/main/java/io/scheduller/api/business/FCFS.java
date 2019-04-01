package io.scheduller.api.business;

import io.scheduller.api.dto.ProcessDTO;
import io.scheduller.api.dto.SchedullerDTO;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class FCFS implements Scheduller {

    private List<ProcessDTO> processList;

    private SchedullerDTO schedullerDTO;

    public FCFS(SchedullerDTO sch) {

        this.schedullerDTO = sch;

        this.processList = sch.getProcessList();

        processList.sort(Comparator.comparing(ProcessDTO::getArrival));

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
        this.schedullerDTO.setAverageTurnaround(turnaroundTime.get() / processList.size());
    }

    //executar primeiro
    @Override
    public void calculatingAverageWaitingTime() {
        AtomicReference<Double> waitingTime = new AtomicReference<>((double) 0);
        this.processList.forEach(p -> {
            p.setPId((long) Math.abs(new Random().nextInt(2999)));
            p.setWaitTime(waitingTime.get() - p.getArrival());
            p.setStartTime(waitingTime.get().intValue() - p.getArrival());
            waitingTime.updateAndGet(v -> v + p.getBurstTime() - p.getArrival());
        });
        this.schedullerDTO.setAverageWaitingTime(waitingTime.get() / processList.size());
    }

    @Override
    public SchedullerDTO resolveScheduller() {
        return schedullerDTO;
    }


}
