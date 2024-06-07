package com.samanecorp.secureapp.controller;

import java.io.IOException;
import java.io.Serial;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samanecorp.secureapp.dto.AccountUserDto;
import com.samanecorp.secureapp.entities.AccountUserEntity;
import com.samanecorp.secureapp.mapper.AccountUserMapper;
import com.samanecorp.secureapp.service.LoginService;
import com.samanecorp.secureapp.util.HibernateUtil;

@WebServlet(name = "signup", value = "/signup")
public class SignUpServlet extends HttpServlet {
	
	@Serial
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SignUpServlet.class);
	private LoginService loginService;

	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
        //AccountUserMapper userMapper = new AccountUserMapper();
        this.loginService = new LoginService();
	}


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);		
		if(session.getAttribute("user") != null){
			logger.info("\n\n\tVous etes deja connecte, deconnectez-vous d'abord...\n");
			resp.sendRedirect("welcome");
		} else {
			//request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
			req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
		}
	}


	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Checking if the User already exists
		boolean emailExists = loginService.isEmailExist(email);
        if (emailExists) {
        	 request.setAttribute("errorMessage", "Email already exists. Please choose another one.");
             request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
        } else {
			AccountUserDto user = new AccountUserDto();
			user.setEmail(email);
			user.setPassword(password);
			user.setState(true);

			// Save the user to the database
			int result = loginService.register(user);

			logger.info("\n\n\tSaving result: {}...\n", result);

			if(result == 1) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath() + "/welcome");
			} else {
				response.sendRedirect(request.getContextPath() + "/signup");
			}
		}
    }

}
