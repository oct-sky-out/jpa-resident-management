package com.nhnacademy.dto.housemovementaddress;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class ModifyHouseholdMovementAddressDto {

    @NotBlank
    @Length(max = 500)
    private String address;

    @NotBlank
    @Length(max = 1)
    private String lastAddress;

}
