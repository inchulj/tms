package com.tms.model.request;

import lombok.Data;

import java.util.Map;

@Data
public class BranchRequest {
    private String name;
    private String website;
    private String contact;
    private String email;
    private Map<String, Integer> tariffReq;
}
