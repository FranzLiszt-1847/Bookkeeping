package com.franzliszt.newbookkeeping.base;

public class UpdateBean {
    private Boolean isUpdate;

    public UpdateBean(Boolean isUpdate){
        this.isUpdate = isUpdate;
    }

    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }

}
