package questions.question;

public abstract class AQuestion {

    /**
     * Properties
     */

    private String questionId;
    private String question;
    private String answer;

    /**
     * Setter Method
     */

    public void setId(String questionId) {
        this.questionId = questionId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Getter Method
     */

    public String getId() {
        return this.questionId;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    /**
     * Display information
     */

    public abstract void showInformation();
}
