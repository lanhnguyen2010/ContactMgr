//package vn.kms.launch.contactmgr;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.web.filter.GenericFilterBean;
//
//public class AuthenticationFilter extends GenericFilterBean {
//
//    private AuthenticationManager authenticationManager;
//    public AuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager=authenticationManager;
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response,
//            FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = asHttp(request);
//        HttpServletResponse httpResponse = asHttp(response);
//        System.out.print("chay..");
//        
//    }
//    private HttpServletRequest asHttp(ServletRequest request) {
//        return (HttpServletRequest) request;
//    }
//
//    private HttpServletResponse asHttp(ServletResponse response) {
//        return (HttpServletResponse) response;
//    }
//
//}
