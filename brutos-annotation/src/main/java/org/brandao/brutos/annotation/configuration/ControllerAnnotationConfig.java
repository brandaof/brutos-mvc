/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.annotation.configuration;

import java.lang.reflect.Method;
import java.util.List;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Dispatcher;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotationImp;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.bean.BeanProperty;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Controller.class)
public class ControllerAnnotationConfig 
    extends AbstractAnnotationConfig{

    @Override
    public Object applyConfiguration(Object arg0, Object arg1,
            ConfigurableApplicationContext applicationContext){
        
        Class source = (Class)arg0;
        ControllerBuilder builder;
        
        Controller annotationController =
                (Controller) source.getAnnotation(Controller.class);

        
        String controllerID;
        String view;
        DispatcherType dispatcher;
        
        String name     = null;
        String actionID = null;

        if(annotationController != null){
            name = annotationController.name();
            actionID = annotationController.actionId();
        }

        controllerID = this.getControllerId(annotationController, source);
        
        view = getView((View) source.getAnnotation(View.class), source,
            applicationContext);

        Dispatcher dispatcherAnnotation = (Dispatcher) source.getAnnotation(Dispatcher.class);
        dispatcher = dispatcherAnnotation == null? 
                BrutosConstants.DEFAULT_DISPATCHERTYPE : 
                DispatcherType.valueOf(dispatcherAnnotation.value());
        
        builder =
                applicationContext.getControllerManager().addController(
                    controllerID,
                    view,
                    dispatcher,
                    name,
                    source,
                    actionID);

        addProperties(builder, applicationContext, source);
        addActions( builder, applicationContext, source );
        return builder;
    }
    
    protected String getView(View viewAnnotation, Class controllerClass,
        ConfigurableApplicationContext applicationContext){
        
        if(viewAnnotation != null)
            return viewAnnotation.value();
        else
            return createControllerView(controllerClass, applicationContext);
    }
    
    protected String createControllerView(Class controllerClass,
            ConfigurableApplicationContext applicationContext){
        
        return applicationContext.getViewResolver()
                .getView(controllerClass, null, 
                applicationContext.getConfiguration());
    }
    
    protected void addProperties(ControllerBuilder controllerBuilder, 
            ConfigurableApplicationContext applicationContext, Class clazz){
    
        BeanInstance instance = new BeanInstance(null,clazz);
        List props = instance.getProperties();
        for(int i=0;i<props.size();i++){
            BeanProperty prop = (BeanProperty) props.get(i);
            super.applyInternalConfiguration(new BeanPropertyAnnotationImp(prop), 
                    controllerBuilder, applicationContext);
        }
    }
    
    protected void addActions( ControllerBuilder controllerBuilder, 
            ConfigurableApplicationContext applicationContext, Class clazz ){
        
        Method[] methods = clazz.getMethods();

        for( Method m: methods ){
            super.applyInternalConfiguration(m, controllerBuilder, applicationContext);
        }
    }
    
    protected String getControllerId(Controller annotation, Class controllerClass){
        if(annotation != null)
            return annotation.id();
        else
            return null;
    }
    
    public boolean isApplicable(Object source) {
        return source instanceof Class && 
               (((Class)source).isAnnotationPresent( Controller.class ) ||
               ((Class)source).getSimpleName().endsWith("Controller"));
    }
    
}
