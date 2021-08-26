package student;

import base.Help;
import person.APerson;
import questions.Question;
import student.operations.ISOperation;

public class Student extends APerson implements ISOperation {

    /**
     * Properties
     */

    private double mark = -1.0;

    private static Student[] students = new Student[Help.STUDENT_SIZE];
    private static int currNumOfStudent = 0;

    public void studentStart() {

        int choice = 0;
        double tmpResult = -1.0;
        boolean isAdd = false;
        // boolean isDelete = false;

        String line = null;

        String tmpId = null;
        String tmpName = null;
        String tmpEmail = null;
        Student tmpStudent = null;

        while (true) {
            Help.generateOptionsList(new String[] { "Registration", "Go For Exam", "Student Information",
                    "Student Results", "Go Back" });

            if ((choice = Help.readInteger()) == -1) {
                Help.echoLn("\n\n[!] Wrong Options!!\n");
                continue;
            }

            switch (choice) {
                case 5:
                    // Go Back
                    return;

                case 1:
                    // Registration
                    if (this.isFull()) {
                        Help.echoLn("\n\n[!] Registration member has been reached the limit!!\n");
                        break;
                    }

                    tmpStudent = new Student();

                    // Input all the info from console
                    while (true) {
                        Help.echo("Student ID: ");
                        tmpId = Help.readString();

                        if (!"".equals(tmpId))
                            break;
                    }

                    while (true) {
                        Help.echo("Student Name: ");
                        tmpName = Help.readString();

                        if (!"".equals(tmpName))
                            break;
                    }

                    while (true) {
                        Help.echo("Student Email: ");
                        tmpEmail = Help.readString();

                        if (!"".equals(tmpEmail))
                            break;
                    }

                    // Set all the info
                    tmpStudent.setId(tmpId);
                    tmpStudent.setName(tmpName);
                    tmpStudent.setEmail(tmpEmail);

                    // Add Student
                    isAdd = this.addStudent(tmpStudent);

                    // Write in the file
                    if (isAdd) {
                        line = tmpStudent.getId() + Help.DIVDER;
                        line += tmpStudent.getMark() + Help.DIVDER;
                        line += tmpStudent.getName() + Help.DIVDER;
                        line += tmpStudent.getEmail() + "\n";

                        Help.writeFile(Help.STUDENT_PATH, line, true);
                    }

                    if (isAdd) {
                        Help.echoLn("\n\n[+] Successfully Registered\n");
                    } else {
                        Help.echoLn("\n\n[?] Unsuccessfull to Registered\n");
                    }

                    // Reset all the temporary variable
                    isAdd = false;
                    tmpStudent = null;
                    tmpId = null;
                    tmpName = null;
                    tmpEmail = null;

                    break;

                case 2:
                    // Go For Exam
                    if (this.isEmpty()) {
                        Help.echoLn("\n\n[!] Student Capacity is empty!!\n");
                        break;
                    }

                    // Input info from console
                    while (true) {
                        Help.echo("Student ID: ");
                        tmpId = Help.readString();

                        if (!"".equals(tmpId))
                            break;
                    }

                    tmpStudent = this.getStudent(tmpId);

                    if (tmpStudent == null) {
                        Help.echoLn("\n\n[?] Can not find Student!!\n");
                        break;
                    }

                    // Check if the student already done the exam
                    if (tmpStudent.getMark() >= 0.0) {
                        Help.echoLn("\n\n[?] You've already performe Exam!!");
                        Help.echoLn("\n\tYour Mark is: " + (tmpStudent.getMark() == -1.0 ? "N/A" : tmpStudent.getMark())
                                + "\n");
                        break;
                    }

                    // Start the exam
                    tmpResult = new Question().examStart(tmpStudent);

                    // Set the Result
                    tmpStudent.setMark(tmpResult);

                    // Show the Result
                    Help.echoLn("\n\tYour Mark is: " + (tmpStudent.getMark() == -1.0 ? "N/A" : tmpStudent.getMark()));

                    // Reset all the temporary variable
                    tmpStudent = null;
                    tmpId = null;

                    break;

                case 3:
                    // Student information
                    if (this.isEmpty()) {
                        Help.echoLn("\n\n[!] Student Capacity is empty!!\n");
                        break;
                    }

                    // Input info from console
                    while (true) {
                        Help.echo("Student ID: ");
                        tmpId = Help.readString();

                        if (!"".equals(tmpId))
                            break;
                    }

                    tmpStudent = this.getStudent(tmpId);

                    if (tmpStudent == null) {
                        Help.echoLn("\n\n[?] Can not find Student!!\n");
                        break;
                    }

                    // Showing information
                    tmpStudent.showInformation();

                    // Reset all the temporary variable
                    tmpStudent = null;
                    tmpId = null;

                    break;

                case 4:
                    // Student Results
                    if (this.isEmpty()) {
                        Help.echoLn("\n\n[!] Student Capacity is empty!!\n");
                        break;
                    }

                    // Input info from console
                    while (true) {
                        Help.echo("Student ID: ");
                        tmpId = Help.readString();

                        if (!"".equals(tmpId))
                            break;
                    }

                    tmpStudent = this.getStudent(tmpId);

                    if (tmpStudent == null) {
                        Help.echoLn("\n\n[?] Can not find Student!!\n");
                        break;
                    }

                    // Showing information
                    Help.echoLn("\n\tYour Mark is: " + (tmpStudent.getMark() == -1.0 ? "N/A" : tmpStudent.getMark()));

                    // Reset all the temporary variable
                    tmpStudent = null;
                    tmpId = null;

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
        return 0 >= Student.currNumOfStudent;
    }

    private boolean isFull() {
        return Help.STUDENT_SIZE <= Student.currNumOfStudent;
    }

    public void initStudent(String[] studentInfo) {
        Student student = new Student();

        student.setId(studentInfo[0]);
        student.setMark(Double.parseDouble(studentInfo[1]));
        student.setName(studentInfo[2]);
        student.setEmail(studentInfo[3]);

        this.addStudent(student);
    }

    /**
     * Setter Method
     */

    private void setMark(double mark) {
        this.mark = mark;
    }

    /**
     * Getter Method
     */

    private double getMark() {
        return this.mark;
    }

    /**
     * Abstract Override Method
     * 
     * @see ~/person/APerson.java
     */

    public void showInformation() {
        Help.echoLn("\n\tStudent ID: " + this.getId());
        Help.echoLn("\tStudent Name: " + this.getName());
        Help.echoLn("\tStudent Email: " + this.getEmail());
        Help.echoLn("\tStudent Mark: " + (this.getMark() == -1.0 ? "N/A" : this.getMark()));
    }

    /**
     * Interface Override Method
     * 
     * @see ~/student/operations/ISOperation.java
     */

    public boolean addStudent(Student student) {
        // if (this.isFull()) {
        // Help.echoLn("\nCan't Insert Student, capacity is full!!\n");
        // return false;
        // }

        if (this.getStudent(student.getId()) == null) {
            Student.students[Student.currNumOfStudent++] = student;
            return true;
        } else {
            Help.echo("\n\n[!] Duplication ID! can not insert!!");
        }
        return false;
    }

    // public boolean removeStudent(String studentId) {
    // return false;
    // }

    public Student getStudent(int index) {
        return Student.students[index];
    }

    public Student getStudent(String studentId) {
        for (int i = 0; i < Student.currNumOfStudent; i++) {
            if (this.getStudent(i).getId().equals(studentId)) {
                return this.getStudent(i);
            }
        }
        return null;
    }

    public void showAllInformation() {
        if (this.isEmpty()) {
            Help.echoLn("\n\n[!] Student Capacity is empty!!\n");
            return;
        }

        Help.echoLn("");

        for (int i = 0; i < Student.currNumOfStudent; i++) {
            Help.echoLn("Student " + (i + 1) + " :");
            this.getStudent(i).showInformation();
        }

        Help.echoLn("");
    }
}