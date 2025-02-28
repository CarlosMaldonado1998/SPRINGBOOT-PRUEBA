package com.carlos.microservice2.vo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterTransactionDto {
    
    private Date start;
    private Date end;
    private String identification;
}
