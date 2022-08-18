package edu.neu.coe.huskySort.sort.huskySort;

import edu.neu.coe.huskySort.sort.huskySortUtils.ChineseCharacter;
import org.junit.Test;

import static edu.neu.coe.huskySort.sort.huskySort.HuskySortBenchmark.CHINESE_NAMES_CORPUS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ChineseCharacterIntegrationTest {
    @Test
    public void testParsePinyinN() {
        for (final String word : HuskySortBenchmarkHelper.getWords(CHINESE_NAMES_CORPUS, HuskySortBenchmark::lineAsList)) {
            try {
                final String[] parsedPinyin = ChineseCharacter.parsePinyin(ChineseCharacter.convertToPinyin(word), word.length());
                assertEquals(word.length(), parsedPinyin.length);
            } catch (final Exception e) {
                fail("Failed parse pinyin for: " + word + ": " + e.getLocalizedMessage());
            }
        }
    }
}