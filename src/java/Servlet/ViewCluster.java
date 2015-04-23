/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classes.Cluster;
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
 * @author Arren Antioquia
 */
public class ViewCluster extends HttpServlet {

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
            out.println("<title>Servlet AddClusterMembers</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddClusterMembers at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        int clusterID = Integer.parseInt(request.getParameter("clusterID"));

        CircaDatabase db = CircaDatabase.getInstance();
        String clusterName = db.getClusterName(clusterID);

        Cluster cluster = new Cluster(clusterID, clusterName);

        cluster.setMemberList(db.getClusterMembers(clusterID));

        request.getSession().setAttribute("clusterToProcess", cluster);

        User user = (User) request.getSession().getAttribute("loggedUser");
        user.setEventList(db.getEvents(user.getUserID()));

        RequestDispatcher reqDispatcher = request.getRequestDispatcher("ClusterPage.jsp");
        reqDispatcher.forward(request, response);
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

        String type = request.getParameter("form-type");
        User user = (User) request.getSession().getAttribute("loggedUser");
        Cluster cluster = (Cluster) request.getSession().getAttribute("clusterToProcess");
        CircaDatabase db = CircaDatabase.getInstance();

        response.setContentType("text/html;charset=UTF-8");
        if (type != null) {
            if (type.equals("add-cluster-member")) {
                String[] newmember = request.getParameterValues("new-member");
                if (newmember != null) {
                    for (String s : newmember) {
                        db.addUserToCluster(user.getUserID(), Integer.parseInt(s), cluster.getClusterID());
                        User newMember = db.getUserDetails(Integer.parseInt(s));
                        response.getWriter().write("<li id = \"member_" + newMember.getUserID() + "\" class = \"cluster-member\">\n"
                                + "                        <form onsubmit = \"return deleteMember(" + newMember.getUserID() + ")\" class = \"delete-cluster-member-form\">\n"
                                + "                            <input type = \"hidden\" name = \"cluster-member-id\" value = \"" + newMember.getUserID() + "\" />\n"
                                + "                            <input type = \"hidden\" name = \"form-type\" value = \"delete-cluster-member\" />\n"
                                + "                            <input class = \"delete-cluster-member-button\" type = \"image\" src = \"img/clusterpage/DeleteButtonSmall.png\">\n"
                                + "                        </form>\n"
                                + "                        <a href = \"User?action=view&id=" + newMember.getUserID() + "\">\n"
                                + "                            <img src=\"" + newMember.getProfilePicture() + "\" class = \"cluster-member-img\" title = \"" + newMember.getFirstName() + " " + newMember.getLastName() + "\" width = \"60px\" height=\"60px\" />\n"
                                + "                        </a>\n"
                                + "                    </li>");
                    }
                }
            } else if (type.equals("delete-cluster-member")) {
                int clusterMemberID = Integer.parseInt(request.getParameter("cluster-member-id"));

                db.deleteUsertoCluster(clusterMemberID, cluster.getClusterID());
                User member = db.getUserDetails(clusterMemberID);

                response.getWriter().write("<li class = \"new-member-item\">\n"
                        + "                                    <input type = \"checkbox\" id = \"new-member\" name = \"new-member\" value = \"" + member.getUserID() + "\"/>\n"
                        + "                                    <label for=\"new-member\">" + member.getFirstName() + " " + member.getLastName() + "</label>\n"
                        + "                                </li>");
            } else if (type.equals("edit-cluster-name")) {
                String name = request.getParameter("cluster-name");
                int clusterID = Integer.parseInt(request.getParameter("cluster-id"));
                cluster.setName(name);
                db.editClusterName(clusterID, name);

                response.getWriter().write(name);
            }
        }
        //RequestDispatcher reqDispatcher = request.getRequestDispatcher("ClusterPage.jsp");
        //  reqDispatcher.forward(request, response);
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
