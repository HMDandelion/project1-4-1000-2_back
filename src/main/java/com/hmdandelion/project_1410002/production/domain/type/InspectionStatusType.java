package com.hmdandelion.project_1410002.production.domain.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InspectionStatusType {
    PASSED("검수 완료"),
    FAILED("검수 중지"),
    BEFORE("검수 전");

    private final String displayName ;
    private InspectionStatusType (String displayName){
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName(){
        return displayName;
    }
    }


