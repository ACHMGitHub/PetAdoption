package com.petadoption.minprogram.dataInterface.service;

import com.petadoption.minprogram.dataInterface.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 * @since 2019-02-07
 */
public interface IUsersService extends IService<Users> {

    Users getUserByWxOpenId(String wxOpenId);
}
