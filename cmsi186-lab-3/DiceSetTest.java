import java.util.List;

public class DiceSetTest extends TestSuite {
    public static void main(String[] args) {
        TestSuite.run(new DiceSetTest());
    }

    public Test[] getTests() {
        return new Test[]{
            new Test("A bad number of dice argument in the constructor", () -> expectThrows(
                () -> new DiceSet(5, 1),
                IllegalArgumentException.class, "At least two dice required"
            )),
            new Test("A bad sides argument in the constructor", () -> expectThrows(
                () -> new DiceSet(3, 10),
                IllegalArgumentException.class, "Dice must have at least four sides"
            )),
            new Test("Too few dice in the constructor", () -> expectThrows(
                () -> new DiceSet(6),
                IllegalArgumentException.class, "At least two dice required"
            )),
            new Test("Constructor detects illegal dice", () -> expectThrows(
                () -> new DiceSet(5, 1, 3, 6, 2),
                IllegalArgumentException.class, "Die value not legal for die shape"
            )),
            new Test("A new dice set sums to the expected value", () -> {
                expectEqual(new DiceSet(5, 10).sum(), 10);
            }),
            new Test("A new dice set contains all 1s", () -> {
                expectEqual(new DiceSet(5, 8).values(), List.of(1, 1, 1, 1, 1, 1, 1, 1));
            }),
            new Test("A new dice set is converted to a string will all 1s", () -> {
                expectEqual(new DiceSet(5, 4).toString(), "[1][1][1][1]");
            }),
            new Test("toString works for specific dice test", () -> {
                expectEqual(new DiceSet(5, 4, 1, 1, 3).toString(), "[4][1][1][3]");
            }),
            new Test("sum works for specific dice test", () -> {
                expectEqual(new DiceSet(20, 1, 2, 3, 5, 8).sum(), 19);
            }),
            new Test("Descriptor works for 6d20", () -> {
                expectEqual(new DiceSet(20, 4, 2, 4, 19, 1, 2).descriptor(), "6d20");
            }),
            new Test("Descriptor works for 2d6", () -> {
                expectEqual(new DiceSet(6, 6, 6).descriptor(), "2d6");
            }),
            new Test("getIndividual works", () -> {
                expectEqual(new DiceSet(20, 1, 2, 3, 5, 8).getIndividual(2), 3);
            }),
            new Test("rollAll works", () -> {
                var diceSet = new DiceSet(9, 3, 5, 7, 8, 5);
                diceSet.rollAll();
                expectMatch(diceSet.toString(), "(\\[\\d\\]){5}");
            }),
            new Test("rollIndividual doesn't change the other values", () -> {
                var diceSet = new DiceSet(6, 5, 1, 2, 3, 5);
                diceSet.rollIndividual(3);
                expectMatch(diceSet.toString(), "\\[5\\]\\[1\\]\\[2\\]\\[[1-6]\\]\\[5\\]");
            }),
            new Test("identicalTest", () -> {
                expect(new DiceSet(5, 5, 2, 3, 2).isIdenticalTo(new DiceSet(5, 3, 2, 2, 5)),
                        "[5][2][3][2] should be identical to [3][2][2][5]");
            }),
        };
    }
}
