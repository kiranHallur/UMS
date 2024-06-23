package com.user.user.service;

import com.user.user.dto.PropertyUserDto;
import com.user.user.entity.PropertyUserEntity;

import java.util.List;

public interface PropertyUser {


    public PropertyUserDto addPropertyUser(PropertyUserDto dto);

    void deletePropertyUser(long propertyUserId);

    PropertyUserDto updatePropertyUser(long propertyUserId, PropertyUserDto dto);

    List<PropertyUserDto> getPropertyUsers(int pageSize, int pageNo, String sortBy, String sortDir);


    PropertyUserEntity getPropertyUserById(long propertyUseId);
}
