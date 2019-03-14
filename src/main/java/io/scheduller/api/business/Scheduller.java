package io.scheduller.api.business;

import io.scheduller.api.dto.SchedullerDTO;

public interface Scheduller {


    void calculatingAverageTurnaroundTime();

    void calculatingAverageWaitingTime();

    SchedullerDTO resolveScheduller();
}
