package com.acg12.entity.event;

public class CommonEvent {

    private Object object;
    private CommonEnum commonEvent = CommonEnum.COMMON_DEF;

    public CommonEvent(CommonEnum commonEvent){
        this.commonEvent = commonEvent;
    }

    public CommonEvent(CommonEnum commonEvent , Object object){
        this.commonEvent = commonEvent;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public CommonEnum getCommonEvent() {
        return commonEvent;
    }

    public void setCommonEvent(CommonEnum commonEvent) {
        this.commonEvent = commonEvent;
    }
}
