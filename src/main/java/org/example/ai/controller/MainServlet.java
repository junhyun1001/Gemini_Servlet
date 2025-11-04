package org.example.ai.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.example.ai.service.AIService;

@WebServlet("/")
public class MainServlet extends HttpServlet {

  private AIService ai = null;

  @Override
  public void init() throws ServletException {
    ai = new AIService();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setAttribute("title", "AI가 질문을 답해줍니다!");
    req.setAttribute("answer", "질문을 입력해보세요!");
    req.getRequestDispatcher("/WEB-INF/chat.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String textParam = req.getParameter("text"); // form = post, input name=text
        String answer = ai.chat( "%s, 꾸미는 텍스트 없이.".formatted(textParam));
//    String answer = ai.chatByGroq("%s, 꾸미는 텍스트 없이.".formatted(textParam));
    req.setAttribute("title", "'%s'에 대한 AI의 답".formatted(textParam));
    req.setAttribute("answer", answer);
    req.getRequestDispatcher("/WEB-INF/chat.jsp").forward(req, resp);
  }
}
