package com.minhnhat.Quanlysanbong.controller;

import com.minhnhat.Quanlysanbong.model.ResponseDataModel;
import com.minhnhat.Quanlysanbong.models.Beverage;
import com.minhnhat.Quanlysanbong.models.MaterialFacilities;
import com.minhnhat.Quanlysanbong.service.MaterialFacilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material")
public class MaterialFacilitiesController {

    @Autowired
    MaterialFacilitiesService materialFacilitiesService;
    @GetMapping("/getAll")
    @ResponseBody
    public ResponseEntity<List<MaterialFacilities>> getMaterialFacilities() {
        return ResponseEntity.ok(materialFacilitiesService.findAll());
    }

    @GetMapping("findById/{materialFacilitiesId}")
    @ResponseBody
    public ResponseEntity<ResponseDataModel> findMaterialFacilitiesByID(@PathVariable("materialFacilitiesId") Long materialFacilitiesId) {
        return ResponseEntity.ok(materialFacilitiesService.findMaterialFacilitiesByID(materialFacilitiesId));
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<ResponseDataModel> addNewMaterialFacilities(@RequestBody MaterialFacilities materialFacilities) {
        return ResponseEntity.ok(materialFacilitiesService.add(materialFacilities));
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<ResponseDataModel> updateNewBeverage(@RequestBody MaterialFacilities materialFacilities) {
        return ResponseEntity.ok(materialFacilitiesService.update(materialFacilities));
    }

    @DeleteMapping(value = {"delete/{materialFacilitiesId}"})
    @ResponseBody
    public ResponseEntity<ResponseDataModel> delete(@PathVariable("materialFacilitiesId") Long materialFacilitiesId) {
        return ResponseEntity.ok(materialFacilitiesService.delete(materialFacilitiesId));
    }
}
