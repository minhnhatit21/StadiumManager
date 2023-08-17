package com.minhnhat.Quanlysanbong.controller;

import com.minhnhat.Quanlysanbong.model.ResponseDataModel;
import com.minhnhat.Quanlysanbong.models.Beverage;
import com.minhnhat.Quanlysanbong.models.Stadium;
import com.minhnhat.Quanlysanbong.service.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beverage")
public class BeverageController {

    @Autowired
    BeverageService beverageService;

    @GetMapping("/getAll")
    @ResponseBody
    public ResponseEntity<List<Beverage>> getBeverage() {
        return ResponseEntity.ok(beverageService.findAll());
    }

    @GetMapping("findById/{beverageId}")
    @ResponseBody
    public ResponseEntity<ResponseDataModel> findBeverageByID(@PathVariable("beverageId") Long beverageId) {
        return ResponseEntity.ok(beverageService.findBeverageByID(beverageId));
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<ResponseDataModel> addNewBeverage(@RequestBody Beverage beverage) {
        return ResponseEntity.ok(beverageService.add(beverage));
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<ResponseDataModel> updateNewBeverage(@RequestBody Beverage beverage) {
        return ResponseEntity.ok(beverageService.update(beverage));
    }

    @DeleteMapping(value = {"delete/{beverageId}"})
    @ResponseBody
    public ResponseEntity<ResponseDataModel> delete(@PathVariable("beverageId") Long beverageId) {
        return ResponseEntity.ok(beverageService.delete(beverageId));
    }
}
