package com.qussai.expenseTracker.service;


import com.qussai.expenseTracker.dto.JwtAuthResponse;
import com.qussai.expenseTracker.dto.LoginDto;
import com.qussai.expenseTracker.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);


    String registerAdmin(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);

    Long getUserIdByUsernameOrEmail(String usernameOrEmail);
}
