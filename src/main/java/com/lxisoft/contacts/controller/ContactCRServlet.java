package com.lxisoft.contacts.controller;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import com.lxisoft.contacts.model.Contact;
import com.lxisoft.contacts.service.ContactService;
import java.util.logging.Logger;
import java.util.logging.LogManager;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.WARNING;
import static java.util.logging.Level.INFO;

/**
 * Servlet class for working as a controller for getting all the contact
 * and create new contacts
 * get source codes from <a href=${git.remote.origin.url}></a>  
 *
 * @author ${git.build.user.name}
 * ${name:git_config(user.name)}, ${email:git_config(user.email)}
 * @version ${git.commit.id.abbrev}
 * @see javax.servlet.http.HttpServlet
 */

public class ContactCRServlet extends HttpServlet {

	/**
	 * Reference to Service class
	 */
	private ContactService service = new ContactService();

	/**
	 * Logger instance to get log messages
	 */
	private static final Logger logger = Logger.getLogger(ContactCRServlet.class.getName());

	/**
	 * Configures the logger object
	 */
	{

		try {
			LogManager.getLogManager()
					.readConfiguration(new FileInputStream(getClass().getClassLoader().getResource("logging.properties").getFile()));
		}

		catch (SecurityException e) {
			logger.log(WARNING, "Exception caught", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.log(WARNING, "Exception caught", e);
			e.printStackTrace();
		}
	}

	/**
	 * method for initial working
	 *
	 * @throws ServletException
	 *             if undesired condition occures
	 */
	public void init() throws ServletException {
		logger.fine("execution starts");
		logger.fine("execution ends");
	}

	/**
	 * Get method for getting all the elements from the database
	 *
	 * @param request
	 *            http request
	 * @param response
	 *            http response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.log(INFO, "execution starts with request:{0} and response{1}", new Object[] { request, response });

		Set<Contact> contacts = service.findAll();
		request.setAttribute("contacts", contacts);
		logger.info("execution ends");
		request.getRequestDispatcher("contact-list.jsp").forward(request, response);
		// response.sendRedirect("contact-list.jsp");
	}

	/**
	 * Create a new contact based on the parameters received and insert that
	 * contact into the database
	 *
	 * @param request
	 *            http request
	 * @param response
	 *            http response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.log(INFO, "execution starts with request:{0} and response{1}", new Object[] { request, response });

		Contact contact = new Contact(request.getParameter("first_name"), request.getParameter("last_name"),
				request.getParameter("phone_number"), request.getParameter("email"));
		service.save(contact);
		logger.info("execution ends");
		doGet(request, response);
	}

}