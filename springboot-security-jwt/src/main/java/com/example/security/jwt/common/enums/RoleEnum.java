package com.example.security.jwt.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author zhaojh
 * @PackageName:com.wangfugui.apprentice.common.enums
 * @ClassName: RoleEnum
 * @Description: TODO
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(1, "管理员"),
    NORMAL(2,"普通用户");

    private Integer id;
    private String desc;
}
