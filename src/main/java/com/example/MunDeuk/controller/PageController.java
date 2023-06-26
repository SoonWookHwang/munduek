package com.example.MunDeuk.controller;

import com.example.MunDeuk.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

  private final MemberService memberService;


  @GetMapping("/")
  public String homepage() {
    return "index";
  }

//  @GetMapping("/login")
//  public String loginpage(){
//    return "login";
//  }
//

  @GetMapping("/login")
  public String getLoginPage(HttpServletResponse res) {
    return "login";
  }

  @GetMapping("/signup")
  public String getSigunupPage() {
    return "signup";
  }

//  @PostMapping("/login")
//  public String processLogin(@RequestParam("username") String username,
//      @RequestParam("password") String password,
//      HttpSession session) throws UserNotFoundException, InvalidPasswordException {
//    Member member = memberService.login(username, password);
//    session.setAttribute("member", member);
//
//    return "redirect:/";
//  }
}
