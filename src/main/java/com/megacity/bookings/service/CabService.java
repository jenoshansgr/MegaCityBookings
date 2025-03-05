package com.megacity.bookings.service;

import com.megacity.bookings.dao.CabDAO;
import com.megacity.bookings.dao.CabTypeDAO;
import com.megacity.bookings.model.Cab;
import com.megacity.bookings.model.CabType;

import java.util.List;


// User Service
public class CabService {
    private final CabDAO cabDAO;
    private final CabTypeDAO cabTypeDAO;

    public CabService() {
        this.cabDAO = new CabDAO();
        this.cabTypeDAO = new CabTypeDAO();
    }

    public List<Cab> getCabList() {
        List<Cab> cabList = this.cabDAO.selectAllCabs();

        for (Cab cab : cabList) {
            CabType cabType = this.cabTypeDAO.getCabTypeById(cab.getCabTypeId());
            cab.setCabType(cabType.getName());
            cab.setPricePerDay(cabType.getPricePerDay());
            cab.setPricePerKm(cabType.getPricePerKm());
        }

        return cabList;
    }

    public List<CabType> getCabTypeList() {
        return this.cabTypeDAO.selectAllCabTypes();
    }

    public int insert(Cab cab) throws Exception {
        int cabId = this.cabDAO.saveCab(cab);

        if (cabId > 0) {
            return cabId;
        }

        throw new Exception("Failed to save cab");
    }

    public Cab getCabById(int id) {
        Cab cab = this.cabDAO.getCabById(id);

        if (cab == null) {
            return new Cab();
        }

        return cab;
    }

    public boolean deleteCabById(int id) {
        return this.cabDAO.deleteById(id);
    }

    public boolean updateCab(Cab cab) {
        return this.cabDAO.updateCab(cab);
    }
}
