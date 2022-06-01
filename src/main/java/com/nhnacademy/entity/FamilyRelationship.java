package com.nhnacademy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
@Table(name = "family_relationship")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FamilyRelationship {
    @EmbeddedId
    private Pk pk;

    @Column(name ="family_relationship_code", length = 20)
    private String code;

    @JsonIgnore
    @MapsId("baseSerialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_resident_serial_number")
    private Resident resident;

    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    public static class Pk implements Serializable {
        @Column(name = "base_resident_serial_number")
        private Long baseSerialNumber;

        @Column(name = "family_resident_serial_number")
        private Long familySerialNumber;
    }

    public void changeCode(String code) {
        this.code = code;
    }
}
