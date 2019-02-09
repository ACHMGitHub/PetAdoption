package com.petadoption.minprogram.dataInterface.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2019-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "media_id", type = IdType.AUTO)
    private Integer mediaId;

    private String mediaName;

    private String mediaType;

    private String mediaPath;

    private LocalDateTime mediaUploadTime;

    private Integer mediaPet;


}
