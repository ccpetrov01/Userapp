package ccpetrov01.userapplication.Security;

import ccpetrov01.userapplication.Users.UserEntity;
import ccpetrov01.userapplication.Users.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String path = request.getServletPath();
        if (path.equals("/user/login") || path.equals("/user/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity user = userRepository.findByEmail(email).orElse(null);

            if (user != null && jwtService.isTokenValid(token, user)) {
                String roleName = "ROLE_" + user.getRole().name();
                System.out.println("Assigning authority: " + roleName);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority(roleName))
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                    auth.getAuthorities().forEach(authority ->
                            System.out.println("Role in security context: " + authority.getAuthority()));
                }
            }

        }

        filterChain.doFilter(request, response);
    }


}
