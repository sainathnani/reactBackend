package com.clg.dto.flag;


import lombok.Data;

@Data
public class FlagRequest {

    private Long flagId;
    private Long commentId;
    private String flaggedUser; // commented user
}
