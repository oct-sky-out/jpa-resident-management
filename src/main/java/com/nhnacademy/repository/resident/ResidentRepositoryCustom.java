package com.nhnacademy.repository.resident;

import com.nhnacademy.dto.resident.ResidentViewDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ResidentRepositoryCustom {
    Page<ResidentViewDto> getAllResidentByPage(Pageable pageable);
}
