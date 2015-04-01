/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import Database.CircaDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arces
 */
@WebServlet(name = "SignupServlet", urlPatterns = {"/SignupServlet"})
public class Signup extends HttpServlet {

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
            out.println("<title>Servlet SignupServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignupServlet at " + request.getContextPath() + "</h1>");
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
       // processRequest(request, response);
        response.setContentType("text/html");
        
        CircaDatabase db = CircaDatabase.getInstance();
        
        try{
            ArrayList<Boolean> errorList = new ArrayList<>();
            String password = request.getParameter("password");
            String confirmPass = request.getParameter("confirmpassword");
            RequestDispatcher reqDispatcher = null;
            
            if(!password.equals(confirmPass))
            {
                errorList.add(true);
            }
            else{
                String birthday = request.getParameter("birthday");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = sdf1.parse(birthday);
                java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());  
                System.out.println("FIRST NAME: " + request.getParameter("first_name"));
                System.out.println("LAST NAME: " + request.getParameter("last_name"));
                System.out.println("EMAIL ADDRESS: " + request.getParameter("email_address"));
                System.out.println("BIRTHDAY: " + sqlStartDate);
                System.out.println("USERNAME: " + request.getParameter("username"));
                System.out.println("PASSWORD: " + request.getParameter("password"));
                
                db.addUser(request.getParameter("first_name"), request.getParameter("last_name"), request.getParameter("email_address"), 
                        sqlStartDate, request.getParameter("username"), request.getParameter("password"));
            }
            reqDispatcher = request.getRequestDispatcher("index.jsp");
            reqDispatcher.forward(request, response);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
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
