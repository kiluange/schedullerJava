package io.scheduller.api.business;

import io.scheduller.api.dto.ProcessDTO;
import io.scheduller.api.dto.SchedullerDTO;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class RoundRobin implements PreemptiveScheduller {

    private List<ProcessDTO> processList;

    private SchedullerDTO schedullerDTO;

    public RoundRobin(SchedullerDTO sch) {
        this.schedullerDTO = sch;
        this.processList = sch.getProcessList();
        processList.sort(Comparator.comparing(ProcessDTO::getArrival));

        calculatingOverHead();
        calculatingAverageTurnaroundTime();

    }

    @Override
    public void calculatingAverageTurnaroundTime() {
        this.processList.stream().forEach(processDTO -> {
            if (processDTO.getCpuTime() < processDTO.getBurstTime() &&
                    (processDTO.getBurstTime() - processDTO.getCpuTime() < schedullerDTO.getQuantum() &&
                            processDTO.getBurstTime() - processDTO.getCpuTime() > 0)) {
                processDTO.setCpuTime(processDTO.getCpuTime() + (processDTO.getBurstTime() - processDTO.getCpuTime()));

            } else if (processDTO.getCpuTime() < processDTO.getBurstTime() && processDTO.getOverheads() > 0) {
                processDTO.setCpuTime(processDTO.getCpuTime() + schedullerDTO.getQuantum() + schedullerDTO.getOverHead());
                processDTO.setOverheads(processDTO.getOverheads() - 1);
            }
        });

    }

    @Override
    public void calculatingAverageWaitingTime() {

    }

    @Override
    public SchedullerDTO resolveScheduller() {
        return schedullerDTO;
    }

    @Override
    public void calculatingOverHead() {
        this.processList.stream().forEach(processDTO -> {
            processDTO.setOverheads((processDTO.getBurstTime() % this.schedullerDTO.getQuantum()) > 0 ?
                    (processDTO.getBurstTime() / this.schedullerDTO.getQuantum()) :
                    processDTO.getBurstTime() <= this.schedullerDTO.getQuantum() ? 0 : (int) Math.floor(processDTO.getBurstTime() / this.schedullerDTO.getQuantum()));
        });

    }
}
