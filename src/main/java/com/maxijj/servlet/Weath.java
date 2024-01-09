package com.maxijj.servlet;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

@WebServlet(name = "Weath", value = "/Weath")
public class Weath extends HttpServlet {

    private String tempInfo;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String city = request.getParameter("city");
        String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + city
                + "&appid=175013e0e7a04140e3cae57228b9a79b&units=metric");

        if (!output.isEmpty()) {
            JSONObject cityJSON = new JSONObject(output);
            tempInfo = "Weather" + cityJSON.getJSONObject("main").getInt("temp");
        }

        PrintWriter pw = response.getWriter();
        pw.println("<html>");
        pw.println("<h1>" + tempInfo + "</h1>");
        pw.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static String getUrlContent(String urlWeather) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlWeather);
            URLConnection connection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("City is not find");
        }
        return content.toString();
    }
}
