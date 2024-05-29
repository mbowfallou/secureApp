package com.samanecorp.secureapp.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samanecorp.secureapp.dto.AccountUserDto;
import com.samanecorp.secureapp.mapper.AccountUserMapper;
import com.samanecorp.secureapp.service.LoginService;

@WebServlet("/login") // name="login", value = 
public class LoginServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);
	private LoginService loginService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
        AccountUserMapper userMapper = new AccountUserMapper();
        this.loginService = new LoginService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("\n\tConnexion...\n");
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password  = req.getParameter("password");
		
		Optional<AccountUserDto> userDTO = loginService.login(email, password);
		
		if(userDTO.isPresent()) {
			HttpSession session = req.getSession();
			AccountUserDto user = userDTO.get();
			session.setAttribute("user", user);
	        
			//req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(request, response);
			//req.setAttribute("user", user);
			//resp.sendRedirect("welcome"); // request.getContextPath() + "/welcome"
			resp.sendRedirect(req.getContextPath() + "/welcome");
		} else {
            req.setAttribute("error", "Invalid email or password");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
	}
}