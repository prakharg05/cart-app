package com.shopper.controller;

import com.shopper.dao.Inventory;
import com.shopper.dto.InventoryDTO;
import com.shopper.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class DemoController {

    @RequestMapping("/ping")
    List<String> getDemoResponse() {
        return Arrays.asList("Hello", "High");
    }

}
