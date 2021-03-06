package org.wordsmith.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.wordsmith.blog.domain.Authority;
import org.wordsmith.blog.domain.User;
import org.wordsmith.blog.service.AuthorityService;
import org.wordsmith.blog.service.UserService;
import org.wordsmith.blog.util.ConstraintViolationExceptionHandler;
import org.wordsmith.blog.vo.Response;

/**
 * 主页控制器
 * @author Wordsmith
 *
 */
@Controller
public class MainController {

private static final Long ROLE_USER_AUTHORITY_ID = 2L;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService  authorityService;
	
	@GetMapping("/")
	public String root() {
		return "redirect:/index";
	}
	
	@GetMapping("/index")
	public String index() {
		return "redirect:/blogs";
	}

	/**
	 * 获取登录界面
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登陆失败，账号或者密码错误！");
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	/**
	 * 注册用户
	 * @param user
	 * @param result
	 * @param redirect
	 * @return
	 */
//	@PostMapping("/register")
//	public String registerUser(User user) {
//		List<Authority> authorities = new ArrayList<>();
//		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
//		user.setAuthorities(authorities);
//		userService.registerUser(user);
//		return "redirect:/login";
//	}
	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(User user) {
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
		user.setAuthorities(authorities);
		try {
			userService.registerUser(user);
		}  catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", user));
	}
	
	@GetMapping("/search")
	public String search() {
		return "search";
	}
}