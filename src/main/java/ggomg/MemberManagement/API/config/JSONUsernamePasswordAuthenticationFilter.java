package ggomg.MemberManagement.API.config;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JSONUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String username;
    private String password;

    public JSONUsernamePasswordAuthenticationFilter() {
        this.setFilterProcessesUrl("/api/login");
        ProviderManager providerManager = new ProviderManager();
    }

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
            this.username = jsonData.getString("username");
            this.password = jsonData.getString("password");
        } catch (IOException e) {
            throw new IllegalArgumentException("json 형식 에러");
        }
    }
}
