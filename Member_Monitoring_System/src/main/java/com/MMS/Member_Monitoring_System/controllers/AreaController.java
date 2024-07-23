package com.MMS.Member_Monitoring_System.controllers;

import com.MMS.Member_Monitoring_System.models.Area;
import com.MMS.Member_Monitoring_System.services.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class AreaController {

    @Autowired
    private AreaRepository repo;


    @GetMapping("/area_details")
    public String showAreaDetails(Model model) {
        List<Area> areas = repo.findAll();
        model.addAttribute("areas", areas);
        return "areas/area_details";
    }

    @GetMapping("/create")
    public String showCreateAreaForm(Model model) {
        model.addAttribute("area", new Area());
        return "areas/area_registration";
    }

    @PostMapping("/create")
    public String createArea(@ModelAttribute Area area) {
        repo.save(area);
        return "redirect:/dashboard/area_details";
    }

    @GetMapping("/edit/{id}")
    public String showEditAreaForm(@PathVariable("id") int id, Model model) {
        Area area = repo.findById(id).orElse(null);
        model.addAttribute("area", area);
        return "areas/area_edit";
    }

    @PostMapping("/edit/{id}")
    public String updateArea(@PathVariable("id") int id, @ModelAttribute Area area) {
        area.setAreaNumber((long) id);
        repo.save(area);
        return "redirect:/dashboard/area_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteArea(@PathVariable("id") int id) {
        repo.deleteById(id);
        return "redirect:/dashboard/area_details";
    }
}
