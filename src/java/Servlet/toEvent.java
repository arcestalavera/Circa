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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arces
 */
public class toEvent extends HttpServlet {

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
        CircaDatabase db = CircaDatabase.getInstance();
        String action = request.getParameter("action");
        int eventID = 0, userID;
        User user;
        RequestDispatcher reqDispatcher = null;

        switch (action) {
            case "view":
                eventID = Integer.parseInt(request.getParameter("id"));
                Event event = db.getEventDetails(eventID);
                event.setPostList(db.getPosts(eventID));
                request.getSession().setAttribute("eventDetails", event);
                reqDispatcher = request.getRequestDispatcher("Event.jsp");
                break;

            case "create":
                user = (User) request.getSession().getAttribute("loggedUser");
                userID = user.getUserID();

                Event newEvent = getEvent(request);

                db.addEvent(userID, newEvent.getEventName(), new java.sql.Timestamp(newEvent.getStartDate().getTime()), new java.sql.Timestamp(newEvent.getEndDate().getTime()), newEvent.getVenue(), newEvent.getType(), newEvent.getDescription());
                reqDispatcher = request.getRequestDispatcher("CreateEvent.jsp");
                break;

            case "delete":
                eventID = Integer.parseInt(request.getParameter("id"));
                user = (User) request.getSession().getAttribute("loggedUser");

                db.deleteEvent(eventID);

                reqDispatcher = request.getRequestDispatcher("User?action=view&id=" + user.getUserID());
                break;

            case "edit":
                request.getSession().setAttribute("isEdit", true);
                reqDispatcher = request.getRequestDispatcher("CreateEvent.jsp");
                break;

            case "confirm":
                eventID = Integer.parseInt(request.getParameter("id"));
                Event editEvent = getEvent(request);

                db.editEvent(eventID, editEvent.getEventName(), new java.sql.Timestamp(editEvent.getStartDate().getTime()), new java.sql.Timestamp(editEvent.getEndDate().getTime()), editEvent.getVenue(), editEvent.getType(), editEvent.getDescription());
                reqDispatcher = request.getRequestDispatcher("Event?action=view&id=" + eventID);
                break;

            case "join":
                eventID = Integer.parseInt(request.getParameter("id"));
                Event joinEvent = db.getEventDetails(eventID);
                user = (User) request.getSession().getAttribute("loggedUser");

                switch (joinEvent.getType()) {
                    case "Public":
                        db.addJoin(eventID, user.getUserID());
                        break;
                    case "Closed":
                        db.addJoinRequest(joinEvent.getHost().getUserID(), eventID, user.getUserID());
                        break;
                }
                reqDispatcher = request.getRequestDispatcher("Event?action=view&id=" + eventID);
                break;
                
            case "leave":
                eventID = Integer.parseInt(request.getParameter("id"));
                user = (User) request.getSession().getAttribute("loggedUser");
                
                db.deleteJoin(eventID, user.getUserID());
                reqDispatcher = request.getRequestDispatcher("Event?action=view&id=" + eventID);
                break;
        }
        reqDispatcher.forward(request, response);
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
        processRequest(request, response);
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

    public Event getEvent(HttpServletRequest request) {
        String eventName = request.getParameter("eventName");
        String eventVenue = request.getParameter("eventVenue");
        String eventDescription = request.getParameter("eventDescription");
        String eventStartDate = request.getParameter("eventStartDate");
        String eventStartTime = request.getParameter("eventStartTime");
        String eventEndDate = request.getParameter("eventEndDate");
        String eventEndTime = request.getParameter("eventEndTime");
        String eventType = request.getParameter("eventType");
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

        Event tempEvent = new Event(eventName, eventVenue, eventType, eventDescription, startDate, endDate);

        return tempEvent;
    }
}
