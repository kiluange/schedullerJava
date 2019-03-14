package io.scheduller.api.service;

import io.scheduller.api.business.FCFS;
import io.scheduller.api.business.SJF;
import io.scheduller.api.dto.SchedullerDTO;
import org.springframework.stereotype.Service;

import static io.scheduller.api.enumerator.SchedullerType.*;

@Service
public class SchedullerService {

    public SchedullerDTO runScheduler(SchedullerDTO dto){

        if (dto.getType().equals(EDF)){

        }else if (dto.getType().equals(FCFS)){
            var fcfs = new FCFS(dto).resolveScheduller();
            return fcfs;
        }else if (dto.getType().equals(PRIORITY)){

        }else if (dto.getType().equals(RR)){

        }else if (dto.getType().equals(SJF)){
            var sjf = new SJF(dto).resolveScheduller();
            return sjf;
        }


        return dto;
    }
}
