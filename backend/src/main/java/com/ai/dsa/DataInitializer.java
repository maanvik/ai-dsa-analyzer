package com.ai.dsa;

import com.ai.dsa.model.Problem;
import com.ai.dsa.model.Solution;
import com.ai.dsa.entity.User;
import com.ai.dsa.repository.ProblemRepository;
import com.ai.dsa.repository.SolutionRepository;
import com.ai.dsa.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.* ;

@Component
public class DataInitializer {
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final SolutionRepository solutionRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository,
                           ProblemRepository problemRepository,
                           SolutionRepository solutionRepository) {
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
        this.solutionRepository = solutionRepository;

    }

    @PostConstruct
    public void initializeData(){
        User user1 = new User("john", "john@example.com", "password1234", Arrays.asList("USER"));
        User user2 = new User("Doe", "doe@example.com", "password456", Arrays.asList("USER"));


        userRepository.save(user1);
        userRepository.save(user2);

        Problem problem1 =  new Problem(
                "Reverse Array",
                "Arrays",
                "Write a function to reverse an array of integers",
                "Easy"
        );

        Problem problem2 = new Problem(
                "Find Minimum",
                "Arrays",
                "Find the minimum value in an unsorted array",
                "Medium"
        );

        problemRepository.save(problem1);
        problemRepository.save(problem2);

        Solution solution1 = new Solution(
                "public static void reverseArray(int[] arr) {\n" +
                        "    int i = 0;\n" +
                        "    int j = arr.length - 1;\n" +
                        "    while (i < j) {\n" +
                        "        int temp = arr[i];\n" +
                        "        arr[i] = arr[j];\n" +
                        "        arr[j] = temp;\n" +
                        "        i++;\n" +
                        "        j--;\n" +
                        "    }\n}",
                user1,
                problem1,
                true
        );

        Solution solution2 = new Solution(
                "public static int findMin(int[] arr) {\n" +
                        "    int min = arr[0];\n" +
                        "    for (int i = 1; i < arr.length; i++) {\n" +
                        "        if (arr[i] < min) {\n" +
                        "            min = arr[i];\n" +
                        "        }\n" +
                        "    }\n" +
                        "    return min;\n}",
                user2,
                problem2,
                true
        );

        solutionRepository.save(solution1);
        solutionRepository.save(solution2);
    }
}
