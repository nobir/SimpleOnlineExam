package questions.operations;

import questions.Question;

public interface IQOperaton {
    boolean addQuestion(Question question);

    boolean removeQuestion(String questionId);

    Question getQuestion(int index);

    Question getQuestion(String questionId);

    void showAllInformation();
}
