package com.hmdandelion.project_1410002.production.domain.type;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.processing.Completion;

public enum LineStatusType {
    ACTIVE("가동 가능"),
    INACTIVE("가동 불가");

    private final String displayName ;
    private LineStatusType (String displayName){
        this.displayName = displayName;
    }
    @JsonValue
    public String getDisplayName(){
        return displayName;
    }
    }

