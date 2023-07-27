package com.tailtales.production.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse<T> {
    private int page;
    private int total;
    private T ObjectList;
}
