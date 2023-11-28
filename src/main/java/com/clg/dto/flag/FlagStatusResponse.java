package com.clg.dto.flag;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FlagStatusResponse {

    private String emailId;
    private String status;
}
