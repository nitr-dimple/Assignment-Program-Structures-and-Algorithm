package edu.neu.coe.huskySort.sort.huskySortUtils;

/**
 * Abstract base class for a Unicode Character.
 */
public abstract class UnicodeCharacter implements Comparable<UnicodeCharacter> {
    /**
     * Generate the long proxy value for the alternativeString x.
     * <p>
     * NOTE we should test whether the encoding is perfect.
     *
     * @return a long value.
     */
    public abstract long encode();

    /**
     * Method to generate the alternative representation for character x.
     * In the case of Chinese characters, the result will be a Pinyin string with tones.
     *
     * @return the alternative representation.
     */
    public abstract String alt();

    /**
     * Compare this UnicodeCharacter with "other".
     *
     * @param other the UnicodeCharacter to be compared.
     * @return the comparison of the longCodes.
     */
    public int compareTo(final UnicodeCharacter other) {
        return Long.compare(longCode, other.longCode);
    }

    /**
     * The (constant) null value for UnicodeCharacter.
     */
    public static final UnicodeCharacter NullChar = new UnicodeCharacter((char) 0) {
        public long encode() {
            return 0; // NOTE: this code is unreachable.
        }

        public String alt() {
            return " "; // NOTE: this code is unreachable.
        }
    };

    /**
     * Constructor to take a unicode character.
     *
     * @param unicode a unicode character.
     */
    public UnicodeCharacter(final char unicode) {
        this.unicode = unicode;
        this.alt = alt();
        this.longCode = encode();
    }

    @Override
    public String toString() {
        return "UnicodeCharacter{" + "unicode=" + unicode + ", alt='" + alt + '\'' + ", longCode=" + longCode + '}';
    }

    protected final char unicode;
    protected final long longCode;

    protected final String alt;
}
