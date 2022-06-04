package com.nhnacademy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "resident")
public class Resident {
    @Id
    @Column(name = "resident_serial_number", nullable = false)
    private Long serialNumber;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "resident_registration_number", length = 300)
    private String registrationNumber;

    @Column(name = "gender_code", length = 20)
    private String genderCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "birth_place_code", length = 20)
    private String birthPlaceCode;

    @Column(name = "registration_base_address", length = 500)
    private String baseAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "death_date")
    private LocalDateTime deathDate;

    @Column(name = "death_place_code", length = 20)
    private String deathPlaceCode;

    @Column(name = "death_place_address", length = 500)
    private String deathPlaceAddress;

    @Column(name = "user_id", length = 50, nullable = false)
    private String userId;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "resident",
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<FamilyRelationship> familyRelationship;

    @JsonIgnore
    @OneToMany(mappedBy = "resident",
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<BirthDeathReportResident> birthDeathReportResident;

    @JsonIgnore
    @OneToMany(mappedBy = "resident",
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<CertificateIssue> certificateIssues;

    @JsonIgnore
    @OneToMany(mappedBy = "resident",
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<Household> households;

    @JsonIgnore
    @OneToMany(mappedBy = "resident",
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<HouseholdCompositionResident> householdCompositionResidents;

    public void changeResidentInfo(String name, String baseAddress, LocalDateTime birthDate,
                                   String birthPlaceCode, String registrationNumber, String genderCode){
        this.name = name;
        this.baseAddress = baseAddress;
        this.birthDate = birthDate;
        this.birthPlaceCode = birthPlaceCode;
        this.registrationNumber = registrationNumber;
        this.genderCode = genderCode;
    }
}
