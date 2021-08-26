package questions;

import base.Help;
import student.Student;
import questions.question.AQuestion;
import questions.operations.IQOperaton;

public final class Question extends AQuestion implements IQOperaton {

    /**
     * Properties
     */

    private static Question[] questions = new Question[Help.QUESTION_SIZE];
    private static int currNumOfQuestion = 0;

    public void questionStart() {

        int choice = 0;
        boolean isAdd = false;
        boolean isDelete = false;

        String line = null;

        String tmpId = null;
        String tmpQstn = null;
        String tmpAnswer = null;
        Question tmpQuestion = null;

        while (true) {
            Help.generateOptionsList(
                    new String[] { "Insert Question", "Delete Question", "Show All Questions", "Go Back" });

            if ((choice = Help.readInteger()) == -1) {
                Help.echoLn("\n\n[!] Wrong Options!!\n");
                continue;
            }

            switch (choice) {
                case 4:
                    // Go Back
                    return;

                case 1:
                    // Insert
                    if (this.isFull()) {
                        Help.echoLn("\n\n[!] Can't Insert Question, capacity is full!!\n");
                        break;
                    }

                    tmpQuestion = new Question();

                    // Input all the info from console
                    while (true) {
                        Help.echo("Question ID: ");
                        tmpId = Help.readString();

                        if (!"".equals(tmpId))
                            break;
                    }

                    while (true) {
                        Help.echo("Question: ");
                        tmpQstn = Help.readString();

                        if (!"".equals(tmpQstn))
                            break;
                    }

                    while (true) {
                        Help.echo("Question Answer: ");
                        tmpAnswer = Help.readString();

                        if (!"".equals(tmpAnswer))
                            break;
                    }

                    // Set all the info
                    tmpQuestion.setId(tmpId);
                    tmpQuestion.setQuestion(tmpQstn);
                    tmpQuestion.setAnswer(tmpAnswer);

                    // Add Examiner
                    isAdd = this.addQuestion(tmpQuestion);

                    // Write in the file
                    if (isAdd) {
                        line = tmpQuestion.getId() + Help.DIVDER;
                        line += tmpQuestion.getQuestion() + Help.DIVDER;
                        line += tmpQuestion.getAnswer() + "\n";

                        Help.writeFile(Help.QUESTION_PATH, line, true);
                    }

                    if (isAdd) {
                        Help.echoLn("\n\n[+] Successfully Added\n");
                    } else {
                        Help.echoLn("\n\n[?] Unsuccessfull to Added\n");
                    }

                    // Reset all the temporary variable
                    isAdd = false;
                    tmpQuestion = null;
                    tmpId = null;
                    tmpQstn = null;
                    tmpAnswer = null;

                    break;

                case 2:
                    // Delete
                    if (this.isEmpty()) {
                        Help.echoLn("\n\n[!] Can not Remove Question, capacity is empty!!\n");
                        break;
                    }

                    // Input info from console
                    while (true) {
                        Help.echo("Question ID: ");
                        tmpId = Help.readString();

                        if (!"".equals(tmpId))
                            break;
                    }

                    isDelete = this.removeQuestion(tmpId);

                    if (isDelete) {
                        Help.echoLn("\n\n[-] Successfully Delete.\n");
                    } else {
                        Help.echoLn("\n\n[?] Unsuccessfull Delete\n");
                    }

                    // Reset all the temporary variable
                    isDelete = false;
                    tmpId = null;

                    break;

                case 3:
                    // Show All Quesiton
                    this.showAllInformation();
                    break;
                default:
                    Help.echoLn("\n\n[!] Wrong Options!!\n");
                    break;
            }
        }
    }

    /**
     * Help Method
     */

    private boolean isEmpty() {
        return 0 >= Question.currNumOfQuestion;
    }

    private boolean isFull() {
        return Help.QUESTION_SIZE <= Question.currNumOfQuestion;
    }

    public void initQuestion(String[] questionInfo) {
        Question question = new Question();

        question.setId(questionInfo[0]);
        question.setQuestion(questionInfo[1]);
        question.setAnswer(questionInfo[2]);

        this.addQuestion(question);
    }

