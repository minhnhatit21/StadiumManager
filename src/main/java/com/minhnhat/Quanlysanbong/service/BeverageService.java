package com.minhnhat.Quanlysanbong.service;

import com.minhnhat.Quanlysanbong.model.ResponseDataModel;
import com.minhnhat.Quanlysanbong.models.Beverage;
import com.minhnhat.Quanlysanbong.repository.BeverageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BeverageService {

    private final BeverageRepository beverageRepository;

    @Autowired
    public BeverageService(BeverageRepository beverageRepository) {
        this.beverageRepository = beverageRepository;
    }

    public ResponseDataModel add(Beverage beverage) {
        String responseMsg = "";
        ResponseDataModel dataModel = new ResponseDataModel();
        try {
            if (beverageRepository.findBeverageName(beverage.getName()) != null) {
                responseMsg = "Beverage is duplicated";
            } else {
                Beverage newBeverage = new Beverage();
                newBeverage.setName(beverage.getName());
                newBeverage.setPrice(beverage.getPrice());
                newBeverage.setQuantity(beverage.getQuantity());
                beverageRepository.saveAndFlush(newBeverage);
                responseMsg = "Beverage is added successfully";
                dataModel.setData(newBeverage);
            }
        } catch (Exception e) {
            responseMsg = "Error when adding drinks";
        }
        dataModel.setResponseMsg(responseMsg);
        return dataModel;
    };

    @Transactional
    public ResponseDataModel update(Beverage beverage) {
        String responseMsg = "";
        ResponseDataModel dataModel = new ResponseDataModel();
        try {
            Beverage exitingBeverage = beverageRepository.findBeverageById(beverage.getId());
            if (exitingBeverage == null) {
                responseMsg = "Can't not find drinks";
            } else {
                exitingBeverage.setPrice(beverage.getPrice());
                exitingBeverage.setQuantity(beverage.getQuantity());
                beverageRepository.saveAndFlush(exitingBeverage);
                responseMsg = "Beverage is update successfully";
                dataModel.setData(exitingBeverage);
            }
        } catch (Exception e) {
            responseMsg = "Error when update drinks";
        }
        dataModel.setResponseMsg(responseMsg);
        return dataModel;
    }

    public ResponseDataModel delete(Long beverageId) {
        String responseMsg = "";
        ResponseDataModel dataModel = new ResponseDataModel();
        Beverage exitingBeverage = beverageRepository.findBeverageById(beverageId);
        try {
            if (exitingBeverage != null) {
                beverageRepository.deleteById(beverageId);
                beverageRepository.flush();
                responseMsg = "Delete successfully";
                dataModel.setData(exitingBeverage);
            }
        } catch(Exception e) {
            responseMsg = "Error when deleting beverage";
        }
        dataModel.setResponseMsg(responseMsg);
        return dataModel;
    }

    public ResponseDataModel findBeverageByID(Long beverageID) {

        String responseMsg = "";
        ResponseDataModel dataModel = new ResponseDataModel();
        try {
            if(beverageRepository.findBeverageById(beverageID) != null) {
                responseMsg = "Find beverage successfully";
                dataModel.setData(beverageRepository.findBeverageById(beverageID));
            } else {
                responseMsg = "Can't not find with ID:" + beverageID;
            }
        } catch (Exception e) {
            responseMsg = "Error when find beverage";
        }
        dataModel.setResponseMsg(responseMsg);
        return dataModel;
    }

    public List<Beverage> findAll() {
        return beverageRepository.findAll(Sort.by(Sort.Direction.ASC, "Id"));
    }

}
