// File: QuizGame.java
import java.util.Scanner;

class Question {
    String question;
    String[] options;
    char correctAnswer;

    // Constructor
    public Question(String question, String[] options, char correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Display question and options
    public void display() {
        System.out.println(question);
        char optionLabel = 'A';
        for (String option : options) {
            System.out.println(optionLabel + ") " + option);
            optionLabel++;
        }
    }

    // Check if answer is correct
    public boolean isCorrect(char answer) {
        return Character.toUpperCase(answer) == correctAnswer;
    }
}

public class QuizGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int score = 0;

        // Create an array of Question objects
        Question[] questions = {
            new Question("1. Who developed Java Programming Language?",
                    new String[]{"James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "Guido van Rossum"}, 'A'),

            new Question("2. Which company originally owned Java?",
                    new String[]{"Microsoft", "Sun Microsystems", "IBM", "Apple"}, 'B'),

            new Question("3. Which keyword is used to inherit a class in Java?",
                    new String[]{"this", "super", "extends", "inherits"}, 'C'),

            new Question("4. Which of the following is not an OOP concept?",
                    new String[]{"Encapsulation", "Polymorphism", "Compilation", "Inheritance"}, 'C'),

            new Question("5. What is the default value of a boolean variable in Java?",
                    new String[]{"true", "false", "null", "0"}, 'B')
        };

        System.out.println("===== 🎯 Welcome to the Java Quiz Game =====");
        System.out.println("Answer by typing A, B, C, or D\n");

        // Loop through each question
        for (Question q : questions) {
            q.display();
            System.out.print("Your answer: ");
            char answer = sc.next().charAt(0);

            if (q.isCorrect(answer)) {
                System.out.println("✅ Correct!\n");
                score++;
            } else {
                System.out.println("❌ Wrong! Correct answer: " + q.correctAnswer + "\n");
            }
        }

        // Show result
        System.out.println("===== 🏁 Quiz Completed! =====");
        System.out.println("Your Score: " + score + " out of " + questions.length);

        // Performance message
        if (score == questions.length)
            System.out.println("🎉 Excellent! You got all answers correct!");
        else if (score >= 3)
            System.out.println("👍 Good job! Keep practicing Java!");
        else
            System.out.println("📘 You need more practice. Try again!");

        sc.close();
    }
}
