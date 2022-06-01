package com.nhnacademy.dto.birthreport;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class ModifyBirthReportDto {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    @NotBlank
    @Length(max = 20)
    private String birthReportQualificationsCode;

    @Length(max = 50)
    private String email = null;

    @Length(max = 20)
    private String phoneNumber;
}
