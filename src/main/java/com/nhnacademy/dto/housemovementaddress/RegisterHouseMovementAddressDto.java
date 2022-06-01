package com.nhnacademy.dto.housemovementaddress;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class RegisterHouseMovementAddressDto {
    @NotNull
    private LocalDate localDate;

    @NotBlank
    @Length(max = 500)
    private String address;

    @NotBlank
    @Length(max = 1)
    private String lastAddress;
}
