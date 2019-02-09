package com.petadoption.minprogram.dataInterface.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.petadoption.minprogram.dataInterface.entity.Users;
import com.petadoption.minprogram.dataInterface.mapper.UsersMapper;
import com.petadoption.minprogram.dataInterface.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2019-02-07
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Override
    public Users getUserByWxOpenId(String wxOpenId) {
        return this.getOne(new QueryWrapper<Users>().eq("user_wx_open_id", wxOpenId));
    }
}
