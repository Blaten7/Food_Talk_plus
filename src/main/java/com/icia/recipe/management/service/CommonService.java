package com.icia.recipe.management.service;

import com.icia.recipe.management.dao.BoardDao;
import com.icia.recipe.management.dao.InvenDao;
import com.icia.recipe.management.dto.FoodItemDto;
import com.icia.recipe.management.dto.InvenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommonService {

    @Autowired
    InvenDao iDao;

    @Autowired
    BoardDao bDao;

}
