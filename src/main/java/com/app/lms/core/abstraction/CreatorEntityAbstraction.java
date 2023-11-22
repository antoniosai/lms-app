package com.app.lms.core.abstraction;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class CreatorEntityAbstraction {
    @Column(name = "created_by")
    private String createdBy;
}
