package ru.Harevich.BookSite.util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Устанавливаем код ответа 403 (Запрещено)
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Перенаправляем пользователя на кастомную страницу ошибки доступа
        RequestDispatcher dispatcher = request.getRequestDispatcher("/access-denied");
        dispatcher.forward(request, response);
    }
}