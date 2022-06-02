package com.nhnacademy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "household")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Household {
    @Id
    @Column(name = "household_serial_number", nullable = false)
    private Long serialNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_resident_serial_number")
    private Resident resident;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "household_composition_date")
    private LocalDate compositionDate;

    @Column(name = "household_composition_reason_code", length = 20)
    private String compositionReasonCode;

    @Column(name = "current_house_movement_address", length = 500)
    private String currentHouseMovementAddress;

    @JsonIgnore
    @OneToMany( mappedBy = "household",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private List<HouseholdMovementAddress> movementAddresses;

    @JsonIgnore
    @OneToMany( mappedBy = "household",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private List<HouseholdCompositionResident> compositionResidents;

    public void changeHouseholdInfo(LocalDate compositionDate, String compositionReasonCode,
                                String currentHouseMovementAddress) {
        this.compositionDate = compositionDate;
        this.compositionReasonCode = compositionReasonCode;
        this.currentHouseMovementAddress = currentHouseMovementAddress;
    }
}
