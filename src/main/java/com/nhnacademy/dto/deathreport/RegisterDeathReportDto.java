package com.nhnacademy.dto.deathreport;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class RegisterDeathReportDto {
    @NotBlank
    @Length(max = 20)
    private String birthDeathTypeCode;

    @NotNull
    private Long reportSerialNumber;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    @NotBlank
    @Length(max = 20)
    private String deathReportQualificationsCode;

    @Length(max = 50)
    private String email = null;

    @Length(max = 20)
    private String phoneNumber;
}