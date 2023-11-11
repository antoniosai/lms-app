package com.app.lms.modules.admin.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.JpaResultHelperUtil;
import com.app.lms.core.utils.ObjectMapperUtil;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.core.utils.SpecificationUtil;
import com.app.lms.modules.admin.repositories.AdministratorMainRepository;
import com.app.lms.modules.admin.specifications.AdministratorSpecification;
import com.app.lms.modules.admin.dtos.AdministratorDTO;
import com.app.lms.modules.admin.entities.AdminEntity;
import com.app.lms.modules.admin.requests.GetAdministratorRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorMainRepository adminMainRepository;

    @Override
    public PaginationUtil<AdminEntity, AdministratorDTO> getAdminByPagination(int page, int perPage, GetAdministratorRequest getAdminRequest) {

        Page<AdminEntity> pagedData = adminMainRepository.findAll(
                AdministratorSpecification.searchByPaginationRequest(getAdminRequest),
                SpecificationUtil.generatePagination(page, perPage)
        );

        return new PaginationUtil<>(pagedData, AdministratorDTO.class);
    }

    @Override
    public AdministratorDTO getAdminByUuid(UUID adminUuid) throws NotFoundException {
        return findSingleAdminByUuid(adminUuid);
    }

    @Override
    public AdministratorDTO createNewAdmin(AdministratorDTO newAdminData) {
        newAdminData.setAdminUuid(UUID.randomUUID());

        log.info("Inserting new Admin Data => " + newAdminData);

        AdminEntity newAdmin = adminMainRepository.save(ObjectMapperUtil.map(newAdminData, AdminEntity.class));

        return ObjectMapperUtil.map(newAdmin, AdministratorDTO.class);
    }

    @Override
    public AdministratorDTO updateAdminByUuid(UUID adminUuid, AdministratorDTO newAdminData) {

        newAdminData.setAdminUuid(adminUuid);

        AdminEntity updatedAdmin = adminMainRepository.save(ObjectMapperUtil.map(newAdminData, AdminEntity.class));

        return ObjectMapperUtil.map(updatedAdmin, AdministratorDTO.class);
    }

    @Override
    public List<AdministratorDTO> findByAccountUuid(UUID adminUuid) {
        log.info("TEST | Looking for Admin => " + adminUuid);
        return ObjectMapperUtil.mapAll(adminMainRepository.findByAccountUuid(adminUuid), AdministratorDTO.class);
    }

    @Override
    public AdministratorDTO findOneByAccountUuid(UUID adminUuid) throws NotFoundException {
        List<AdministratorDTO> administrators = findByAccountUuid(adminUuid);

        if (administrators.isEmpty()) {
            throw new NotFoundException("Administrator Not Found");
        }

        return administrators.get(0);
    }

    @Override
    public void deleteAdminByUuid(UUID adminUuid) throws NotFoundException {
        log.info("Deleting Admin with UUID => " + adminUuid);

        findSingleAdminByUuid(adminUuid);

        // Delete Account ID

        adminMainRepository.deleteById(adminUuid);
    }

    private AdministratorDTO findSingleAdminByUuid(UUID adminUuid) throws NotFoundException {
        AdminEntity admin = JpaResultHelperUtil.getSingleResultFromOptional(adminMainRepository.findById(adminUuid));

        if (admin == null) {
            log.info("Admin Not Found");
            throw new NotFoundException("Admin Not Found");
        }

        return ObjectMapperUtil.map(admin, AdministratorDTO.class);
    }
}
