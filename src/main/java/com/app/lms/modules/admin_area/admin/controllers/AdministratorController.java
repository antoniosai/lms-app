package com.app.lms.modules.admin_area.admin.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.admin.dtos.AdministratorDTO;
import com.app.lms.modules.admin_area.admin.entities.AdminEntity;
import com.app.lms.modules.admin_area.admin.requests.GetAdministratorRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AdministratorController {
    ResponseEntity<HttpResponseDTO<PaginationUtil<AdminEntity, AdministratorDTO>>> getAdminByPagination(int page, int perPage, GetAdministratorRequest getAdminRequest);

    ResponseEntity<HttpResponseDTO<AdministratorDTO>> getAdminById(UUID adminUuid) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<AdministratorDTO>> createNewAdmin(AdministratorDTO newAdminData);

    ResponseEntity<HttpResponseDTO<AdministratorDTO>> updateAdminByUuid(UUID adminUuid, AdministratorDTO newAdminData);

    ResponseEntity<HttpResponseDTO<String>> deleteAdminByUuid(UUID adminUuid) throws NotFoundException;
}
