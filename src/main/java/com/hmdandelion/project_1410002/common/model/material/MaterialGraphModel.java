package com.hmdandelion.project_1410002.common.model.material;

import lombok.Getter;

import java.io.Serializable;
@Getter
public class MaterialGraphModel implements Serializable {
    private String nameValue;
    private int subject;
    private int compare;

    public MaterialGraphModel(String nameValue, int subject, int compare) {
        this.nameValue = nameValue;
        this.subject = subject;
        this.compare = compare;
    }
}
