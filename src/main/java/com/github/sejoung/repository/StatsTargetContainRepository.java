package com.github.sejoung.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.sejoung.model.mongodb.StatsTargetContain;

@Repository
public interface StatsTargetContainRepository extends MongoRepository<StatsTargetContain, String> {

    @Query("{\r\n" + 
            "        targetType: \"iKc\",\r\n" + 
            "        targetData: {\r\n" + 
            "            $in: [\"387\", \"389\"]\r\n" + 
            "        }\r\n" + 
            "    }")
    public List<StatsTargetContain> findAll();


}
