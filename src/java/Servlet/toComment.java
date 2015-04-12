/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classes.Cluster;
import Classes.Event;
import Classes.Post;
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
public class toComment extends HttpServlet {

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
        String curPage = request.getParameter("curpage");
        int postID;
        String commentText;
        int userID;
        RequestDispatcher reqDispatcher = null;
        CircaDatabase db = CircaDatabase.getInstance();

        switch (action) {
            case "add":
                User user = (User) request.getSession().getAttribute("loggedUser");
                Cluster cluster = (Cluster) request.getSession().getAttribute("clusterToProcess");
                userID = user.getUserID();
                postID = Integer.parseInt(request.getParameter("id"));
                commentText = request.getParameter("commentText");
                db.addComment(postID, commentText, userID);
                //getpost
                Post post = db.getPostDetails(postID);
                if(curPage.equals("event"))
                    reqDispatcher = request.getRequestDispatcher("Event?action=view&id=" + post.getEvent().getEventID());
                else if(curPage.equals("cluster"))
                    reqDispatcher = request.getRequestDispatcher("ClusterPage.jsp");
                break;
            
            case "delete":
                int commentID = Integer.parseInt(request.getParameter("id"));
                Event event = (Event) request.getSession().getAttribute("eventDetails");

                db.deleteComment(commentID);
                if(curPage.equals("event"))
                    reqDispatcher = request.getRequestDispatcher("Event?action=view&id=" + event.getEventID());
                else if(curPage.equals("cluster"))
                    reqDispatcher = request.getRequestDispatcher("ClusterPage.jsp");
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

}
