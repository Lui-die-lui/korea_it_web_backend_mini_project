package com.korit.BoardStudy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.RelationNotification;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    private Integer userRoleId;
    private Integer userId;
    private Integer RoleId;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    private Role role;

}
