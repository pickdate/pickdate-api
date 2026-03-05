package com.pickdate.poll.infrastructure;

import com.pickdate.poll.domain.Phone;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
class PhoneConverter implements AttributeConverter<Phone, String> {

    @Override
    public String convertToDatabaseColumn(Phone phone) {
        return phone == null ? null : phone.getValue();
    }

    @Override
    public Phone convertToEntityAttribute(String dbData) {
        return dbData == null
                ? Phone.EMPTY
                : Phone.of(dbData);
    }
}
