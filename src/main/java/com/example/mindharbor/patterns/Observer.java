package com.example.mindharbor.patterns;

import com.example.mindharbor.Enum.UserType;

public interface Observer {
    void updateUserStatus(UserType userType);
}

