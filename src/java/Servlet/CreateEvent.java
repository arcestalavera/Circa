/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classes.User;
import Database.CircaDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arces
 */
public class CreateEvent extends HttpServlet {

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
            out.println("<title>Servlet CreateEvent</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateEvent at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        
        //get user id
        User user = (User)request.getSession().getAttribute("loggedUser");
        int userID = user.getUserID();
        
        //get event details
        String eventName = request.getParameter("eventName");
        String eventVenue = request.getParameter("eventVenue");
        String eventDescription = request.getParameter("eventDescription");
        String eventStartDate = request.getParameter("eventStartDate");
        String eventStartTime = request.getParameter("eventStartTime");
        String eventEndDate = request.getParameter("eventEndDate");
        String eventEndTime = request.getParameter("eventEndTime");
        String eventType = request.getParameter("eventType");
        CircaDatabase db = CircaDatabase.getInstance();
        Date startDate = null;
        Date endDate = null;
                
        try {
            //set start date with time
            SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            startDate = startDateFormat.parse(eventStartDate + " " + eventStartTime + ":00");
            
            //set end date with time
            SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            endDate = endDateFormat.parse(eventEndDate + " " + eventEndTime + ":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        db.addEvent(userID, eventName, new java.sql.Timestamp(startDate.getTime()), new java.sql.Timestamp(endDate.getTime()), eventVenue, eventType, eventDescription);
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
