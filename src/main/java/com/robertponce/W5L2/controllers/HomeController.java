package com.robertponce.W5L2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.robertponce.W5L2.models.Comment;
import com.robertponce.W5L2.models.Login;
import com.robertponce.W5L2.models.User;
import com.robertponce.W5L2.services.CommentService;
import com.robertponce.W5L2.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private UserService uServ;
	
	@Autowired
	private CommentService cServ;
	
	@GetMapping("/")
	public String index(
			@ModelAttribute("loginUser") Login l,
			@ModelAttribute("registerUser") User u
			) {
		
		return "index.jsp";
	}
	
	
	@PostMapping("/register")
	public String registerRoute(
			@Valid@ModelAttribute("registerUser") User u,
			BindingResult result,
			Model model,
			HttpSession session
			
			) {
		
			User currentUser = uServ.registerUser(u,result);
			
			if(result.hasErrors()) {
				model.addAttribute("loginUser",new Login());
				return "index.jsp";
			}
			
			Long userId = currentUser.getId();
			session.setAttribute("userId", userId);
			
			
			
			
		
		
		return "redirect:/dashboard";
	}
	
	@PostMapping("/login")
	public String loginRoute(
			@Valid@ModelAttribute("loginUser") Login l,
			BindingResult result,
			Model model,
			HttpSession session
			) {
		
		User currentUser = uServ.login(l,result);

			
			if(result.hasErrors()) {
				model.addAttribute("registerUser" ,new User());
				return "index.jsp";
			}
			
			Long userId = currentUser.getId();
			session.setAttribute("userId", userId);
			
			
		
		return "redirect:/dashboard";
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage(
			Model model,HttpSession session
			) {
			Long userId = (Long) session.getAttribute("userId");
			if(userId == null) {
				return "redirect:/";
			}
			User thisUser = uServ.getUserById(userId);
			model.addAttribute("user",thisUser);
			model.addAttribute("comments",cServ.getAllComments());
		return "dash.jsp";
	}
	
	@GetMapping("/comments/new")
	public String createComment(
			@ModelAttribute("comment") Comment comment,
			HttpSession session,
			Model model
			) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		User thisUser = uServ.getUserById(userId);
		model.addAttribute("user",thisUser);
		
		return "addComment.jsp";
	}
	
	@PostMapping("/comments")
	public String handleComment(
			@Valid @ModelAttribute("comment") Comment comment,
			BindingResult result,
			HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		
		User u = uServ.getUserById(userId);
		
		if(result.hasErrors()) {
			return "addComment.jsp";
		}
		
		comment.setUser(u);
		
		cServ.createComment(comment);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/comments/{id}")
	public String viewComment(
			@PathVariable("id") Long id,
			Model model,
			HttpSession session
			) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		User thisUser = uServ.getUserById(userId);
		model.addAttribute("user",thisUser);
		model.addAttribute("comment", cServ.getOneComment(id));
		return "viewOneComment.jsp";
	}
	
	
	@GetMapping("/comments/{id}/edit")
	public String viewEditComment(
			@PathVariable("id") Long id,
			Model model,
			HttpSession session
			) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		User thisUser = uServ.getUserById(userId);
		model.addAttribute("user",thisUser);
		model.addAttribute("comment", cServ.getOneComment(id));
		return "editOneComment.jsp";
	}
	
	@PutMapping("/comments/{id}")
	public String editCommentHandler(
			
			Model model,
			@Valid @ModelAttribute("comment") Comment comment,
			BindingResult result,
			HttpSession session,
			@PathVariable("id") Long id
			) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		
		User u = uServ.getUserById(userId);
		Comment c = cServ.getOneComment(id);
		
		
		if(result.hasErrors()) {
			
			return "editOneComment.jsp";
		}
		
		comment.setUser(u);
		
		if(c.getUser().getId() == userId) {
			cServ.createComment(comment);

		}
		
		return "redirect:/dashboard";
	}
	
	@DeleteMapping("/comments/{id}/delete")
	public String deleteCommentHandler(
		
			HttpSession session,
			@PathVariable("id") Long id
			) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		Comment c = cServ.getOneComment(id);

		if(c.getUser().getId().equals(userId)) {
			cServ.deleteOne(id);

		}
		
		return"redirect:/dashboard";
		
	}
	
	@PostMapping("comments/{id}/likes/new")
	public String likeCommentHandler(
			
			HttpSession session,
			@PathVariable("id") Long id
			) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		Comment c = cServ.getOneComment(id);
		User u = uServ.getUserById(userId);
		
		if(c.getUser().getId() != userId) {
			c.getLiked_comment().add(u);
			cServ.createComment(c);
			
		}
		
		
		return"redirect:/dashboard";
		
	}
	
	@DeleteMapping("comments/{id}/likes/delete")
	public String unlikeCommentHandler(
			
			HttpSession session,
			@PathVariable("id") Long id
			) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		Comment c = cServ.getOneComment(id);
		User u = uServ.getUserById(userId);
		
		if(! c.getUser().getId().equals(userId)) {
			c.getLiked_comment().remove(u);
			cServ.createComment(c);
			
		}
		
		
		return"redirect:/dashboard";
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
		
	}
	
		
	
	
}
