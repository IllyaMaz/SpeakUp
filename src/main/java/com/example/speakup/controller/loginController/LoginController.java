package com.example.speakup.controller.loginController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class LoginController {

    @GetMapping
    public void get(HttpServletResponse resp){
        try {
            resp.sendRedirect("/report");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
