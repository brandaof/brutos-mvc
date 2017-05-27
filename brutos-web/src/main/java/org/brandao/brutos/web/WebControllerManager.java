/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManagerImp;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.web.mapping.WebController;
import org.brandao.brutos.web.util.WebUtil;

/**
 * 
 * @author Brandao
 */
public class WebControllerManager extends ControllerManagerImp{
 
    public WebControllerManager(){
        super();
    }
    
	protected Controller creatControllerInstance(){
		return new WebController(this.applicationContext);
	}
    
    public ControllerBuilder addController( String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class<?> classType, String actionId ){
            return addController( id, view, resolvedView,
                    dispatcherType, name, classType, actionId, 
                    WebActionType.HIERARCHY);
    }
    
    public ControllerBuilder addController( String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType, String name, 
            Class<?> classType, 
            String actionId, ActionType actionType ){

    	if(actionType == null){
    		actionType = WebActionType.HIERARCHY;
    	}
    	else{
    		actionType = WebActionType.valueOf(actionType.id());
    	}
    	
    	if(!actionType.isValidControllerId(id))
    		throw new MappingException("invalid controller id: " + id);
    	
        if(resolvedView && view != null)
            WebUtil.checkURI(view, true);
        
        ControllerBuilder builder =  
            super.addController( id, view,
                resolvedView,
                dispatcherType, name, classType, actionId, actionType );
        
        return new WebControllerBuilder(builder, this.internalUpdate);
    }
    
}
