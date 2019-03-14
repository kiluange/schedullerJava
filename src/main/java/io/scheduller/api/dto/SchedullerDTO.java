package io.scheduller.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import io.scheduller.api.enumerator.SchedullerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(NON_EMPTY)
public class SchedullerDTO {
    private SchedullerType type;
    private List<ProcessDTO> processList;
    private Integer quantum;
    private Integer overHead;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double waitingTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double turnaround;
}
