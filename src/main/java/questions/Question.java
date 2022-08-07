package questions;

public class Question {
    private String questionName;//question
    private String questionType;//Type of question
    private String examID;//ID of the exam of the question
    private String questionID;//ID of the question
    private String sectionID;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctoption;
    private boolean isAnswered;

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public int getCorrectoption() {
        return correctoption;
    }

    public void setCorrectoption(int correctoption) {
        this.correctoption = correctoption;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }
    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
    public String getExamID() {
        return examID;
    }
    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getQuestionID() {
        return questionID;
    }

    @Override
    public String toString() {
        return this.questionName;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

}
