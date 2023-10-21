package com.app.lms.modules.admin_area.admin.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.core.validations.IsNumeric;
import com.app.lms.modules.admin_area.admin.dtos.AdministratorDTO;
import com.app.lms.modules.admin_area.admin.entities.AdminEntity;
import com.app.lms.modules.admin_area.admin.requests.GetAdministratorRequest;
import com.app.lms.modules.admin_area.admin.services.AdministratorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/administrator-area/administrator")
public class AdministratorControllerImpl implements AdministratorController {
    @Autowired
    private AdministratorService adminService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<AdminEntity, AdministratorDTO>>> getAdminByPagination(
            @RequestParam(defaultValue = "1") @IsNumeric int page,
            @RequestParam(defaultValue = "20") @IsNumeric int perPage,
            GetAdministratorRequest getAdminRequest
    ) {
        return new HttpResponseDTO<>(adminService.getAdminByPagination(page, perPage, getAdminRequest))
                .setResponseHeaders("getAdminRequest", getAdminRequest)
                .setResponseHeaders("page", page)
                .setResponseHeaders("perPage", perPage)
                .toResponse("Fetch Admin by Pagination from Server");
    }

    @Override
    @GetMapping(value = "/{adminUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<AdministratorDTO>> getAdminById(@PathVariable UUID adminUuid) throws NotFoundException {
        return new HttpResponseDTO<>(adminService.getAdminByUuid(adminUuid))
                .setResponseHeaders("adminUuid", adminUuid)
                .toResponse("Fetch Single Admin by UUID from Server");
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<AdministratorDTO>> createNewAdmin(@Valid @RequestBody AdministratorDTO newAdminData) {
        return new HttpResponseDTO<>(adminService.createNewAdmin(newAdminData), HttpStatus.CREATED)
                .setResponseHeaders("newAdminData", newAdminData)
                .toResponse("Successfully Created an Admin");
    }

    @Override
    @PutMapping(value = "/{adminUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<AdministratorDTO>> updateAdminByUuid(@PathVariable UUID adminUuid, @RequestBody @Valid AdministratorDTO newAdminData) {
        return new HttpResponseDTO<>(adminService.updateAdminByUuid(adminUuid, newAdminData))
                .setResponseHeaders("newAdminData", newAdminData)
                .toResponse("Successfully Updated an Admin By UUID from Server");
    }

    @Override
    @DeleteMapping(value = "/{adminUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<String>> deleteAdminByUuid(@PathVariable UUID adminUuid) throws NotFoundException {
        adminService.deleteAdminByUuid(adminUuid);
        return new HttpResponseDTO<>("Admin has been Deleted")
                .setResponseHeaders("adminUuid", adminUuid)
                .toResponse("Successfully Deleted an Admin By UUID from Server");
    }
}
