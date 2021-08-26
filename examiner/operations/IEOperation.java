package examiner.operations;

import examiner.Examiner;

public interface IEOperation {
    boolean addExaminer(Examiner examiner);
    boolean removeExaminer(String examinerId);
    Examiner getExaminer(int index);
    Examiner getExaminer(String examinerId);
    void showAllInformation();
}
