package com.icia.recipe.management.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Paging {
    private int totalNum;
    private int pageNum;
    private int listCnt;
    private int pageCnt;
    private String listUrl;
}

