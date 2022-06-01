package com.nhnacademy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "birth_death_report_resident")
public class BirthDeathReportResident {
    @EmbeddedId
    private Pk pk;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_death_report_date")
    private LocalDate reportDate;

    @Column(name = "birth_report_qualifications_code", length = 20)
    private String birthReportQualificationsCode;

    @Column(name = "death_report_qualifications_code", length = 20)
    private String deathReportQualificationsCode;

    @Column(name = "email_address", length = 50)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @JsonIgnore
    @MapsId("serialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_serial_number")
    private Resident resident;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    public static class Pk implements Serializable {
        @Column(name = "birth_death_type_code", nullable = false, length = 20)
        private String birthDeathTypeCode;

        @Column(name = "report_resident_serial_number", nullable = false)
        private Long reportSerialNumber;

        @Column(name = "resident_serial_number", nullable = false)
        private Long serialNumber;
    }

    public void modifyBirthReport(String birthReportQualificationsCode, LocalDate reportDate, String email, String phoneNumber){
        this.birthReportQualificationsCode = birthReportQualificationsCode;
        this.reportDate = reportDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void modifyDeathReport(String deathReportQualificationsCode, LocalDate reportDate, String email, String phoneNumber){
        this.deathReportQualificationsCode = deathReportQualificationsCode;
        this.reportDate = reportDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
