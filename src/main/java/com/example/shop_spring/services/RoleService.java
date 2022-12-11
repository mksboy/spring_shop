package com.example.shop_spring.services;

import com.example.shop_spring.models.Role;
import com.example.shop_spring.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;
@Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRole(){
    return roleRepository.findAll();
    }

    @Transactional
    public void saveRole(Role role){
    roleRepository.save(role);
    }

    public Role getRoleID(int id){
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }

    // Данный метод позволяет обновить данные о категории
    @Transactional
    public void updateRole(int id, Role role){
        role.setId(id);
        roleRepository.save(role);
    }

    // Данный метод позволяет удалить категории по ID
    @Transactional
    public void deleteRole(int id){
        roleRepository.deleteById(id);
    }

}