    public double examStart(Student tmpStudent) {
        if (this.isEmpty()) {
            Help.echoLn("\n\n[!] Something went wrong\n[!] Can not find any Question!!\n");
            return -1.0;
        }

        double result = 0.0;
        String answer = null;
        String line = "";
        String[] students = Help.readFile(Help.STUDENT_PATH, Help.STUDENT_SIZE);

        Help.echoLn("");

        Help.echoLn("\n=====================================");
        Help.echoLn("|    Total Mark (" + Question.currNumOfQuestion + " x 1.25 = "
                + (Question.currNumOfQuestion * Help.MARK_PER_QUESTION) + ")    |");
        Help.echoLn("=====================================\n");

        for (int i = 0; i < Question.currNumOfQuestion; i++) {
            Help.echoLn("\t" + (i + 1) + ". " + this.getQuestion(i).getQuestion());
            Help.echo("\tAnswer: ");
            answer = Help.readString();

            if (this.getQuestion(i).getAnswer().equals(answer)) {
                result += Help.MARK_PER_QUESTION;
            }
        }

        Help.echoLn("");

        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {
                break;
            }

            String[] studentInfo = students[i].split(Help.DIVDER);

            if (studentInfo[0].equals(tmpStudent.getId())) {
                studentInfo[1] = "" + result;

                students[i] = studentInfo[0] + Help.DIVDER + studentInfo[1] + Help.DIVDER + studentInfo[2] + Help.DIVDER
                        + studentInfo[3];
            }

            line += students[i] + "\n";
        }

        Help.writeFile(Help.STUDENT_PATH, line, false);

        return result;
    }

    /**
     * Abstract Override Method
     * 
     * @see ~/questions/question/AQuestion.java
     */

    public void showInformation() {
        Help.echoLn("\tQuestion ID: " + this.getId());
        Help.echoLn("\tQuestion: " + this.getQuestion());
        Help.echoLn("\tAnswer: " + this.getAnswer());
    }

    /**
     * Interface Override Method
     * 
     * @see ~/questions/operations/IQOperation.java
     */

    public boolean addQuestion(Question question) {
        // if (this.isFull()) {
        // Help.echoLn("\nCan't Insert Question, capacity is full!!\n");
        // return false;
        // }

        if (this.getQuestion(question.getId()) == null) {
            Question.questions[Question.currNumOfQuestion++] = question;
            return true;
        } else {
            Help.echo("\n\n[!] Duplication ID! can not insert!!");
        }
        return false;
    }

    public boolean removeQuestion(String questionId) {

        boolean found = false;
        String line = "";
        String[] questions = Help.readFile(Help.QUESTION_PATH, Question.currNumOfQuestion);

        // For last Question
        if (this.getQuestion(Question.currNumOfQuestion - 1).getId().equals(questionId)) {
            Question.questions[Question.currNumOfQuestion - 1] = null;

            // Update the file
            for (int i = 0; i < questions.length; i++) {
                if (i == questions.length - 1) {
                    questions[i] = "";
                }

                if (i == questions.length - 1) {
                    line += questions[i];
                } else {
                    line += questions[i] + "\n";
                }
            }

            Help.writeFile(Help.QUESTION_PATH, line, false);

            Question.currNumOfQuestion--;
            return true;
        }

        // For (n - 1) Question
        for (int i = 0; i < Question.currNumOfQuestion - 1; i++) {
            if (this.getQuestion(i).getId().equals(questionId) && !found) {
                found = true;
            }

            // Rearrange questions array
            if (found) {
                Question.questions[i] = Question.questions[i + 1];
            }
        }

        // Update the file
        for (int i = 0; i < questions.length - 1; i++) {
            boolean _found = false;
            String[] examinerInfo = questions[i].split(Help.DIVDER);

            if (examinerInfo[0].equals(questionId) && !_found) {
                _found = true;
            }

            if (_found) {
                questions[i] = questions[i + 1];
            }

            line += questions[i] + "\n";
        }

        Help.writeFile(Help.QUESTION_PATH, line, false);

        if (!found) {
            Help.echo("\n\n[!] Can not find Question!!");
        } else {
            Question.currNumOfQuestion--;
        }

        return found;
    }

    public Question getQuestion(int index) {
        return Question.questions[index];
    }

    public Question getQuestion(String questionId) {
        for (int i = 0; i < Question.currNumOfQuestion; i++) {
            if (this.getQuestion(i).getId().equals(questionId)) {
                return this.getQuestion(i);
            }
        }
        return null;
    }

    public void showAllInformation() {
        if (this.isEmpty()) {
            Help.echoLn("\n\n[!] Question Capacity is empty!!\n");
            return;
        }

        Help.echoLn("");

        for (int i = 0; i < Question.currNumOfQuestion; i++) {
            Help.echoLn("Question " + (i + 1) + " :");
            this.getQuestion(i).showInformation();
        }

        Help.echoLn("");
    }
}
