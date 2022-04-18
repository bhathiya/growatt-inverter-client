package model;

public class WebhookBody {
    Card[] cards;

    public WebhookBody(Card[] cards) {
        this.cards = cards;
    }

    public Card[] getCards() {
        return cards;
    }
}
