package com.icia.recipe.service.manageService;

import com.icia.recipe.management.dao.BoardDao;
import com.icia.recipe.management.dao.InvenDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommonService {

    @Autowired
    InvenDao iDao;

    @Autowired
    BoardDao bDao;

}
