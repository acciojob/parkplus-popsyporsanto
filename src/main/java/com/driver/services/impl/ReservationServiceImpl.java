package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SpotRepository spotRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ParkingLotRepository parkingLotRepository;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {

        try {

            User user = userRepository.findById(userId).get();
            ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).get();

            if(user==null || parkingLot==null) {
                throw new Exception("Cannot make reservation");
            }

            List<Spot> spotList = parkingLot.getSpotList();

            Spot minPriceSpot = null;
            int minPrice = Integer.MAX_VALUE;



            if (minPriceSpot==null)
                throw new Exception("Cannot make reservation");

            Reservation reservation = new Reservation(timeInHours);
            reservation.setSpot(minPriceSpot);
            reservation.setUser(user);

            List<Reservation> reservationList = user.getReservationList();

            if (reservationList==null)
                reservationList = new ArrayList<>();

            reservationList.add(reservation);

            user.setReservationList(reservationList);

            List<Reservation> SpotReservationList = minPriceSpot.getReservationList();

            minPriceSpot.setOccupied(true);

            userRepository.save(user);
            spotRepository.save(minPriceSpot);

            return reservation;
        }
        catch (Exception e){
            return null;
        }
    }

}