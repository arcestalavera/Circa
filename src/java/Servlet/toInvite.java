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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arces
 */
public class toInvite extends HttpServlet {

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
        String action = request.getParameter("action");
        CircaDatabase db = CircaDatabase.getInstance();
        User user;
        Event event;
        response.setContentType("text/html;charset=UTF-8");

        switch (action) {
            case "invite":
                String[] invitedID = request.getParameterValues("invite-buddy");
                user = (User) request.getSession().getAttribute("loggedUser");
                event = (Event) request.getSession().getAttribute("eventDetails");
                System.out.println("invitedID = " + invitedID.length + "\nuser = " + user.getFirstName() + "\nevent = " + event.getEventName());

                for (String invite : invitedID) {
                    User invited = db.getUserDetails(Integer.parseInt(invite));
                    response.getWriter().write("<form onsubmit = \"return uninviteBuddy(" + invited.getUserID() + ")\">\n"
                            + "                                <li id = \"invited_" + invited.getUserID() + "\" class = \"invite-item\">\n"
                            + "                                    <img src = \"" + invited.getProfilePicture() + "\" class = \"invite-pic\"/>\n"
                            + "                                    <label for=\"invite-buddy\"> " + invited.getFirstName() + " " + invited.getLastName() + "</label>\n"
                            + "\n"
                            + "                                    <input type = \"submit\" class = \"remove-post\" value = \"x\"/>\n"
                            + "                                </li>\n"
                            + "                            </form>");
                    db.addInvite(user.getUserID(), event.getEventID(), Integer.parseInt(invite));
                }
                break;

            case "uninvite":
                int userID = Integer.parseInt(request.getParameter("id"));
                event = (Event) request.getSession().getAttribute("eventDetails");
                user = db.getUserDetails(userID);
                db.deleteInvite(event.getEventID(), userID);

                response.getWriter().write("<li id = \"buddy_" + userID + "\" class = \"invite-item\">\n"
                        + "                                    <input type = \"checkbox\" id = \"invite-buddy\" name = \"invite-buddy\" value = \"" + user.getUserID() + "\"/>\n"
                        + "                                    <img src = \"" + user.getProfilePicture() + "\" class = \"invite-pic\"/>\n"
                        + "                                    <label for=\"invite-buddy\"> " + user.getFirstName() + " " + user.getLastName() + "</label>\n"
                        + "                                </li>");
                break;
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

}
