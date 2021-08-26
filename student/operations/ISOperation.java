package student.operations;

import student.Student;

public interface ISOperation {
    boolean addStudent(Student studentId);
    // boolean removeStudent(String studentId);
    Student getStudent(int index);
    Student getStudent(String studentId);
    void showAllInformation();
}
