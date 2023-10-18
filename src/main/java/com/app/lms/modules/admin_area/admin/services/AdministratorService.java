package com.app.lms.modules.admin_area.admin.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.admin.dtos.AdministratorDTO;
import com.app.lms.modules.admin_area.admin.entities.AdminEntity;
import com.app.lms.modules.admin_area.admin.requests.GetAdministratorRequest;

import java.util.List;
import java.util.UUID;

public interface AdministratorService {

    PaginationUtil<AdminEntity, AdministratorDTO> getAdminByPagination(int page, int perPage, GetAdministratorRequest getAdminRequest);

    AdministratorDTO getAdminByUuid(UUID adminUuid) throws NotFoundException;

    AdministratorDTO createNewAdmin(AdministratorDTO newAdminData);

    AdministratorDTO updateAdminByUuid(UUID adminUuid, AdministratorDTO newAdminData);

    List<AdministratorDTO> findByAccountUuid(UUID adminUuid);

    void deleteAdminByUuid(UUID adminUuid) throws NotFoundException;
}
