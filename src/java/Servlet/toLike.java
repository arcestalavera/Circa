/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classes.Cluster;
import Classes.Event;
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
public class toLike extends HttpServlet {

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
        RequestDispatcher reqDispatcher;
        int postID = Integer.parseInt(request.getParameter("pid"));
        int userID = Integer.parseInt(request.getParameter("uid"));
        String action = request.getParameter("action");
        String curpage = request.getParameter("curpage");
        Event event = (Event) request.getSession().getAttribute("eventDetails");
        CircaDatabase db = CircaDatabase.getInstance();

        switch (action) {
            case "like":
                db.likePost(postID, userID);
                break;
            case "unlike":
                db.unlikePost(postID, userID);
                break;
        }
        switch (curpage) {
            case "event":
                reqDispatcher = request.getRequestDispatcher("Event?action=view&id=" + event.getEventID());
                reqDispatcher.forward(request, response);
                break;
            case "cluster":
                Cluster cluster = (Cluster) request.getSession().getAttribute("clusterToProcess");
                reqDispatcher = request.getRequestDispatcher("ViewCluster?clusterID=" + cluster.getClusterID());
                reqDispatcher.forward(request, response);
                break;
            case "home":
                reqDispatcher = request.getRequestDispatcher("Home.jsp");
                reqDispatcher.forward(request, response);
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
