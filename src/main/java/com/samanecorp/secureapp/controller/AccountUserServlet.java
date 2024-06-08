package com.samanecorp.secureapp.controller;

import com.samanecorp.secureapp.dto.AccountUserDto;
import com.samanecorp.secureapp.service.AccountUserService;
import com.samanecorp.secureapp.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet({"/users", "/userDetails", "/userEdit"})
public class AccountUserServlet extends HttpServlet{
	
	@Serial
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AccountUserServlet.class);
	private AccountUserService accountUserService;
	private LoginService loginService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.accountUserService = new AccountUserService();
        this.loginService = new LoginService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();

		try {
			switch (action) {
                case "/userDetails":
					showUser(req, resp, "showDetails");
					break;
				case "/userEdit":
					showUser(req, resp, "showEditForm");
					break;
				default:
					listUsers(req, resp);
					break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            updateUser(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	private void listUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		HttpSession session = request.getSession(false);
		if(session.getAttribute("user") != null){
			logger.info("\n\tUsers List Page...\n");
			Optional<List<AccountUserDto>> usersOptional = accountUserService.getAllUsers();

			if (usersOptional.isPresent()) {
				request.setAttribute("usersList", usersOptional.get());
			} else {
				request.setAttribute("error", "No users found.");
			}
			request.getRequestDispatcher("/WEB-INF/jsp/usersList.jsp").forward(request, response);
		} else {
			logger.info("\n\n\tYou don't have access for that Page !!!\n\n");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	private void showUser(HttpServletRequest request, HttpServletResponse response,String operation) throws SQLException, IOException, ServletException {

		long id = Long.parseLong(request.getParameter("id"));

		HttpSession session = request.getSession(false);
		if(session.getAttribute("user") != null){
			logger.info("\n\tUser Details Page...\n");
			Optional<AccountUserDto> userDto = accountUserService.getUserById(id);

			if (userDto.isPresent()) {
				request.setAttribute("userDto", userDto.get());
			} else {
				request.setAttribute("error", "No user found for id ("+id+").");
			}

			if(operation.equals("showDetails"))
				request.getRequestDispatcher("/WEB-INF/jsp/userDetails.jsp").forward(request, response);
			else if(operation.equals("showEditForm"))
				request.getRequestDispatcher("/WEB-INF/jsp/editUser.jsp").forward(request, response);
		} else {
			logger.info("\n\n\tYou don't have access for that Page !!!\n\n");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}


	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		long id = Long.parseLong(request.getParameter("id"));
		String email = request.getParameter("email").trim();
		boolean state = Boolean.parseBoolean(request.getParameter("state"));

		logger.info("\n\n\tUpdating User Info Page...\n");
		AccountUserDto userDto = accountUserService.getUserById(id).get();

		if (userDto != null) {

			logger.info("\n\n\tUser To UPDATE FOUND (" + userDto.getEmail() + ", " + userDto.isState() + ")...\n");

			if(userDto.getEmail().trim().equals(email) && userDto.isState() == state){

				logger.warn("\n\n\tUser To UPDATE FOUND but Same Info also NOTICED...\n");

				request.setAttribute("errorMessage", "Any change is noticed !!");
			} else {
				userDto.setEmail(email);
				userDto.setState(state);

				// Update the user to the database
				int result = loginService.updateUser(userDto);

				if (result == 1) {
					logger.info("\n\n\tUser updated successfully...\n");
					request.setAttribute("updateState", "User(" + email + ") updated successfully.");
				} else {
					logger.warn("\n\n\tFAILED to update User...\n");
					request.setAttribute("updateState", "User(" + email + ") updated failed.");
				}
			}
			request.setAttribute("userDto", userDto);
			response.sendRedirect(request.getContextPath() + "/userDetails?id=" + id);
		} else {
			logger.error("\n\n\tNo user found for id (\"+id+\")...\n");
			request.setAttribute("error", "No user found for id ("+id+").");
		}
	}
}
