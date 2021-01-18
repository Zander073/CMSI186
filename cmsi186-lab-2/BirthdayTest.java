public class BirthdayTest extends TestSuite {
    public static void main(String[] args) {
        TestSuite.run(new BirthdayTest());
    }

    public Test[] getTests() {
        return new Test[]{
            new Test("A bad people argument", () -> expectThrows(
                () -> SharedBirthday.probabilityEstimate(1, 2, 1),
                IllegalArgumentException.class, "At least two people required"
            )),
            new Test("A bad day argument", () -> expectThrows(
                () -> SharedBirthday.probabilityEstimate(5, 0, 2),
                IllegalArgumentException.class, "At least one day required"
            )),
            new Test("A bad trial argument", () -> expectThrows(
                () -> SharedBirthday.probabilityEstimate(2, 2, -3),
                IllegalArgumentException.class, "At least one trial required"
            )),
            new Test("2 people and 3 days", () -> expectWithin(
                SharedBirthday.probabilityEstimate(2, 3, 100000), 1.0 / 3, 0.01
            )),
            new Test("2 people and 4 days", () -> expectWithin(
                SharedBirthday.probabilityEstimate(2, 4, 100000), 0.250000, 0.01
            )),
            new Test("20 people and 400 days", () -> expectWithin(
                SharedBirthday.probabilityEstimate(20, 400, 100000), 0.383016, 0.01
            )),
            new Test("10 people and 20 days", () -> expectWithin(
                SharedBirthday.probabilityEstimate(10, 20, 1000000), 0.934527, 0.005
            )),
            new Test("200 people and 365 days", () -> expectWithin(
                SharedBirthday.probabilityEstimate(200, 365, 10000), 1.0, 0.001
            )),
            new Test("Having no command line arguments", () -> expectStandardErrorToHave(
                () -> SharedBirthday.main(new String[]{}),
                "Exactly three arguments required"
            )),
            new Test("Having four command line arguments", () -> expectStandardErrorToHave(
                () -> SharedBirthday.main(new String[]{"100", "100", "100", "100"}),
                "Exactly three arguments required"
            )),
            new Test("A non-numeric command line argument", () -> expectStandardErrorToHave(
                () -> SharedBirthday.main(new String[]{"3", "5", "dog"}),
                "Arguments must all be integers"
            )),
            new Test("Bad people argument on command line", () -> expectStandardErrorToHave(
                () -> SharedBirthday.main(new String[]{"1", "7", "100"}),
                "At least two people required"
            )),
            new Test("Bad day argument on command line", () -> expectStandardErrorToHave(
                () -> SharedBirthday.main(new String[]{"3", "0", "100"}),
                "At least one day required"
            )),
            new Test("Bad trial argument on command line", () -> expectStandardErrorToHave(
                () -> SharedBirthday.main(new String[]{"3", "10", "0"}),
                "At least one trial required"
            )),
            new Test("10 people and 20 days from command line", () -> expectStandardOutput(
                () -> SharedBirthday.main(new String[]{"10", "20", "100000"}),
                (res) -> expectWithin(Double.parseDouble(res), 0.934527, 0.005)
            )),
            new Test("2 people and 4 days from command line", () -> expectStandardOutput(
                () -> SharedBirthday.main(new String[]{"2", "4", "100000"}),
                (res) -> expectWithin(Double.parseDouble(res), 0.25, 0.01)
            )),
        };
    }
}
