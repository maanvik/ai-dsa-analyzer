package com.ai.dsa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "problems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    @Id
    private String id;
    private String title;
    private String category;
    private String description;
    private String difficulty;

    public Problem(String title, String category, String description, String difficulty) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.difficulty = difficulty;
    }
}
