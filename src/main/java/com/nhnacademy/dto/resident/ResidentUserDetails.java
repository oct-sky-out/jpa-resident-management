package com.nhnacademy.dto.resident;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResidentUserDetails {
    private Long serialNumber;
    private String name;
    private String userId;
    private String password;
    private String email;
}
