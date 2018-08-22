package com.fnsco.cms.service.impl;

import com.fnsco.cms.dao.ManagerMapper;
import com.fnsco.cms.model.Manager;
import com.fnsco.cms.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public Integer addManager(Manager manager) {
        return managerMapper.insertSelective(manager);
    }

    @Override
    public Integer removeManager(Integer managerId) {
        return managerMapper.delete(managerId);
    }

    @Override
    public Integer changerManager(Manager manager) {
        return managerMapper.updateByPrimaryKeySelective(manager);
    }

    @Override
    public Manager getOne(Integer managerId) {
        return managerMapper.selectByPrimaryKey(managerId);
    }

    @Override
    public List<Manager> getAll() {
        return managerMapper.selectAll();
    }
}
