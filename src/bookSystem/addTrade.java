package bookSystem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Database;

/**
 * Servlet implementation class addTrade
 */
@WebServlet("/addTrade")
public class addTrade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addTrade() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (Database.addTrade(request.getParameter("symbol"),
				request.getParameter("exp"),
				request.getParameter("lots"),
				request.getParameter("price"),
				request.getParameter("buysell"),
				request.getParameter("trader"),
				request.getParameter("transDate"),
				request.getParameter("transTime"))) {
			response.sendRedirect(request.getContextPath());
		} 
	}

}
