package com.javadeveloper.computershare.controller;

import com.javadeveloper.computershare.service.CompanyDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StockPriceController {
    private final CompanyDetailService companyDetailService;

    @GetMapping(value = "/lastWeekStockPrice", produces = MediaType.APPLICATION_JSON_VALUE)
    public String weeklyStockPriceByCompanyName(@RequestParam String companyName) {
        return companyDetailService.weeklyStockPriceByCompanyName(companyName);
    }
}
