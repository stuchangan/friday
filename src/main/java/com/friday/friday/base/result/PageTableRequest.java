package com.friday.friday.base.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageTableRequest implements Serializable {
    private Integer page;
    private Integer limit;
    private Integer offset;

    public void countOffSet(){
        if (null == this.page || null == this.limit){
            this.offset = 0;
            return;
        }else {
            this.offset = (this.page - 1) * limit;
        }
    }
}
