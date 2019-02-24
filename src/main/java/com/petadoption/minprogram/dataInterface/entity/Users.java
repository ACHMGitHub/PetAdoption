package com.petadoption.minprogram.dataInterface.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String uuid;

    private String userWxOpenId;

    private String userSalt;

    private String userSessionKey;

    private String userName;

    private String userGeneral;

    private String userCity;

    private BigDecimal userIncome;

    private String userPhone;

    private LocalDateTime userCreateTime;

    private String userAvator;

    private LocalDateTime userBirthday;

    private LocalDateTime userLastLogin;
}
