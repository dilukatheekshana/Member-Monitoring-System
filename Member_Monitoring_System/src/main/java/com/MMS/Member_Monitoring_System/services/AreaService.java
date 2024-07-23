package com.MMS.Member_Monitoring_System.services;

import com.MMS.Member_Monitoring_System.models.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    public List<Area> getAllAreas() {
        return areaRepository.findAll();
    }

    public Area getAreaById(int areaId) {
        Optional<Area> area = areaRepository.findById(areaId);
        return area.orElse(null);
    }
}
