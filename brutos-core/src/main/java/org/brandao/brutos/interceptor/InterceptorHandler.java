/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it 
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later 
 * version.
 * You may obtain a copy of the License at
 * 
 * http://www.gnu.org/licenses/gpl.html 
 * 
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied.
 *
 */

package org.brandao.brutos.interceptor;

import org.brandao.brutos.ResourceMethod;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Afonso Brandao
 */
public interface InterceptorHandler {

    /**
     * @deprecated
     * @return
     */
    public ServletContext getContext();
    
    /**
     * @deprecated
     * @return
     */
    public HttpServletRequest getRequest();
    
    /**
     * @deprecated
     * @return
     */
    public HttpServletResponse getResponse();
    
    /**
     * @deprecated
     * @return
     */
    public String getURI();
    
    public ResourceMethod getResourceMethod();
    
    public Object getResource();

    public String view();

    
}
