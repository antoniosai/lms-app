package com.app.lms.modules.admin.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin.dtos.AdministratorDTO;
import com.app.lms.modules.admin.entities.AdminEntity;
import com.app.lms.modules.admin.requests.GetAdministratorRequest;

import java.util.List;
import java.util.UUID;

public interface AdministratorService {

    PaginationUtil<AdminEntity, AdministratorDTO> getAdminByPagination(int page, int perPage, GetAdministratorRequest getAdminRequest);

    AdministratorDTO getAdminByUuid(UUID adminUuid) throws NotFoundException;

    AdministratorDTO createNewAdmin(AdministratorDTO newAdminData);

    AdministratorDTO updateAdminByUuid(UUID adminUuid, AdministratorDTO newAdminData);

    List<AdministratorDTO> findByAccountUuid(UUID adminUuid);

    AdministratorDTO findOneByAccountUuid(UUID adminUuid) throws NotFoundException;

    void deleteAdminByUuid(UUID adminUuid) throws NotFoundException;
}
