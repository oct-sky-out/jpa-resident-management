package com.nhnacademy.dto.household;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class RegisterHouseHoldDto {
    @NotNull
    private Long householdSerialNumber;

    @NotNull
    private Long residentSerialNumber;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate compositionDate;

    @NotBlank
    @Length(max = 20)
    private String compositionReasonCode;

    @NotBlank
    @Length(max = 500)
    private String currentHouseMovementAddress;
}
