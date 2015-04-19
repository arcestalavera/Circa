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
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String curPage = request.getParameter("curpage");
        int postID;
        String commentText;
        int userID, commentID;
        RequestDispatcher reqDispatcher = null;
        CircaDatabase db = CircaDatabase.getInstance();

        switch (action) {
            case "add":
                User user = (User) request.getSession().getAttribute("loggedUser");
                userID = user.getUserID();
                postID = Integer.parseInt(request.getParameter("id"));
                commentText = request.getParameter("commentText");
                commentID = db.addComment(postID, commentText, userID);
                switch (curPage) {
                    case "event":
                        response.getWriter().write("<li id = \"comment_" + commentID + "\">\n"
                                + "                                        <div class = \"post-comment\">\n"
                                + "                                            <form onsubmit = \"return deleteComment(" + commentID + ")\">\n"
                                + "                                                <input type = \"submit\" class = \"remove-post\" value = \"x\"/>\n"
                                + "                                            </form>\n"
                                + "                                            <img src = \"" + user.getProfilePicture() + "\" alt = \"" + user.getFirstName() + " " + user.getLastName() + "\" class = \"comment-pic\"/>\n"
                                + "                                            <p class = \"event-post-text\"><a href = \"User?action=view&id=" + user.getUserID() + "\"><b>\n"
                                + "                                                        <br>" + user.getFirstName() + " " + user.getLastName() + "</b></a> " + commentText + "</p>\n"
                                + "                                        </div>\n"
                                + "                                    </li>");

                        break;
                    case "cluster":
                        System.out.println("cluster hehehe");
                        response.getWriter().write("<li class = \"post-comment-commenter-div\">\n"
                                + "                                <a href = \"User?action=view&id=" + user.getUserID() + "\">\n"
                                + "                                    <img src = \"" + user.getProfilePicture() + "\" title = \"" + user.getFirstName() + " " + user.getLastName() + "\" class = \"post-comment-commenter-img\" height = \"30px\" width=\"30px\">\n"
                                + "                                </a>\n"
                                + "\n"
                                + "                                <div class = \"post-comment-commenter-info\">\n"
                                + "                                    <p class = \"post-comment-commenter-name\">\n"
                                + "                                        <a href = \"User?action=view&id=" + user.getUserID() + "\" class = \"link\">\n"
                                + "                                            " + user.getFirstName() + " " + user.getLastName() + "\n"
                                + "                                        </a>\n"
                                + "                                    </p>\n"
                                + "                                    <form class = \"delete-comment-form\" action=\"Comment?action=delete&id=<%=comment.getCommentID()%>&curpage=cluster\" method = \"post\">\n"
                                + "                                        <input type=\"image\" src=\"img/clusterpage/DeleteButtonSmall.png\" class=\"delete-comment-button\"/>\n"
                                + "                                    </form>\n"
                                + "                                    <p class = \"post-comment-commenter-text\">" + commentText + "</p>\n"
                                + "                                </div>\n"
                                + "                            </li>");
                        //reqDispatcher = request.getRequestDispatcher("ClusterPage.jsp");
                        break;
                    case "home":
                        reqDispatcher = request.getRequestDispatcher("Home.jsp");
                        break;
                }

                break;

            case "delete":
                commentID = Integer.parseInt(request.getParameter("id"));

                db.deleteComment(commentID);
                switch (curPage) {
                    case "cluster":
                        reqDispatcher = request.getRequestDispatcher("ClusterPage.jsp");
                        break;
                    case "home":
                        reqDispatcher = request.getRequestDispatcher("Home.jsp");
                        break;
                }
                break;
        }
       /* if (!curPage.equals("event")) {
            reqDispatcher.forward(request, response);
        }*/
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
