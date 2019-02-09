package com.petadoption.minprogram.dataInterface.service.impl;

import com.petadoption.minprogram.dataInterface.entity.Comment;
import com.petadoption.minprogram.dataInterface.mapper.CommentMapper;
import com.petadoption.minprogram.dataInterface.service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
