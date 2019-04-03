package io.scheduller.api.business;

import io.scheduller.api.dto.ProcessDTO;
import io.scheduller.api.dto.SchedullerDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class EDF implements PreemptiveScheduller {

    private List<ProcessDTO> processList;

    private List<ProcessDTO> responseList = new ArrayList<>();

    private SchedullerDTO schedullerDTO;

    Double waitingTime = 0D;

    public EDF(SchedullerDTO sch) {
        this.schedullerDTO = sch;
        this.processList = sch.getProcessList();
        processList.sort(Comparator.comparing(ProcessDTO::getArrival).thenComparing(ProcessDTO::getDeadLine));
        this.processList.forEach(p -> p.setPId((long) Math.abs(new Random().nextInt(2999))));

        calculatingOverHead();
        calculatingPreemptiveTurnaroundTime(processList);
        calculatingAverageTurnaroundTime();
        calculatingAverageWaitingTime();

    }


    @Override
    public void calculatingAverageTurnaroundTime() {
        AtomicReference<Double> turnRoundTime = new AtomicReference<>(0D);
        this.processList.forEach(process -> turnRoundTime.updateAndGet(v -> v + process.getTurnaroundTime()));
        schedullerDTO.setAverageTurnaround(turnRoundTime.get() / processList.size());
    }

    @Override
    public void calculatingAverageWaitingTime() {
        AtomicReference<Double> waitingTime = new AtomicReference<>(0D);
        this.processList.forEach(process -> waitingTime.updateAndGet(v -> v + process.getWaitTime()));
        schedullerDTO.setAverageWaitingTime(waitingTime.get() / processList.size());
    }

    @Override
    public void calculatingPreemptiveTurnaroundTime(List<ProcessDTO> process) {
        ProcessDTO proc = process.remove(0);

        if (proc.getWaitTime() == null) proc.setWaitTime(this.waitingTime - proc.getArrival());

        if (proc.getBurstTime() > schedullerDTO.getQuantum()){
            proc.setBurstTime(proc.getBurstTime() - schedullerDTO.getQuantum());
            proc.setCpuTime(proc.getCpuTime() + schedullerDTO.getQuantum());
            proc.setTurnaroundTime(proc.getTurnaroundTime() + schedullerDTO.getQuantum() + schedullerDTO.getOverHead());
            process.add(proc);
            this.waitingTime += schedullerDTO.getQuantum() + schedullerDTO.getOverHead();
        }
        else {
            proc.setTurnaroundTime(proc.getTurnaroundTime() + proc.getBurstTime());
            proc.setCpuTime(proc.getCpuTime() + proc.getBurstTime());
            proc.setBurstTime(0);
            this.responseList.add(proc);
            this.waitingTime += schedullerDTO.getQuantum();
        }

        if (!process.isEmpty()){
            calculatingPreemptiveTurnaroundTime(process);
        }
        this.processList = this.responseList;
        schedullerDTO.setProcessList(processList);
    }

    @Override
    public SchedullerDTO resolveScheduller() {
        return schedullerDTO;
    }

    @Override
    public void calculatingOverHead() {
        this.processList.forEach(processDTO -> {
            processDTO.setOverheads((processDTO.getBurstTime() % this.schedullerDTO.getQuantum()) > 0 ?
                    (processDTO.getBurstTime() / this.schedullerDTO.getQuantum()) :
                    processDTO.getBurstTime() <= this.schedullerDTO.getQuantum() ? 0 : (int) Math.floor(processDTO.getBurstTime() / this.schedullerDTO.getQuantum()));
        });
    }
}
