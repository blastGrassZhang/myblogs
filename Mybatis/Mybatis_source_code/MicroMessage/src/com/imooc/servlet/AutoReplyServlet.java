package com.imooc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.service.QueryService;

/**
 * �Զ��ظ����Ʋ�
 * @author ���ĺ�
 *
 */
@WebServlet("/AutoReplyServlet.action")
public class AutoReplyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ��ֹ����
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter  out = resp.getWriter();
		QueryService queryService = new QueryService();
		out.write(queryService.queryByCommand(req.getParameter("content")));
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
