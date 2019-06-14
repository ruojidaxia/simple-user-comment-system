package com.fgj.comment.mybatisplus.enums;

public enum MyCodeEnum implements CodeEnum{
    HAO(10),
    HEN_HAO(20),
    FEI_HCANG_HAO(30);

    private final int code;

    MyCodeEnum(int code){
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }
}
