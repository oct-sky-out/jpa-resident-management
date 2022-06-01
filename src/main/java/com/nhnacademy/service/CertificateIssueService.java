package com.nhnacademy.service;

import com.nhnacademy.command.BirthIssueCommand;
import com.nhnacademy.command.Command;
import com.nhnacademy.command.DeathIssueCommand;
import com.nhnacademy.command.FamilyCertCommand;
import com.nhnacademy.command.RegistrationCertCommand;
import com.nhnacademy.dto.certificateissue.CertificationTitleDto;
import com.nhnacademy.dto.certificateissue.RegisterCertificateIssueDto;
import com.nhnacademy.dto.familyrelationship.FamilyRelationshipCertificationDto;
import com.nhnacademy.dto.familyrelationship.FamilyRelationshipCertificationDtoImpl;
import com.nhnacademy.dto.household.HouseholdViewDto;
import com.nhnacademy.dto.householdcomposition.HouseholdCompositionViewDto;
import com.nhnacademy.dto.housemovementaddress.HouseMovementAddressViewDto;
import com.nhnacademy.entity.CertificateIssue;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.exceptions.HasNotFamilyException;
import com.nhnacademy.exceptions.HouseholdNotFoundException;
import com.nhnacademy.exceptions.InvalidCertificationException;
import com.nhnacademy.exceptions.ResidentNotFoundExceprtion;
import com.nhnacademy.repository.certificateissue.CertificateIssueRepository;
import com.nhnacademy.repository.familyrelationship.FamilyRelationshipRepository;
import com.nhnacademy.repository.householdcomposition.HouseholdCompositionResidentRepository;
import com.nhnacademy.repository.householdmovement.HouseholdMovementAddressRepository;
import com.nhnacademy.repository.household.HouseholdRepository;
import com.nhnacademy.repository.resident.ResidentRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CertificateIssueService {
    private static final String FAMILY_CERT = "가족관계증명서";
    private static final String REGISTRATION_CERT = "주민등록등본";
    private static final String BIRTH_ISSUE = "출생신고서";
    private static final String DEATH_ISSUE = "사망신고서";

    private final CertificateIssueRepository certificateIssueRepository;
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final ResidentRepository residentRepository;
    private final HouseholdCompositionResidentRepository householdCompositionResidentRepository;
    private final HouseholdRepository householdRepository;
    private final HouseholdMovementAddressRepository householdMovementAddressRepository;

    public CertificateIssueService(CertificateIssueRepository certificateIssueRepository,
                                   FamilyRelationshipRepository familyRelationshipRepository,
                                   ResidentRepository residentRepository,
                                   HouseholdCompositionResidentRepository
                                       householdCompositionResidentRepository,
                                   HouseholdRepository householdRepository,
                                   HouseholdMovementAddressRepository
                                       householdMovementAddressRepository) {
        this.certificateIssueRepository = certificateIssueRepository;
        this.familyRelationshipRepository = familyRelationshipRepository;
        this.residentRepository = residentRepository;
        this.householdCompositionResidentRepository = householdCompositionResidentRepository;
        this.householdRepository = householdRepository;
        this.householdMovementAddressRepository = householdMovementAddressRepository;
    }

    public String getRedirectionUrl(CertificateIssue certification,
                                    RegisterCertificateIssueDto registerCertificateIssue){
        Command nextUrlCommand = null;
        // 가족관계증명서
        if(Objects.equals(certification.getCode(), FAMILY_CERT)){
            nextUrlCommand = new FamilyCertCommand(certification.getConfirmationNumber() + "");
        }
        // 출생신고서
        if(Objects.equals(certification.getCode(), BIRTH_ISSUE)){
            nextUrlCommand = new BirthIssueCommand(registerCertificateIssue.getSerialNumber() + ""
            );
        }
        // 사망신고서
        if(Objects.equals(certification.getCode(), DEATH_ISSUE)){
            nextUrlCommand = new DeathIssueCommand(
                registerCertificateIssue.getSerialNumber() + ""
            );
        }
        // 주민등록본
        if(Objects.equals(certification.getCode(), REGISTRATION_CERT)){
            nextUrlCommand = new RegistrationCertCommand(
                certification.getConfirmationNumber()
                + "?serialNumber="
                + registerCertificateIssue.getSerialNumber());
        }
        if(Objects.isNull(nextUrlCommand)){
            throw new InvalidCertificationException("존재하지않는 증명서 형식입니다.");
        }

        return nextUrlCommand.getRedirectionUrl();
    }

    @Transactional
    public CertificateIssue registerCertificate(RegisterCertificateIssueDto certificateIssueDto){
        Optional<Resident> resident = residentRepository.findById(certificateIssueDto.getSerialNumber());

        if(resident.isPresent()){
            CertificateIssue certificateIssue = CertificateIssue.builder()
                .resident(resident.get())
                .code(certificateIssueDto.getTypeCode())
                .issueDate(LocalDate.now())
            .build();

            return certificateIssueRepository.saveAndFlush(certificateIssue);
        }

        throw new ResidentNotFoundExceprtion("주민일련번호가 존재하지않습니다.");
    }

    public CertificationTitleDto certificationTitle(Long confirmationNumber){
        return certificateIssueRepository.getCertificationTitleBySerialNumber(confirmationNumber);
    }

    public List<FamilyRelationshipCertificationDto> getFamilyRelationshipCertification(Long serialNumber){
        Optional<Resident> resident = residentRepository.findById(serialNumber);
        List<FamilyRelationshipCertificationDto> certificationDtos =
            familyRelationshipRepository.findFamilyRelationshipBySerialNumber(serialNumber)
                .orElseThrow(() -> new HasNotFamilyException("가족정보가 없습니다."));

        resident.ifPresent(resident1 -> {
            addSelfResident(resident1, certificationDtos);
        });

        return certificationDtos.stream()
            .map(certificationDto -> {
                var temp = new FamilyRelationshipCertificationDtoImpl(certificationDto);
                temp.hideRegistrationBackNumber();
                return temp;
            })
            .sorted(Comparator.comparing(FamilyRelationshipCertificationDtoImpl::getBirthDate))
            .collect(Collectors.toList());
    }

    private void addSelfResident(Resident resident, List<FamilyRelationshipCertificationDto> certificationDtos) {
        certificationDtos.add(FamilyRelationshipCertificationDtoImpl.builder()
            .relationshipCode("본인")
            .name(resident.getName())
            .birthDate(resident.getBirthDate())
            .registrationNumber(resident.getRegistrationNumber())
            .genderCode(resident.getGenderCode())
            .build());
    }

    public Long findHouseholdSerialNumber(Long residentSerialNumber){
        Optional<Long> householdSerials =
            householdCompositionResidentRepository.findHouseholdSerialByResidentSerial(residentSerialNumber);

        return householdSerials
            .orElseThrow(() -> new HouseholdNotFoundException("세대주를 발견하지 못했습니다."));
    }

    public List<HouseholdCompositionViewDto> findHouseCompositionResidents(Long householdSerial){
        return householdCompositionResidentRepository.findHouseholdCompositionBy(householdSerial)
            .stream()
            .map(householdCompositionViewDto -> {
                householdCompositionViewDto.hideRegistrationBackNumber();
                return householdCompositionViewDto;
            })
            .collect(Collectors.toList());
    }

    public HouseholdViewDto findHousehold(Long householdSerial){
        return householdRepository.findBy(householdSerial)
            .orElseThrow(() -> new HouseholdNotFoundException("세대를 발견하지 못했습니다"));
    }

    public List<HouseMovementAddressViewDto> findHouseMoventsRecord(Long householdSerial){
        return householdMovementAddressRepository.findHouseMovementAddressesBy(householdSerial);
    }
}
