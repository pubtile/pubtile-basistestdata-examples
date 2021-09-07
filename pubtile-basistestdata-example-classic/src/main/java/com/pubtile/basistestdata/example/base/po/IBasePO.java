package com.pubtile.basistestdata.example.base.po;

import java.time.LocalDateTime;

public interface IBasePO {

    Long getId();

    Long getTenantId();

    Long getCreatedBy();

    java.time.LocalDateTime getCreatedTime();

    Long getUpdatedBy();

    java.time.LocalDateTime getUpdatedTime();

    Long getRevision();

    void setId(Long id);

    void setTenantId(Long tenantId);

    void setCreatedBy(Long createdBy);

    void setCreatedTime(LocalDateTime createdTime);

    void setUpdatedBy(Long updatedBy);

    void setUpdatedTime(LocalDateTime updatedTime);

    void setRevision(Long revision);
}
