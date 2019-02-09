package com.petadoption.minprogram.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.petadoption.minprogram.dataInterface.entity.Role;
import com.petadoption.minprogram.dataInterface.entity.Userrole;
import com.petadoption.minprogram.dataInterface.entity.Users;
import com.petadoption.minprogram.dataInterface.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限的操作
 * 添加角色
 * 添加权限
 * 为用户添加角色
 * 为角色添加权限
 */
@Service
public class AuthorityManagerImpl implements AuthorityManager {

    @Autowired
    private IUsersService usersService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IUserroleService userroleService;
    @Autowired
    private IRolepermissionService rolepermissionService;

    private String userTypeConvert(String userType) throws Exception {
        switch (userType) {
            case "发布者":
                return "promulgator";
            case "管理员":
                return "admin";
            case "普通用户":
                return "users";
            default:
                throw new Exception("用户类型错误");
        }
    }

    public void addRoleToUser(String userType, String userId) throws Exception {
        List<Role> roleList = roleService.list(new QueryWrapper<Role>().eq("ro_name", userTypeConvert(userType)));
        Users user = usersService.getById(userId);

        if (user == null)
            return;
        if (roleList == null || roleList.size() < 1)
            return;

        for (Role r : roleList) {
            Userrole userrole = new Userrole();
            userrole.setUrUser(userId);
            userrole.setUrRole(r.getRoId());
            userroleService.save(userrole);
        }
    }

}
