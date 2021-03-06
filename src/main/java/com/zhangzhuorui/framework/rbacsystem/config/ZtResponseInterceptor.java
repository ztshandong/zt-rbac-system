// package com.zhangzhuorui.framework.rbacsystem.config;
//
// import org.springframework.stereotype.Component;
//
// import javax.servlet.Filter;
// import javax.servlet.FilterChain;
// import javax.servlet.FilterConfig;
// import javax.servlet.ServletException;
// import javax.servlet.ServletRequest;
// import javax.servlet.ServletResponse;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
//
// @Component
// public class ZtResponseInterceptor implements Filter {
//
//     @Override
//     public void init(FilterConfig filterConfig) throws ServletException {
//
//     }
//
//     @Override
//     public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//
//         HttpServletResponse response = (HttpServletResponse) res;
//
//         response.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) req).getHeader("Origin"));
//         response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//         response.setHeader("Access-Control-Max-Age", "3600");
// //        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept ,token, accessToken");
//         response.addHeader("Access-Control-Allow-Headers", "*");
//         response.setHeader("Content-Type", "application/json;charset=UTF-8");
//         response.setHeader("Access-Control-Allow-Credentials", "true");
//
//         chain.doFilter(req, res);
//     }
//
//     @Override
//     public void destroy() {
//
//     }
// }
