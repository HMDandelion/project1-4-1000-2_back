package com.hmdandelion.project_1410002.production.domain.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DefectStatusType {
    PROCESSING_START("처리 시작"),
    PROCESSING_END("처리 완료");


    private final String displayName ;

    private DefectStatusType (String displayName){
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName(){
        return displayName;
    }
    }

