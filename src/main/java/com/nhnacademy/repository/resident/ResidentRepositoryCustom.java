package com.nhnacademy.repository.resident;

import com.nhnacademy.dto.resident.ResidentUserDetails;
import com.nhnacademy.dto.resident.ResidentViewDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ResidentRepositoryCustom {
    Page<ResidentViewDto> getAllResidentByPage(Pageable pageable);

    Optional<ResidentUserDetails> findUserDetailsByUsername(String username);
}
