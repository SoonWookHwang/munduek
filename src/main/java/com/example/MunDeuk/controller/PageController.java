package com.example.MunDeuk.controller;

import com.example.MunDeuk.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/page")
public class PageController {

  private final MemberService memberService;


  @GetMapping
  public String homepage() {
    return "index";
  }

//  @GetMapping("/login")
//  public String loginpage(){
//    return "login";
//  }
//

  @GetMapping("/login")
  public String getLoginPage(@RequestParam(name = "error", required = false) String error, Model model, HttpServletResponse res) {
    if (error != null) {
      model.addAttribute("msg", "로그인에 실패했습니다.");
      return "error";
    }
    return "redirect:login-success";
  }

  @GetMapping("/signup")
  public String getSigunupPage() {
    return "signup";
  }

  @GetMapping("/login-success")
  public String getLoginSuccess(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    model.addAttribute("nickname", userDetails.getUsername());
    return "login-success";
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
