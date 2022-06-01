package com.nhnacademy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "household_movement_address")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HouseholdMovementAddress {
    @EmbeddedId
    private Pk pk;

    @JsonIgnore
    @MapsId("serialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_serial_number")
    private Household household;

    @Column(name = "house_movement_address", length = 500)
    private String address;

    @Column(name = "last_address_yn", length = 1)
    private String lastAddress;

    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class Pk implements Serializable {

        @Column(name = "house_movement_report_date")
        private LocalDate reportDate;

        @Column(name = "household_serial_number")
        private Long serialNumber;
    }

    public void changeAddressInfo(String address, String lastAddress){
        this.address = address;
        this.lastAddress = lastAddress;
    }
}
