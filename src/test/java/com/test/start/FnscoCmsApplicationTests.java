package com.test.start;


import com.fnsco.cms.FnscoCmsApplication;
import com.fnsco.cms.dao.RoleMapper;
import com.fnsco.cms.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FnscoCmsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FnscoCmsApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void test() {

        Role role = new Role();

        List<Role> roles = roleMapper.pageList(role, 0, 2);
        for (Role role1 : roles
                ) {

            System.out.println(role1.getRoleId());
        }


    }

}
