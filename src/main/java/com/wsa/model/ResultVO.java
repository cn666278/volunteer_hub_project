package com.wsa.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO implements Serializable {
    private static final long serialVersionUID = -161362897795933188L;
    private Integer code;
    private Object data;
}
