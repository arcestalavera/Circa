/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classes.Event;
import Classes.User;
import Database.CircaDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Arren Antioquia
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        CircaDatabase db = CircaDatabase.getInstance();
        HttpSession reqSession = request.getSession();
        Boolean isCorrect;
        RequestDispatcher reqDispatcher;
        
        String referer = request.getHeader("Referer");
        System.out.println("Request URL" + request.getRequestURL());
        System.out.println("Referer " + referer);
        
        // if user is logging in
       // if(referer.equals("http://localhost:8080/Circa/") || referer.equals("http://localhost:8080/Circa/Logout")){
            String inputUser = request.getParameter("inputUser");
            String inputPass = request.getParameter("inputPassword");
            
            if (inputPass.equals(db.getPassword(inputUser))) {
                isCorrect = true;

                //get user ID
                int userID = db.getUserID(inputUser);

                //get and set user info
                User loggedUser = db.getUserDetails(userID);
                
                //put user info in session
                reqSession.removeAttribute("isCorrect");
                reqSession.setAttribute("loggedUser", loggedUser);
                
                //redirect to home
                reqDispatcher = request.getRequestDispatcher("Home.jsp");
            } else {
                isCorrect = false;
                reqSession.setAttribute("isCorrect", isCorrect);
                reqDispatcher = request.getRequestDispatcher("index.jsp");
            }
            reqDispatcher.forward(request, response);
        //}
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
