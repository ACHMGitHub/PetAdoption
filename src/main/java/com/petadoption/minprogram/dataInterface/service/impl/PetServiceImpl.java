package com.petadoption.minprogram.dataInterface.service.impl;

import com.petadoption.minprogram.dataInterface.entity.Pet;
import com.petadoption.minprogram.dataInterface.mapper.PetMapper;
import com.petadoption.minprogram.dataInterface.service.IPetService;
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
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements IPetService {

}
