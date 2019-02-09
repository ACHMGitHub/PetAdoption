package com.petadoption.minprogram.dataInterface.mapper;

import com.petadoption.minprogram.dataInterface.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2019-02-07
 */
public interface UsersMapper extends BaseMapper<Users> {

    /**
     * 根据用户uuid查找
     *
     * @param uuid
     * @return
     */
    Users findByUUID(@Param("uuid") String uuid);
}
