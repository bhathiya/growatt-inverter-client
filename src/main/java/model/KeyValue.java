package model;

public class KeyValue {

    String topLabel;
    String content;
    String contentMultiline = "true";

    public KeyValue(String topLabel, String content) {
        this.topLabel = topLabel;
        this.content = content;
    }

    public String getTopLabel() {
        return topLabel;
    }

    public String getContent() {
        return content;
    }

    public String getContentMultiline() {
        return contentMultiline;
    }
}
