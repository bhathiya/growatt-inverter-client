package model;

public class Card {

    Header header;
    Section[] sections;

    public Card(Header header, Section[] sections) {
        this.header = header;
        this.sections = sections;
    }

    public Header getHeader() {
        return header;
    }

    public Section[] getSections() {
        return sections;
    }
}
