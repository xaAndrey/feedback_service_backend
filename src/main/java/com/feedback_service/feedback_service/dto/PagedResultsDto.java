package com.feedback_service.feedback_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResultsDto <T>{
    private List<T> result;
    private Integer count;

    public PagedResultsDto(List<T> result, Integer count) {
        this.result = result;
        this.count = count;
    }
}
