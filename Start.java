import base.Help;
import questions.Question;
import student.Student;
import examiner.Examiner;

public final class Start {

    Start() {

        // Initialize
        this.init();

        int choice = 0;

        Help.echo("\n");
        Help.echoLn("========================================");
        Help.echoLn("|\tSimple Online Exam System\t|");
        Help.echoLn("========================================");
        Help.echo("\n");

        while (true) {
            Help.generateOptionsList(new String[] { "Student", "Examiner", "Exit" });

            if ((choice = Help.readInteger()) == -1) {
                Help.echoLn("\n\n[!] Wront Options!!\n");
                continue;
            }

            switch (choice) {
                case 3:
                    // Exit
                    Help.echoLn("\n=====================");
                    Help.echoLn("|     Thank you     |");
                    Help.echoLn("=====================\n");
                    return;

                case 1:
                    // Student
                    new Student().studentStart();
                    break;

                case 2:
                    // Examiner
                    new Examiner().examinerStart();
                    break;

                default:
                    Help.echoLn("\n\n[!] Wront Options!!\n");
                    break;
            }
        }
    }

    private void init() {
        String[] examiners = Help.readFile(Help.EXAMINER_PATH, Help.EXAMINER_SIZE);
        String[] students = Help.readFile(Help.STUDENT_PATH, Help.STUDENT_SIZE);
        String[] questions = Help.readFile(Help.QUESTION_PATH, Help.QUESTION_SIZE);

        for (int i = 0; i < examiners.length; i++) {
            if (examiners[i] == null) {
                break;
            }

            String[] examinerInfo = examiners[i].split(Help.DIVDER);
            Examiner examiner = new Examiner();
            examiner.initExaminer(examinerInfo);
        }

        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {
                break;
            }

            String[] studentInfo = students[i].split(Help.DIVDER);
            Student student = new Student();
            student.initStudent(studentInfo);
        }

        for (int i = 0; i < questions.length; i++) {
            if (questions[i] == null) {
                break;
            }

            String[] questionInfo = questions[i].split(Help.DIVDER);
            Question question = new Question();
            question.initQuestion(questionInfo);
        }
    }

    public static void main(String[] args) {
        new Start();
    }
}