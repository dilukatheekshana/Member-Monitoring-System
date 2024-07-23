package com.MMS.Member_Monitoring_System.controllers;

import com.MMS.Member_Monitoring_System.models.Area;
import com.MMS.Member_Monitoring_System.models.Member;
import com.MMS.Member_Monitoring_System.models.MemberFee;
import com.MMS.Member_Monitoring_System.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberFeeRepository memberFeeRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberFeeService memberFeeService;

    @Autowired
    private AreaService areaService;



    @GetMapping("/member_details")
    public String showMemberDetails(Model model) {
        List<Member> members = memberService.getAllMembers();
        Map<String, List<MemberFee>> memberFees = memberFeeService.getAllMemberFees()
                .stream()
                .collect(Collectors.groupingBy(fee -> fee.getMember().getMemberNo()));
        model.addAttribute("members", members);
        model.addAttribute("memberFees", memberFees);
        return "members/member_details";
    }


    @GetMapping("/member_create")
    public String showMemberRegistrationForm(Model model) {
        List<Area> areas = areaService.getAllAreas(); // Fetch all Area objects
        model.addAttribute("areas", areas); // Pass Area objects to the view
        model.addAttribute("member", new Member());
        return "members/member_registration";
    }

    @PostMapping("/member_create")
    public String createMember(@RequestParam("memberNo") String memberNo,
                               @RequestParam("name") String name,
                               @RequestParam("address1") String address1,
                               @RequestParam("address2") String address2,
                               @RequestParam("address3") String address3,
                               @RequestParam("street") String street,
                               @RequestParam("dob") String dob,
                               @RequestParam("doj") String doj,
                               @RequestParam("tel") String tel,
                               @RequestParam("whatsapp") String whatsapp,
                               @RequestParam("email") String email,
                               @RequestParam("status") String status,
                               @RequestParam("areaId") Long areaId,
                               @RequestParam("amount") String amount,
                               @RequestParam("yearMonth") String yearMonth,
                               @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        Member member = new Member();
        member.setMemberNo(memberNo);
        member.setName(name);
        member.setAddress1(address1);
        member.setAddress2(address2);
        member.setAddress3(address3);
        member.setStreet(street);
        member.setDob(dob);
        member.setDoj(doj);
        member.setTel(tel);
        member.setWhatsapp(whatsapp);
        member.setEmail(email);
        member.setStatus(status.equals("A") ? "A" : "I");

        // save image file if present

        if (image != null && !image.isEmpty()) {
            String storageFileName = image.getOriginalFilename();
            try {
                String uploadDir = "public/images/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                member.setImageFileName(storageFileName);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
        } else {
            member.setImageFileName(null);
        }

        Area area = areaRepository.findById(Math.toIntExact(areaId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid area ID"));
        member.setArea(area);

        memberRepository.save(member);

        MemberFee memberFee = new MemberFee();
        memberFee.setMember(member);
        memberFee.setYearMonth(yearMonth);
        memberFee.setAmount(amount);

        memberFeeRepository.save(memberFee);

        return "redirect:/dashboard/member_details";
    }

    @GetMapping("/member_edit/{id}")
    public String showEditMemberForm(@PathVariable("id") String id, Model model) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // Fetch associated MemberFees
        List<MemberFee> memberFees = memberFeeRepository.findByMember(member);

        List<Area> areas = areaService.getAllAreas();
        model.addAttribute("member", member);
        model.addAttribute("memberFees", memberFees); // Add fees to the model
        model.addAttribute("areas", areas);
        return "members/member_edit";
    }

    @PostMapping("/member_edit/{id}")
    public String updateMember(@PathVariable("id") String id,
                               @RequestParam("memberNo") String memberNo,
                               @RequestParam("name") String name,
                               @RequestParam("address1") String address1,
                               @RequestParam("address2") String address2,
                               @RequestParam("address3") String address3,
                               @RequestParam("street") String street,
                               @RequestParam("dob") String dob,
                               @RequestParam("doj") String doj,
                               @RequestParam("tel") String tel,
                               @RequestParam("whatsapp") String whatsapp,
                               @RequestParam("email") String email,
                               @RequestParam("status") String status,
                               @RequestParam("areaId") Long areaId,
                               @RequestParam(value = "image", required = false) MultipartFile image,
                               @RequestParam("yearMonth") List<String> yearMonths,
                               @RequestParam("amount") List<String> amounts,
                               @RequestParam("removeFlags") List<String> removeFlags) throws IOException {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        member.setMemberNo(memberNo);
        member.setName(name);
        member.setAddress1(address1);
        member.setAddress2(address2);
        member.setAddress3(address3);
        member.setStreet(street);
        member.setDob(dob);
        member.setDoj(doj);
        member.setTel(tel);
        member.setWhatsapp(whatsapp);
        member.setEmail(email);
        member.setStatus(status.equals("A") ? "A" : "I");

        // Save image file if present
        if (image != null && !image.isEmpty()) {
            String storageFileName = image.getOriginalFilename();
            try {
                String uploadDir = "public/images/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                member.setImageFileName(storageFileName);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
        }

        Area area = areaRepository.findById(Math.toIntExact(areaId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid area ID"));
        member.setArea(area);

        memberRepository.save(member);

        // Handle MemberFees
        List<MemberFee> existingFees = memberFeeRepository.findByMember(member);
        for (int i = 0; i < yearMonths.size(); i++) {
            String yearMonth = yearMonths.get(i);
            String amount = amounts.get(i);
            boolean remove = removeFlags.get(i).equals("1");

            // Find if this fee already exists
            MemberFee existingFee = existingFees.stream()
                    .filter(fee -> fee.getYearMonth().equals(yearMonth))
                    .findFirst()
                    .orElse(null);

            if (remove && existingFee != null) {
                // Remove fee if marked for removal
                memberFeeRepository.delete(existingFee);
            } else if (!remove) {
                // Update or create new fee
                if (existingFee == null) {
                    existingFee = new MemberFee();
                    existingFee.setMember(member);
                }
                existingFee.setYearMonth(yearMonth);
                existingFee.setAmount(amount);
                memberFeeRepository.save(existingFee);
            }
        }

        return "redirect:/dashboard/member_details";
    }



    @GetMapping("/member_delete/{id}")
    public String deleteMember(@PathVariable("id") String id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // Delete associated member fees
        List<MemberFee> memberFees = memberFeeRepository.findByMember(member);
        memberFeeRepository.deleteAll(memberFees);

        // Delete the member
        memberRepository.delete(member);

        return "redirect:/dashboard/member_details";
    }

}
