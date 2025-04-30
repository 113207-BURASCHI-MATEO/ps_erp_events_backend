package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.users.UserDTO;
import com.tup.ps.erpevents.dtos.users.UserLoginDTO;
import com.tup.ps.erpevents.dtos.users.UserRegisterDTO;
import com.tup.ps.erpevents.entities.RoleEntity;
import com.tup.ps.erpevents.entities.UserEntity;
import com.tup.ps.erpevents.enums.RoleName;
import com.tup.ps.erpevents.exceptions.ApiException;
import com.tup.ps.erpevents.repositories.RoleRepository;
import com.tup.ps.erpevents.repositories.UserRepository;
import com.tup.ps.erpevents.services.JWTService;
import com.tup.ps.erpevents.services.SecurityService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login (UserLoginDTO us) throws AuthenticationException {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(us.getEmail(), us.getPassword())
        );

        UserEntity user2 = (UserEntity) authentication.getPrincipal();
        return jwtService.createToken(user2.getEmail(), 60);
    }

    @Override
    public UserDTO searchUser(UserLoginDTO us){
        UserDTO userReturn;

        Optional<UserEntity> userEntity = userRepository.findByEmail(us.getEmail());
        if(userEntity != null){
            userReturn = modelMapper.map(userEntity.orElse(null), UserDTO.class);
            return userReturn;
        }
        return null;
    }

    @Override
    public UserDTO registerUser(UserRegisterDTO registerRequest){
        return register(registerRequest, RoleName.USER);
    }

    @Override
    public UserDTO registerAdmin(UserRegisterDTO registerRequest) {
        return register(registerRequest, RoleName.ADMIN);
    }

    @Override
    public UserDTO register(UserRegisterDTO registerRequest, RoleName roleName) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Ese correo electrónico ya está en uso. Elige otro");
        }

        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "No se ha encontrado el rol indicado"));

        UserEntity newUser = new UserEntity();
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setBirthDate(registerRequest.getBirthDate());
        newUser.setDocumentType(registerRequest.getDocumentType());
        newUser.setDocumentNumber(registerRequest.getDocumentNumber());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(role);
        newUser.setSoftDelete(false);

        userRepository.save(newUser);

        return modelMapper.map(newUser, UserDTO.class);
    }

    @Override
    public UserEntity registerEntity(UserRegisterDTO registerRequest, RoleName roleName) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Ese correo electrónico ya está en uso. Elige otro");
        }

        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "No se ha encontrado el rol indicado"));

        UserEntity newUser = new UserEntity();
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setBirthDate(registerRequest.getBirthDate());
        newUser.setDocumentType(registerRequest.getDocumentType());
        newUser.setDocumentNumber(registerRequest.getDocumentNumber());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(role);
        newUser.setSoftDelete(false);

        userRepository.save(newUser);

        return newUser;
    }

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
