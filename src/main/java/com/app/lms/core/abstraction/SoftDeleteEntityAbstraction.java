package com.app.lms.core.abstraction;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class SoftDeleteEntityAbstraction {
    @Column(name = "deleted_by", length = 40)
    private String deletedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
