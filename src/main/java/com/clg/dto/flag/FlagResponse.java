package com.clg.dto.flag;


import lombok.Data;

@Data
public class FlagResponse {

    private Long flagId;
    private String flaggedByUser;
    private Long commentId;
    private String flaggedUser;
    private String status;
}
