package com.app.lms.modules.admin_area.admin.requests;

import lombok.Data;

@Data
public class GetAdministratorRequest {
    private String identificationNumber;

    private String name;
}
