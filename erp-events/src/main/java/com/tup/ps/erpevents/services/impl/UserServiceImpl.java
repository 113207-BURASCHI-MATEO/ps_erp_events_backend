package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.user.UserDTO;
import com.tup.ps.erpevents.dtos.user.UserUpdateDTO;
import com.tup.ps.erpevents.entities.UserEntity;
import com.tup.ps.erpevents.exceptions.ApiException;
import com.tup.ps.erpevents.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public List<UserDTO> getUsers(Integer softDelete, Integer page, Integer size){
        Page<UserEntity> users = userRepository.findAllBySoftDelete(softDelete, PageRequest.of(page, size));
        var totalPages = users.getTotalPages();

        if (totalPages <= page) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "No existe el numero de pagina");
        }

        String next = "", prev = "";
        if (users.hasNext()){
            next = "/users?page="+(page+1);
        }

        if (users.hasPrevious()){
            prev = "/users?page="+(page-1);
        }

        List<UserDTO> usersPage = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
        return usersPage;
    }

    /**
     * Realiza una baja logica del usuario que entra por parametro
     * (1: baja, 0: activo)
     * @param id
     */
    public void softDeleteUser(Long id, String userEmail) {
        /*UserEntity userLogged = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuario autenticado no encontrado"));*/

        UserEntity userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuario a eliminar no encontrado"));

        /*boolean isAdmin = userLogged.getRole().getName() == RoleName.ADMIN;
        boolean isSameUser = Objects.equals(userLogged, userToDelete);

        if (!isAdmin && !isSameUser) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "No se puede eliminar el usuario porque no tiene permiso de administrador");
        }*/

        userToDelete.setSoftDelete(true);
        userRepository.save(userToDelete);
    }

    public UserDTO update(Long id, String userEmail, UserUpdateDTO userUpdateDTO) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        boolean isSameUser = Objects.equals(user.getIdUser(), id);
        if (!isSameUser) {
            throw new ApiException(HttpStatus.CONFLICT, "El usuario loggeado no coincide con el id recibido");
        }

        if (userUpdateDTO.getFirstName() != null) {
            user.setFirstName(userUpdateDTO.getFirstName());
        }

        if (userUpdateDTO.getLastName() != null) {
            user.setLastName(userUpdateDTO.getLastName());
        }

        if (userUpdateDTO.getBirthDate() != null) {
            user.setBirthDate(userUpdateDTO.getBirthDate());
        }

        if (userUpdateDTO.getDocumentNumber() != null) {
            user.setDocumentNumber(userUpdateDTO.getDocumentNumber());
        }

        if (userUpdateDTO.getPassword() != null) {
            if (userUpdateDTO.getPassword().isBlank() || userUpdateDTO.getPassword().isEmpty()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "La contraseña no puede estar vacia");
            }
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }

        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO userDetails(Long id, String email){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if(!user.getIdUser().equals(id)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "No coincide el id con el usuario autenticado");
        }

        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> findUsersByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        List<UserEntity> users = userRepository.findAllById(ids);

        if (users.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "No se encontraron usuarios para los IDs proporcionados");
        }

        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

}
