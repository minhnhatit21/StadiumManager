package com.minhnhat.Quanlysanbong.service;

import com.minhnhat.Quanlysanbong.model.ResponseDataModel;
import com.minhnhat.Quanlysanbong.models.Beverage;
import com.minhnhat.Quanlysanbong.models.MaterialFacilities;
import com.minhnhat.Quanlysanbong.repository.MaterialFacilitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MaterialFacilitiesService {

    @Autowired
    MaterialFacilitiesRepository materialFacilitiesRepository;
    public ResponseDataModel add(MaterialFacilities materialFacilities) {
        String responseMsg = "";
        ResponseDataModel dataModel = new ResponseDataModel();
        try {
            if (materialFacilitiesRepository.findMaterialFacilitiesName(materialFacilities.getNameMaterialFacilities()) != null) {
                responseMsg = "MaterialFacilities is duplicated";
            } else {
                MaterialFacilities newMaterialFacilities = new MaterialFacilities();
                newMaterialFacilities.setNameMaterialFacilities(materialFacilities.getNameMaterialFacilities());
                newMaterialFacilities.setQuantity(materialFacilities.getQuantity());
                newMaterialFacilities.setStatusMaterialFacilities(materialFacilities.getStatusMaterialFacilities());
                responseMsg = "MaterialFacilities is added successfully";
                dataModel.setData(newMaterialFacilities);
            }
        } catch (Exception e) {
            responseMsg = "Error when adding Material Facilities";
        }
        dataModel.setResponseMsg(responseMsg);
        return dataModel;
    };

    @Transactional
    public ResponseDataModel update(MaterialFacilities material) {
        String responseMsg = "";
        ResponseDataModel dataModel = new ResponseDataModel();
        try {
            MaterialFacilities exitingMaterialFacilities = materialFacilitiesRepository.findMaterialFacilitiesById(material.getId());
            if (exitingMaterialFacilities == null) {
                responseMsg = "Can't not find drinks";
            } else {
                exitingMaterialFacilities.setStatusMaterialFacilities(material.getStatusMaterialFacilities());
                exitingMaterialFacilities.setQuantity(material.getQuantity());
                materialFacilitiesRepository.saveAndFlush(exitingMaterialFacilities);
                responseMsg = "MaterialFacilities is update successfully";
                dataModel.setData(exitingMaterialFacilities);
            }
        } catch (Exception e) {
            responseMsg = "Error when update MaterialFacilities";
        }
        dataModel.setResponseMsg(responseMsg);
        return dataModel;
    }

    public ResponseDataModel delete(Long materialFacilitiesId) {
        String responseMsg = "";
        ResponseDataModel dataModel = new ResponseDataModel();
        MaterialFacilities exitingMaterialFacilities = materialFacilitiesRepository.findMaterialFacilitiesById(materialFacilitiesId);
        try {
            if (exitingMaterialFacilities != null) {
                materialFacilitiesRepository.deleteById(materialFacilitiesId);
                materialFacilitiesRepository.flush();
                responseMsg = "Delete successfully";
                dataModel.setData(exitingMaterialFacilities);
            }
        } catch(Exception e) {
            responseMsg = "Error when deleting Material Facilities";
        }
        dataModel.setResponseMsg(responseMsg);
        return dataModel;
    }

    public ResponseDataModel findBeverageByID(Long materialFacilitiesId) {

        String responseMsg = "";
        ResponseDataModel dataModel = new ResponseDataModel();
        try {
            if(materialFacilitiesRepository.findMaterialFacilitiesById(materialFacilitiesId) != null) {
                responseMsg = "Find material facilities successfully";
                dataModel.setData(materialFacilitiesRepository.findMaterialFacilitiesById(materialFacilitiesId));
            } else {
                responseMsg = "Can't not find with ID:" + materialFacilitiesId;
            }
        } catch (Exception e) {
            responseMsg = "Error when find material facilities";
        }
        dataModel.setResponseMsg(responseMsg);
        return dataModel;
    }

    public List<MaterialFacilities> findAll() {
        return materialFacilitiesRepository.findAll(Sort.by(Sort.Direction.ASC, "Id"));
    }
}
