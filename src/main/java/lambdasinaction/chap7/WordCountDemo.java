package lambdasinaction.chap7;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class WordCountDemo {

    public static final String SENTENCE = " Nel   mezzo del cammin  di nostra  vita mi  ritrovai in una  selva oscura che la  dritta via era   smarrita ";

    public static void main(String[] args) {
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
        System.out.println("Found " + countWords(SENTENCE) + " words");
    }

    /**
     * <b>概要：</b>:
     *      方法1：顺序计算单词个数
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/17 11:29 </br>
     * @param s 字符串
     * @return 单词数量
     */
    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) {
                    counter++;
                }
                lastSpace = Character.isWhitespace(c);
            }
        }
        return counter;
    }

    /**
     * <b>概要：</b>:
     *      方法2：函数式计算单词个数
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/17 11:48 </br>
     * @param:
     * @return:
     */
    private static int countWords(Stream<Character> stream) {
        WordCounter initWordCount = new WordCounter(0, true);
        BiFunction<WordCounter, Character, WordCounter> accumulate = WordCounter::accumulate;//定义了如何更改WordCounter状态
        BinaryOperator<WordCounter> combine = WordCounter::combine;//会对作用于不同子部分的两个结果集汇总
        WordCounter wordCounter = stream.reduce(initWordCount, accumulate, combine);
        return wordCounter.getCounter();
    }

    /**
     * <b>概要：</b>:
     *      并行计算单词个数
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/17 11:44 </br>
     * @param sentence 一句话
     * @return:
     */
    public static int countWords(String sentence) {
        //TODO ERROR:并行流统计字数会出错
        Stream<Character> stream = IntStream.range(0, sentence.length())
                                            .mapToObj(SENTENCE::charAt).parallel();
        return countWords(stream);

        //只能在两个词之前拆开String,然后由此创建并行流
//        Spliterator<Character> spliterator = new WordCounterSpliterator(sentence);
//        Stream<Character> stream = StreamSupport.stream(spliterator, true);
//        return countWords(stream);
    }




    private static class WordCounter {

        /** 计数器单词个数 */
        private final int counter;

        /** 上一个单词是否为空格 */
        private final boolean lastSpace;

        public WordCounter(int counter, boolean lastSpace) {
            this.counter = counter;
            this.lastSpace = lastSpace;
        }

        public WordCounter accumulate(Character c) {
            if (Character.isWhitespace(c)) {
                return lastSpace ? this : new WordCounter(counter, true);
            } else {
                return lastSpace ? new WordCounter(counter+1, false) : this;
            }
        }

        public WordCounter combine(WordCounter wordCounter) {
            return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
        }

        public int getCounter() {
            return counter;
        }
    }

    private static class WordCounterSpliterator implements Spliterator<Character> {

        private final String string;
        private int currentChar = 0;

        private WordCounterSpliterator(String string) {
            this.string = string;
        }

        /**
         * <b>概要：</b>:
         *      处理当前字符，如果还有字符要处理，则返回true
         * <b>作者：</b>SUXH</br>
         * <b>日期：</b>2020/3/17 11:54 </br>
         * @param:
         * @return:
         */
        @Override
        public boolean tryAdvance(Consumer<? super Character> action) {
            action.accept(string.charAt(currentChar++));
            return currentChar < string.length();
        }

        @Override
        public Spliterator<Character> trySplit() {
            int currentSize = string.length() - currentChar;
            if (currentSize < 10) {//表示要解析的String足够小，可以顺序处理
                return null;
            }
            for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
                if (Character.isWhitespace(string.charAt(splitPos))) {
                    Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                    currentChar = splitPos;
                    return spliterator;
                }
            }
            return null;
        }

        @Override
        public long estimateSize() {
            return string.length() - currentChar;
        }

        @Override
        public int characteristics() {
            return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
        }
    }
}
