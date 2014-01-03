/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conv.view;

import conv.controller.GWSFacade;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author albert
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    	private static final long serialVersionUID = 1L;
        private GWSFacade gnomeFacade = new GWSFacade();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        System.out.println("username: "+ user);
        System.out.println("password: "+ pwd);
        gnomeFacade.register(user, pwd);
        
        response.sendRedirect("RegisterSuccess.html");
}
    
}
