package com.gs.pay.filter;

import com.gs.pay.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：
 * @ClassName ：payFilter
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/26 12:50
 */
public class payFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(payFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
