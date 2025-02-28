package com.carlos.microservice2.vo.report;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {

    private Date date;
    private String client;
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private Boolean status;
    private Double amount;
    private Double balance;
    
}
