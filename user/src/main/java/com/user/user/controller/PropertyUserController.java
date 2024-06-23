package com.user.user.controller;

import com.user.user.dto.PropertyUserDto;

import com.user.user.entity.PropertyUserEntity;
import com.user.user.service.PropertyUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //makes class as RestAPI class
@RequestMapping("/api/v1/propertyUser")
public class PropertyUserController {

    //http://localhost:8080/api/v1/propertyUser/addPropertyUser

    //instead of autowired
    private PropertyUser pu;  // created constructor parameter also class upcasting is done

    public PropertyUserController(PropertyUser pu) {
        this.pu = pu;  // Automatically inject the object
                       // child obj of interface is automatically injected to parent
                      // i.e. PropertyUserImpl       to                      PropertyUser
    }

    @PostMapping ("/addPropertyUser")
    public ResponseEntity<?> addPropertyUser(
            @Valid @RequestBody PropertyUserDto dto,   //@RequestBody used to copy json content to java object

            BindingResult result    // capture error msg automatically
            ){
        if(result.hasErrors()){
            //if error is present, we need that error with error msg mentioned in dto
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.OK);
            //get the msg from dto
        }


        //returned from the service layer(impl class)
        PropertyUserDto propertyUserDto = pu.addPropertyUser(dto);

        //as return type is response entity and return type will be dto
        return new ResponseEntity<>(propertyUserDto, HttpStatus.CREATED);
                                // propertyUserDto will return back the dto object info
    }

    //http://localhost:8080/api/v1/propertyUser?propertyUserId=
    @DeleteMapping
    public ResponseEntity<String> deletePropertyUser(
            @RequestParam long propertyUserId
    ){
        pu.deletePropertyUser(propertyUserId);
        return new ResponseEntity<>("record deleted", HttpStatus.OK);
    }


    //updating
    //http://localhost:8080/api/v1/propertyUser/id
    @PutMapping("/{propertyUserId}")  //as we have used @PathVariable we need to substitute the variable here
    public ResponseEntity<PropertyUserDto> updatePropertyUser(
            // as we are returning different kind of values (class[PropertyUserEntity] and string[msg] we used "?" as return type

            @PathVariable long propertyUserId,   //for fetching details
            @RequestBody PropertyUserDto dto     //for updating

    ){

       PropertyUserDto propertyUserEntityDto = pu.updatePropertyUser(propertyUserId,dto);
        return new ResponseEntity<>(propertyUserEntityDto, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/propertyUser?pageSize=3&pageNo=0&sortBy=emailId&sortDir=asc
    //list or reading
    @GetMapping
    public ResponseEntity<List<PropertyUserDto>> getPropertyUsers(
            @RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize,    //pageSize will take value from url
                                         //in url if we not give pageSize then automatically it will take 5
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "id", required = false) String sortDir
    ){
        List<PropertyUserDto> propertyUserEntityDto= pu.getPropertyUsers(pageSize,pageNo,sortBy,sortDir);
        return new ResponseEntity<>(propertyUserEntityDto, HttpStatus.OK);
    }

    //get by id
    @GetMapping("/getUserById")
    public ResponseEntity<PropertyUserEntity> getPropertyUserById(
            @RequestParam long propertyUseId
    ){
        PropertyUserEntity propertyUserEntity= pu.getPropertyUserById(propertyUseId);
        return new ResponseEntity<>(propertyUserEntity, HttpStatus.OK);
    }

}
