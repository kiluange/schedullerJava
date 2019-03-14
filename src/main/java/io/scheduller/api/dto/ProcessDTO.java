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
    private Long pId;
    private String pName;
    private Integer deadLine;
    private Integer priority;
    private Integer arrival;
    private Integer startTime;
    private Integer burstTime;
    private Integer cpuTime;
    private ProcessType type;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double responseTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double waitTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double turnaroundTime;
}
