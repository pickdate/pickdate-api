package com.pickdate.iam.infrastructure;

import com.pickdate.iam.domain.DomainUrl;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
class DomainUrlAttributeConverter implements AttributeConverter<DomainUrl, String> {

    @Override
    public String convertToDatabaseColumn(DomainUrl domainUrl) {
        return domainUrl == null ? null : domainUrl.getValue();
    }

    @Override
    public DomainUrl convertToEntityAttribute(String dbData) {
        return dbData == null || dbData.isBlank() ? null : DomainUrl.of(dbData);
    }
}

