package ggomg.MemberManagement.config.APIConfig;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JSONUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String username;
    private String password;

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return password;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {

        obtainJSON(request);
        return username;
    }

    private void obtainJSON(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder jsonPayload = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonPayload.append(line);
            }
            JSONObject jsonData = new JSONObject(jsonPayload.toString());
            this.username = jsonData.getString("email");
            this.password = jsonData.getString("password");
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
