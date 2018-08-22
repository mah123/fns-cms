package com.fnsco.cms.service;

import com.fnsco.cms.model.Manager;

import java.util.List;

public interface ManagerService {

    Integer addManager(Manager manager);

    Integer removeManager(Integer managerId);

    Integer changerManager(Manager manager);

    Manager getOne(Integer managerId);

    List<Manager> getAll();
}
