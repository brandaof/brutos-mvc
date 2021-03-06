package org.brandao.webchat.controller.type;

import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeFactory;
import org.brandao.webchat.model.RoomService;

/**
 *
 * @author Brandao
 */
public class RoomServiceTypeFactory 
    implements TypeFactory{

    @Override
    public Type getInstance() {
        return new RoomServiceType();
    }

    @Override
    public Class getClassType() {
        return RoomService.class;
    }

    @Override
    public boolean matches(Class type) {
        return RoomService.class.isAssignableFrom(type);
    }
    
}
