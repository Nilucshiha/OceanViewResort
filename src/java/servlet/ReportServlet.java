/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

/**
 *
 * @author Nilucshiha
 */
import service.ReportService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportType = request.getParameter("reportType");
        ReportService reportService = new ReportService();
        String report = "";

        if(reportType.equals("Room Occupancy")) {
            report = reportService.getRoomOccupancyReport();
        } else if(reportType.equals("Total Revenue")) {
            report = reportService.getTotalRevenueReport();
        }

        request.setAttribute("report", report);
        request.getRequestDispatcher("report.jsp").forward(request, response);
    }
}
