package com.nhnacademy.dto.resident;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class ModifyResidentDto {
    @NotBlank
    private String name;

    @NotBlank
    private String baseAddress;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthDate;

    @NotBlank
    private String birthPlaceCode;

    @NotBlank
    private String registrationNumber;

    @NotBlank
    @Length(max = 1)
    private String genderCode;
}
