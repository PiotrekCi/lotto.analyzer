package com.example.lotto.analyzer.externalApi.Entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DrawData {
    private int totalRows;
    private List<DrawResultItem> items;
    private Object meta;
    private int code;
}
