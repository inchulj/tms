package com.tms.repository;

import com.tms.model.Branch;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface BranchRepository extends CrudRepository<Branch, String> {
    List<Branch> findByName(String name);
}

//@Repository
//public class BranchRepository {
//
//    @Autowired
//    private DynamoDBMapper mapper;
//
//
//    public Branch addBranch(Branch branch) {
//        mapper.save(branch);
//        return branch;
//    }
//
//    public Branch findBranchByBranchId(String branchId) {
//        return mapper.load(Branch.class, branchId);
//    }
//
//    public String deleteBranch(Branch branch) {
//        mapper.delete(branch);
//        return "branch removed !!";
//    }
//
//    public String editBranch(Branch branch) {
//        mapper.save(branch, buildExpression(branch));
//        return "record updated ...";
//    }
//
//    private DynamoDBSaveExpression buildExpression(Branch branch) {
//        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
//        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
//        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(branch.getId())));
//        dynamoDBSaveExpression.setExpected(expectedMap);
//        return dynamoDBSaveExpression;
//    }
//}