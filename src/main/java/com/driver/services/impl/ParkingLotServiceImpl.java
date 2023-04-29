package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository;
    @Autowired
    SpotRepository spotRepository;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot(name,address);
        parkingLotRepository.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).get();
        SpotType spotType = SpotType.OTHERS;

        if(numberOfWheels<=2)
            spotType = SpotType.TWO_WHEELER;
        else if(numberOfWheels<=4)
            spotType = SpotType.FOUR_WHEELER;

        Spot spot = new Spot(spotType,pricePerHour,false);
        parkingLotRepository.save(parkingLot);

        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {

        spotRepository.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).get();
        Spot spot = null;
        List<Spot> spotList = parkingLot.getSpotList();
        parkingLotRepository.save(parkingLot);
        spotRepository.save(spot);

        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {

        parkingLotRepository.deleteById(parkingLotId);
    }
}
