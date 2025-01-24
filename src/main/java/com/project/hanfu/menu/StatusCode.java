package com.project.hanfu.menu;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusCode {
    /**
     * 成功
     */
    SUCCESS(1000),
    /**
     * 错误
     */
    ERROR(1001);

    private int i;

    private StatusCode(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return this.i + "";
    }

    /**
     * 获取 Integer 型 enum
     * @return
     */
    @JsonValue
    public Integer toInteger() {
        return this.i;
    }


    @JsonCreator
    public static StatusCode create(String name) {
        try {
            return StatusCode.valueOf(name);
        } catch (IllegalArgumentException e) {
            int code = Integer.parseInt(name);
            for (StatusCode value : StatusCode.values()) {
                if (value.i == code) {
                    return value;
                }
            }
        }
        throw new IllegalArgumentException("No element matches " + name);
    }
}
