package org.wordsmith.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wordsmith.blog.domain.Authority;
import org.wordsmith.blog.domain.User;
import org.wordsmith.blog.repository.UserRepository;
import org.wordsmith.blog.service.AuthorityService;
import org.wordsmith.blog.service.UserService;
import org.wordsmith.blog.util.ConstraintViolationExceptionHandler;
import org.wordsmith.blog.vo.Response;

/**
 * 
 * @author Administrator
 *
 */

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityService authorityService;
	
	/**
	 * 查询所用用户
	 * @return
	 */
	@GetMapping
	public ModelAndView list(@RequestParam(value="async",required=false) boolean async,
			@RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
			@RequestParam(value="name",required=false,defaultValue="") String name,
			Model model) {
	 
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<User> page = userService.listUsersByNameLike(name, pageable);
		List<User> list = page.getContent();	// 当前所在页面数据列表
		
		model.addAttribute("page", page);
		model.addAttribute("userList", list);
		return new ModelAndView(async==true?"users/list :: #mainContainerRepleace":"users/list", "userModel", model);
	}

	/**
	 * 获取 form 表单页面
	 * @param user
	 * @return
	 */
	@GetMapping("/add")
	public ModelAndView addUser(Model model) {
		model.addAttribute("user", new User(null, null, null, null));
		return new ModelAndView("users/add", "userModel", model);
	}
	
//	/**
//	 * 查询所有用户
//	 * @param model
//	 * @return
//	 */
//	@GetMapping
//	public ModelAndView list(Model model) {
//		model.addAttribute("userList", userRepository.findAll());
//		model.addAttribute("title","用户管理");
//		return new ModelAndView("users/list","userModel",model);
//	}
	
	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Long id,Model model) {
		Optional<User> user = userRepository.findById(id);
		model.addAttribute("user",user.get());
		model.addAttribute("title","查看用户");
		return new ModelAndView("users/view","userModel",model);
	}

	/**
	 * 获取创建表单页面
	 * @param model
	 * @return
	 */
	@GetMapping("/form")
	public ModelAndView createForm(Model model) {
		model.addAttribute("user",new User(null,null,null,null));
		return new ModelAndView("users/form","userModel",model);
	}
	
//	@PostMapping("/submit")
//	public void saveOrUpdateUser(User user) {
//		userRepository.save(user);
//		user = userRepository.save(user);
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("redirect:/users/list");// 重定向至list映射方法
//		System.out.println("123");
//		return mav;
//	}

	/**
	 * 新建用户
	 * @param user
	 * @param result
	 * @param redirect
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Response> create(User user, Long authorityId) {
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(authorityId));
		user.setAuthorities(authorities);
		
		if(user.getId() == null) {
			user.setEncodePassword(user.getPassword()); // 加密密码
		}else {
			// 判断密码是否做了变更
			User originalUser = userService.getUserById(user.getId());
			String rawPassword = originalUser.getPassword();
			PasswordEncoder  encoder = new BCryptPasswordEncoder();
			String encodePasswd = encoder.encode(user.getPassword());
			boolean isMatch = encoder.matches(rawPassword, encodePasswd);
			if (!isMatch) {
				user.setEncodePassword(user.getPassword());
			}else {
				user.setPassword(user.getPassword());
			}
		}
		
		try {
			userService.saveUser(user);
		}  catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", user));
	}

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
		try {
			userService.removeUser(id);
		} catch (Exception e) {
			return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
		}
		return  ResponseEntity.ok().body( new Response(true, "处理成功"));
	}
	
	/**
	 * 获取修改用户的界面，及数据
	 * @param user
	 * @return
	 */
	@GetMapping(value = "edit/{id}")
	public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return new ModelAndView("users/edit", "userModel", model);
	}
	
	/**
	 * 保存、修改用户
	 * @param model
	 * @return
	 */
//	@PostMapping
//	public ModelAndView saveOrUpdateUser(User user,Model model) {
//		
//		user = userRepository.save(user);
//		model.addAttribute("user",user);
//		model.addAttribute("title","查看用户");
//		return new ModelAndView("users/list","userModel",model);
//	}
	
	
	/**
	 * 根据id查询用户
	 * @param model
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
		return new ModelAndView("redirect:/users");
	}

}







