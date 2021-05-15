package com.hust.medtech.utils;

import java.util.List;

public class CheckRoleType {
    private int getMaxRoleType(List<String> roles) {
        int roleType = 0;
        for (String role : roles) {
            switch (role) {
                case RoleType.ADMIN:
                    roleType = 3;
                    break;
                case RoleType.DOCTOR:
                    if (roleType < 2) {
                        roleType = 2;
                    }
                    break;
                case RoleType.USER:
                    if (roleType < 1) {
                        roleType = 1;
                    }
                    break;

                default:

                    break;
            }
            if (roleType == 3) {
                break;
            }

        }
        return roleType;
    }
}
