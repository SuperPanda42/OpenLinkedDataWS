package fr.unice.polytech.n2i.workingprogress.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by clement0210 on 14-12-04.
 */
@XmlRootElement
@JsonRootName("question")
public class Question {

    private String question;
    private String rightAnswer;
    private List<String> answers;

    public Question(String question, String rightAnswer, List<String> answers) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answers = answers;
    }
    public Question(){

    }

    @XmlElement
    @JsonProperty("question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @XmlElement
    @JsonProperty("right_answer")
    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @XmlElement
    @JsonProperty("answers")
    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
