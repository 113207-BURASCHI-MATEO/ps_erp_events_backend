package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.user.UserDTO;
import com.tup.ps.erpevents.dtos.user.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    Page<UserDTO> getUsers(Pageable pageable);
    UserDTO getUserById(Long idUser);
    void softDeleteUser(Long id, String userEmail);
    UserDTO update(Long id, String userEmail, UserUpdateDTO userUpdateDTO);
    UserDTO userDetails(Long id, String email);
    List<UserDTO> findUsersByIds(List<Long> ids);
}
