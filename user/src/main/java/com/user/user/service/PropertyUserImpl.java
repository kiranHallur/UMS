package com.user.user.service;

import com.user.user.dto.PageDetails;
import com.user.user.dto.PropertyUserDto;
import com.user.user.entity.PropertyUserEntity;
import com.user.user.exception.ResourceNotFoundException;
import com.user.user.repository.PropertyUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyUserImpl implements PropertyUser {

    private PropertyUserRepository propertyUserRepository;

    //insted of @Autowired we used constructor parameter
    public PropertyUserImpl(PropertyUserRepository propertyUserRepository) {
        this.propertyUserRepository = propertyUserRepository;
    }

    //used to convert dto to entity          job of this method is supply dto obj convert it to entity and return
    PropertyUserEntity dtoToEntity(PropertyUserDto dto){
        PropertyUserEntity entity = new PropertyUserEntity();
        entity.setName(dto.getName());
        entity.setMobile(dto.getMobile());
        entity.setEmailId(dto.getEmailId());
        return entity;
    }

    //used to convert entity to dto         job of this method is give entity obj, it copies to dto and returns back dto
    PropertyUserDto EntityToDto(PropertyUserEntity en){
        PropertyUserDto puo=new PropertyUserDto();
        puo.setId(en.getId());
        puo.setName(en.getName());
        puo.setEmailId(en.getEmailId());
        puo.setMobile(en.getMobile());
        return puo;
    }

    @Override
    public PropertyUserDto addPropertyUser(PropertyUserDto dto) {
//        PropertyUserEntity entity = new PropertyUserEntity();
//        entity.setName(dto.getName());
//        entity.setMobile(dto.getMobile());
//        entity.setEmailId(dto.getEmailId());

        PropertyUserEntity entity = dtoToEntity(dto);  //dto obj came from dtoToEntity method

        PropertyUserEntity savedPropertyUser = propertyUserRepository.save(entity);

        //now we need to copy the entity content to dto
//        PropertyUserDto puo=new PropertyUserDto();
//        puo.setId(savedPropertyUser.getId());
//        puo.setName(savedPropertyUser.getName());
//        puo.setEmailId(savedPropertyUser.getEmailId());
//        puo.setMobile(savedPropertyUser.getMobile());

        PropertyUserDto puo=EntityToDto(savedPropertyUser);  //called the method EntityToDto, it converts entity to dto

        return puo; //return the object
    }


    @Override
    public void deletePropertyUser(long propertyUserId) {
        propertyUserRepository.deleteById(propertyUserId);
    }


    @Override
    public PropertyUserDto updatePropertyUser(long propertyUserId, PropertyUserDto dto) {
        Optional<PropertyUserEntity> optionalPropertyUserEntity = propertyUserRepository.findById(propertyUserId);

        if (optionalPropertyUserEntity.isPresent()) {
            PropertyUserEntity propertyUserEntity = optionalPropertyUserEntity.get();
            // Update entity with DTO data
            dtoToEntity(dto, propertyUserEntity);
            // Save updated entity
            propertyUserEntity = propertyUserRepository.save(propertyUserEntity);
            // Convert entity to DTO
            return entityToDto(propertyUserEntity);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + propertyUserId);
        }
    }

    // Method to update entity from DTO
    private void dtoToEntity(PropertyUserDto dto, PropertyUserEntity entity) {
        entity.setName(dto.getName());
        entity.setMobile(dto.getMobile());
        entity.setEmailId(dto.getEmailId());
        // You can add more mappings here if needed
    }

    // Method to convert PropertyUserEntity to PropertyUserDto
    private PropertyUserDto entityToDto(PropertyUserEntity entity) {
        PropertyUserDto dto = new PropertyUserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setMobile(entity.getMobile());
        dto.setEmailId(entity.getEmailId());
        // You can add more mappings here if needed
        return dto;
    }




    @Override
    public List<PropertyUserDto> getPropertyUsers(int pageSize, int pageNo, String sortBy, String sortDir) {
        // PageRequest pageable = PageRequest.of(pageNo, pageSize);  PageRequest is class, so it will not work, we need to do class upcasting

        Pageable pageable=null;
        if(sortDir.equalsIgnoreCase("asc")){
            pageable=PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else if (sortDir.equalsIgnoreCase("desc")) {
            pageable=PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        //Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy)); //sortBy is string, so we need to convert it by using Sort.
        //now we need to pass pageable in findAll(), and it will not return in list, but we need list as return type so
        //List<PropertyUserEntity> pue = propertyUserRepository.findAll();

        Page<PropertyUserEntity> all = propertyUserRepository.findAll(pageable);
        List<PropertyUserEntity> pue = all.getContent();  //getContent() will convert and return as list.
        List<PropertyUserDto> pud = pue.stream().map(p -> EntityToDto(p)).collect(Collectors.toList());

        //this lines are for
        System.out.println(all.getTotalPages());
        System.out.println(all.getNumber());
        return pud;
    }

    //if we inter 100 as id in postman it will crash, so we need to handle it with exception, so this code will not handle it
//    @Override
//    public PropertyUserEntity getPropertyUserById(long propertyUseId) {
//        PropertyUserEntity propertyUserEntity = propertyUserRepository.findById(propertyUseId).get();
//        return propertyUserEntity;
//    }

    @Override
    public PropertyUserEntity getPropertyUserById(long propertyUseId) {
        PropertyUserEntity propertyUserEntity = propertyUserRepository.findById(propertyUseId).orElseThrow(
                // orElseThrow will take supplier here we used () parentheses for supplier
                ()->new ResourceNotFoundException("user not found with id:"+propertyUseId)  //throwing exception
                  //obj created
        );
        return propertyUserEntity;
    }


}
