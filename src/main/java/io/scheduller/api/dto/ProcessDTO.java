package io.scheduller.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.scheduller.api.enumerator.ProcessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(NON_EMPTY)
public class ProcessDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long pId = 0L;
    private String pName;
    private Integer deadLine = 0;
    private Integer priority = 0;
    private Integer arrival = 0;
    private Integer startTime = 0;
    private Integer burstTime = 0;
    private Integer cpuTime = 0;
    private Integer overheads = 0;
    private ProcessType type;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double responseTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double waitTime = null;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double turnaroundTime = 0D;
}
