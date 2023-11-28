package com.clg.dto.flag;


import lombok.Data;

import java.util.Set;

@Data
public class FlaggedUserResponse {

    private Set<String> users;
}
