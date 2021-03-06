package org.brandao.webchat.controller.type;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.type.StringType;
import org.brandao.webchat.model.RoomService;
import org.brandao.webchat.model.RoomServiceFactory;

/**
 *
 * @author Brandao
 */
public class RoomServiceType extends StringType{
    
    @Override
    public Object convert(Object value){
        
        String roomID = (String) super.convert(value);
        
        ApplicationContext context = 
            Invoker.getCurrentApplicationContext();
        
        RoomServiceFactory roomServiceFactory = 
                (RoomServiceFactory) context.getBean(RoomServiceFactory.class);
        
        return roomServiceFactory.getRoomService(roomID);
    }
    
    @Override
    public Class getClassType(){
        return RoomService.class;
    }
}
