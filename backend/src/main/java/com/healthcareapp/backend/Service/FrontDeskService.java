package com.healthcareapp.backend.Service;

import com.healthcareapp.backend.Exception.ResourceNotFoundException;
import com.healthcareapp.backend.Model.Admin;
import com.healthcareapp.backend.Model.FrontDesk;
import com.healthcareapp.backend.Model.Hospital;
import com.healthcareapp.backend.Model.Role;
import com.healthcareapp.backend.Repository.FrontDeskRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FrontDeskService {
    private FrontDeskRepository frontDeskRepository;
    private AuthorizationService authorizationService;
//    private HospitalService hospitalService;
    private AdminService adminService;

    private PasswordEncoder passwordEncoder;

    public FrontDeskService(FrontDeskRepository frontDeskRepository, AuthorizationService authorizationService, AdminService adminService, PasswordEncoder passwordEncoder) {
        this.frontDeskRepository = frontDeskRepository;
        this.authorizationService = authorizationService;
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    public FrontDesk addFrontDesk(FrontDesk frontDesk, String userId) throws RuntimeException{
        authorizationService.checkIfUserIdExists(frontDesk.getUsername());

        Admin admin = adminService.getAdminByUserId(userId);

        Hospital hospital = admin.getHospital();

        frontDesk.setHospital(hospital);
        frontDesk.setRole(Role.ROLE_FRONT_DESK);
        frontDesk.setPassword(passwordEncoder.encode(frontDesk.getPassword()));

        frontDeskRepository.save(frontDesk);
        return frontDesk;
    }

//    public List<FrontDesk> getAllFrontDeskByHospital(Hospital hospital){
//        List<FrontDesk> frontDeskList= frontDeskRepository.findByHospital(hospital);
//        return frontDeskList;
//    }

    public FrontDesk updateFrontDesk(FrontDesk frontDesk) throws RuntimeException{
        Optional<FrontDesk> updatedFrontDesk = frontDeskRepository.findById(frontDesk.getAuthId());

        if(updatedFrontDesk.isEmpty()){
            throw new ResourceNotFoundException("Front Desk with id: "+ frontDesk.getAuthId()+ " not found");
        }

        updatedFrontDesk.get().setName(frontDesk.getName());
        updatedFrontDesk.get().setUsername(frontDesk.getUsername());
        if(frontDesk.getPassword() != null)
            updatedFrontDesk.get().setPassword(passwordEncoder.encode(frontDesk.getPassword()));

        FrontDesk frontDesk1 = frontDeskRepository.save(updatedFrontDesk.get());

        return frontDesk1;
    }

    public FrontDesk getFrontDeskByUserId(String userId){
        Optional<FrontDesk> frontDesk = frontDeskRepository.findByUsername(userId);
        if(frontDesk.isEmpty()){
            throw new ResourceNotFoundException("Front Desk with userId: "+ userId+ " not found");
        }
        return frontDesk.get();
    }
}
