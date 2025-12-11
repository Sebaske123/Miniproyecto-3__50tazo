package org.example.mini_proyecto_50tazo.model.card;

/**
 * Card representation for Cincuentazo game using French deck.
 * Values: 2-10, J, Q, K, A with suits.
 *
 * @author Juan Sebastian Tapia
 * @version 1.0
 * @since 2025
 */
public class Card {
    private final String valueStr;
    private final String suit; // H, D, C, S (Hearts, Diamonds, Clubs, Spades)

    /**
     * Constructor for Card with value only (for compatibility).
     *
     * @param valueStr the card value (2-10, J, Q, K, A)
     */
    public Card(String valueStr) {
        this.valueStr = valueStr;
        this.suit = "H"; // Default suit
    }

    /**
     * Constructor for Card with value and suit.
     *
     * @param valueStr the card value (2-10, J, Q, K, A)
     * @param suit the card suit (H, D, C, S)
     */
    public Card(String valueStr, String suit) {
        this.valueStr = valueStr;
        this.suit = suit;
    }

    public String getValueStr() {
        return valueStr;
    }

    public String getValue() {
        return valueStr;
    }

    public String getSuit() {
        return suit;
    }

    /**
     * Return a base numeric value used for initial card on table.
     *
     * @return the initial value
     */
    public int getValueForInitial() {
        switch (valueStr) {
            case "J":
            case "Q":
            case "K":
                return -10;
            case "A":
                return 1;
            case "9":
                return 0;
            default:
                try {
                    return Integer.parseInt(valueStr);
                } catch (NumberFormatException ex) {
                    return 0;
                }
        }
    }

    /**
     * Checks whether this card can be played given the current sum.
     * For Ace, we consider the best possible value (10 if it doesn't exceed 50).
     *
     * @param currentSum the current sum on the table
     * @return true if the card can be played
     */
    public boolean canBePlayed(int currentSum) {
        int v = resolveValueForPlay(currentSum);
        int newSum = currentSum + v;
        return newSum <= 50;
    }

    /**
     * Alternative method name for compatibility.
     *
     * @param topCard the top card (not used in Cincuentazo)
     * @return always true for now
     */
    public boolean canBePlayedOver(Card topCard) {
        // In Cincuentazo, we don't match cards, we check against sum
        // This method is here for compatibility with old code
        return true;
    }

    /**
     * Resolves the integer value (delta) that will be applied to the table sum.
     * - Face cards (J, Q, K): -10
     * - Nine: 0
     * - Numbers (2-8, 10): face value
     * - Ace: 10 or 1 (whichever keeps sum ≤ 50)
     *
     * @param currentSum the current sum on the table
     * @return the value to add to the sum
     */
    public int resolveValueForPlay(int currentSum) {
        switch (valueStr) {
            case "J":
            case "Q":
            case "K":
                return -10;
            case "9":
                return 0;
            case "A":
                // if adding 10 doesn't exceed 50, prefer 10; otherwise 1
                return (currentSum + 10 <= 50) ? 10 : 1;
            default:
                try {
                    return Integer.parseInt(valueStr);
                } catch (NumberFormatException ex) {
                    return 0;
                }
        }
    }

    /**
     * Gets the image file name for this card based on your folder structure.
     * Format: images/cartas/Cartas_[Suit]/[Value]_[SuitName].png
     * Example: images/cartas/Cartas_Corazon/2_Corazon.png
     *
     * @return the image path
     */
    public String getImageName() {
        String suitFolder = getSuitFolder();
        String suitName = getSuitName();
        String fileName = getFileNameForValue();
        return "images/cartas/" + suitFolder + "/" + fileName + "_" + suitName + ".png";
    }

    /**
     * Gets the suit folder name based on the suit code.
     *
     * @return the suit folder name
     */
    private String getSuitFolder() {
        switch (suit) {
            case "H": return "Cartas_Corazon";
            case "D": return "Cartas_Diamantes";
            case "C": return "Cartas_Trebol";
            case "S": return "Cartas_Picas";
            default: return "Cartas_Corazon";
        }
    }

    /**
     * Gets the suit name for the file name.
     *
     * @return the suit name in Spanish
     */
    private String getSuitName() {
        switch (suit) {
            case "H": return "Corazon";
            case "D": return "Diamante";
            case "C": return "Trebol";
            case "S": return "Picas";
            default: return "Corazon";
        }
    }

    /**
     * Gets the file name prefix for the card value.
     * Maps: A -> AS (for Corazon) or As (for others)
     * Special handling for inconsistent naming
     *
     * @return the file name prefix
     */
    private String getFileNameForValue() {
        if ("A".equals(valueStr)) {
            // Corazon uses "AS", others use "As"
            if ("H".equals(suit)) {
                return "AS";
            } else {
                return "As";
            }
        }
        return valueStr; // 2, 3, 4, ..., 10, J, Q, K
    }

    @Override
    public String toString() {
        return valueStr + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card)) return false;
        Card other = (Card) obj;
        return valueStr.equals(other.valueStr) && suit.equals(other.suit);
    }

    @Override
    public int hashCode() {
        return valueStr.hashCode() + suit.hashCode();
    }
}