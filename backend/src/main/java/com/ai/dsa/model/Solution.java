package com.ai.dsa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.ai.dsa.entity.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "solutions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solution {
    @Id
    private String id;
    private String code;
    @DBRef
    private User user;
    @DBRef
    private Problem problem;
    private boolean isAccepted;

    public Solution(String code, User user, Problem problem, boolean isAccepted) {
        this.code = code;
        this.user = user;
        this.problem = problem;
        this.isAccepted = isAccepted;
    }
}
