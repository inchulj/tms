package com.tms.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.tms.model.Branch;
import com.tms.model.request.BranchRequest;
import com.tms.model.Tariff;
import com.tms.repository.BranchRepository;
import com.tms.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/tourism/api/v1")
@RequiredArgsConstructor

////creating RestController
//@RestController
////     /tourism/api/v1/branch/add-places Adds a new company
////        /tourism/api/v1/branch/update-tariff/{companyID} Update the tariff details for the given place and respective company
////        URL Exposed Purpose
////        /tourism/api/v1/admin/{criteria}/{criteriaValue} Searches for places based on provided criteria
//@RequestMapping("/tourism/api/v1")
public class BranchController
{
    static final List<String> places = Arrays.asList("ANDAMAN", "THAILAND", "DUBAI", "SINGAPORE", "MALAYSIA");

    //autowired the BranchService class
    @Autowired
    BranchService branchService;
    @Autowired
    BranchRepository branchRepository;
//    @Autowired
//    TariffRepository tariffRepository;

    @GetMapping("/branch")
    public List<Branch> getAllBranch()
    {
        return branchService.getAllBranch();
    }

    @GetMapping("/admin/branchId/{branchID}")
    private String getBranch(@PathVariable("branchID") String branchID)
    {
        Branch branch = branchService.getBranchById(branchID);
        if (branch == null) {
            System.out.println("Branches not found");
            return null;
        }

        Map<String, Integer> tariffs = branch.getTariff().getTariffList();

        List<Map.Entry<String, Integer>> tariffReversed = tariffs.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toList());
        return branch.toString() + "\n" + tariffReversed.toString();
    }

    @GetMapping("/admin/branchName/{branchName}")
    private String getBranchByBranchName(@PathVariable("branchName") String branchName)
    {
        List<Branch> branches = branchRepository.findByName(branchName);
        if (branches.size() > 1) {
            System.out.println("Multiple branches found");
            return null;
        }
        if (branches == null || branches.isEmpty()) {
            System.out.println("Branches not found");
            return null;
        }

        Branch branch = branches.get(0);
        Map<String, Integer> tariffs = branch.getTariff().getTariffList();


        List<Map.Entry<String, Integer>> tariffReversed = tariffs.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toList());
        return branch.toString() + "\n" + tariffReversed.toString();
    }

    static private void validateRequestData(BranchRequest branch) throws Exception {
        Map<String, Integer> tariff = branch.getTariffReq();
        Iterator iterator = tariff.entrySet().iterator();

        // sort static place list
        List<String> placesSorted = places.stream().sorted().collect(Collectors.toList());
        // sort input place list
        List<String> tariffKeySorted = (new ArrayList<String>(tariff.keySet())).
                stream().map(k->k.toUpperCase()).sorted().collect(Collectors.toList());
        // check if both place list match
        if (!placesSorted.equals(tariffKeySorted)) {
            throw new Exception();
        }
        // check values for legitimacy
        List<Integer> tariffValuesOutOfRange = tariff.values().
                stream().filter(v->(v<50000 || v>100000)).collect(Collectors.toList());
        if (!tariffValuesOutOfRange.isEmpty()) {
            throw new Exception();
        }
    }

    @PostMapping("/branch/add-places")
    public String saveBranch(@RequestBody BranchRequest branch) throws Exception
    {
        validateRequestData(branch);

        Map<String, Integer> tariffs = branch.getTariffReq();

        Tariff tf = new Tariff(branch.getTariffReq());
//        Tariff saved = tariffRepository.save(tf);

        Branch nb = new Branch(branch.getName(),
                branch.getContact(), branch.getWebsite(), branch.getEmail(),
                tf, LocalDateTime.now().toString());
        branchService.save(nb);

        return nb.getId();
    }

    @PostMapping("/branch/update-tariff/{branchID}")
    public String saveBranch(@PathVariable("branchID") String branchID,
                          @RequestBody Map<String, Object> tariffReq) {
        Branch nb = branchService.getBranchById(branchID);
        if (nb == null) {
            System.out.println("Branches not found");
            return null;
        }

        Map<String, Integer> tariffs = nb.getTariff().getTariffList();

        String place = (String) tariffReq.get("place");
        int tariff = (int) (tariffReq.get("tariff"));
        if (!places.contains(place) ||
                (tariff < 50000 || tariff > 100000)) {
            System.out.println("tariff key/value is wrong");
            return null;
        }

        tariffs.put(place, tariff);
        Tariff tf = new Tariff(tariffs);
//        Tariff saved = tariffRepository.save(tf);
        nb.setTariff(tf);
        nb.setDateTime(LocalDateTime.now().toString());
        branchService.update(nb);
        return branchID;
    }
}
