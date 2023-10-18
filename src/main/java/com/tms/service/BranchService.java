package com.tms.service;

import com.tms.model.Branch;
import com.tms.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//defining the business logic
@Service
@RequiredArgsConstructor
public class BranchService
{
    @Autowired
    BranchRepository branchRepository;
    //getting all branch records
    public List<Branch> getAllBranch()
    {
        List<Branch> branchs = new ArrayList<Branch>();
        branchRepository.findAll().forEach(branch -> branchs.add(branch));
        return branchs;
    }
    //getting a specific record
    public Branch getBranchById(String id)
    {
        return branchRepository.findById(id).get();
    }

    // id not exists. repo save creates obj
    public void save(Branch branch)
    {
        branchRepository.save(branch);
    }

    // id exists. repo save updates obj
    public void update(Branch branch)
    {
        branchRepository.save(branch);
    }

    //deleting a specific record
    public void delete(String id)
    {
        branchRepository.deleteById(id);
    }
}