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
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sub_id", type = IdType.AUTO)
    private Integer subId;

    private String subType;

    private String subVariety;

    private String subDescibe;

    private LocalDateTime subSubTime;

    private String subUser;


}
