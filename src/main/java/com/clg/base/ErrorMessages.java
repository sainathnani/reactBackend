package com.clg.base;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
@Data
public class ErrorMessages {

    private int statusCode;
    private String errMessage;


}
