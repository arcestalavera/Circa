/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classes.Cluster;
import Classes.Event;
import Classes.User;
import Database.CircaDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arces
 */
public class toPost extends HttpServlet {

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
            out.println("<title>Servlet PostToEvent</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostToEvent at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String curpage = request.getParameter("curpage");
        RequestDispatcher reqDispatcher = null;
        Event event = null;
        CircaDatabase db = CircaDatabase.getInstance();
        int postID;
        String postText;

        switch (action) {
            case "post":
                event = (Event) request.getSession().getAttribute("eventDetails");
                User user = (User) request.getSession().getAttribute("loggedUser");
                postText = request.getParameter("postText");

                postID = db.addPost(event.getEventID(), user.getUserID(), postText);
                
                response.getWriter().write("<li id = \"post_" + postID + "\">\n"
                        + "                        <div id = \"event-post-whole\">\n"
                        + "                            <div class = \"event-post-div\">\n"
                        + "                                <form action = \"Post?action=delete&id=" + postID + "&curpage=event\" onsubmit = \"return deletePost()\" method = \"post\">\n"
                        + "                                    <input type = \"submit\" class = \"remove-post\" value = \"x\"/>\n"
                        + "                                </form>\n"
                        + "                                <img src = \"" + user.getProfilePicture() + "\" alt = \"" + user.getFirstName() + " " + user.getLastName() + "\" class = \"post-pic\"/>\n"
                        + "                                <br>\n"
                        + "                                <a href = \"User?action=view&id=" + user.getUserID() + "\">\n"
                        + "                                    <b>" + user.getFirstName() + " " + user.getLastName() + "</b>\n"
                        + "                                </a>\n"
                        + "                                <div id = \"edit-container\">\n"
                        + "                                    <button class = \"edit-button\">Edit This Post</button>\n"
                        + "                                    <div class = \"post-text-div\">"+ postText + "</div>\n"
                        + "                                    <div class = \"edit-post-div\" align = \"center\">\n"
                        + "                                        <form action = \"Post?action=edit&id=" + postID + "&curpage=event\" method = \"post\">\n"
                        + "                                            <textarea name = \"postEditText\" class = \"edit-post-textarea\" rows = \"5\" cols = \"40\">"+ postText + "</textarea><br>\n"
                        + "                                            <input type = \"submit\" value = \"Submit\" class = \"post-edit-submit\"/>\n"
                        + "                                        </form>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <p align = \"right\" id = \"comment-par\">0 likes | <a class = \"comment-link\">Comment</a> <a href= \"Like?action=like&pid=" + postID + "&uid=" + user.getUserID() + "&curpage=event\">Like</a></p>\n"
                        + "                                <div class = \"input-comment-div\" align = \"center\">\n"
                        + "                                    <form action = \"Comment?action=add&id=" + postID + "&curpage=event\" method = \"post\" onsubmit = \"return checkComment('0')\">\n"
                        + "                                        <input type = \"hidden\" name = \"curpage\" value = \"event\" />\n"
                        + "                                        <textarea name = \"commentText\" class = \"comment-textarea\"rows = \"2\" cols = \"70\" placeholder = \"Comment something here!\"></textarea>\n"
                        + "                                        <br>\n"
                        + "                                        <input type = \"submit\" class = \"input-post-submit\" value = \"Comment!\"/>\n"
                        + "                                    </form>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                            <div class = \"post-show-comment\" align = \"center\">Show Comments</div>\n"
                        + "                            <div class =\"post-comments-whole\">\n"
                        + "                                <h3 align = \"center\" class = \"empty-text\">This post has no comments yet.</h3>\n"
                        + "                            </div>\n"
                        + "                        </div>\n"
                        + "                    </li>");
                break;
            case "delete":
                postID = Integer.parseInt(request.getParameter("id"));
                event = (Event) request.getSession().getAttribute("eventDetails");

                db.deletePost(postID);

                break;
            case "edit":
                postID = Integer.parseInt(request.getParameter("id"));
                postText = request.getParameter("postEditText");
                event = (Event) request.getSession().getAttribute("eventDetails");

                db.editPost(postID, postText);
                break;
        }

        // if(curpage.equals("event")){
        //   reqDispatcher = request.getRequestDispatcher("Event?action=view&id=" + event.getEventID());
        // reqDispatcher.forward(request, response);
        //}else 
        if (curpage.equals("cluster")) {
            Cluster cluster = (Cluster) request.getSession().getAttribute("clusterToProcess");
            reqDispatcher = request.getRequestDispatcher("ViewCluster?clusterID=" + cluster.getClusterID());
            reqDispatcher.forward(request, response);
        }else if(curpage.equals("home")){
            reqDispatcher = request.getRequestDispatcher("Home.jsp");
            reqDispatcher.forward(request, response);
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
