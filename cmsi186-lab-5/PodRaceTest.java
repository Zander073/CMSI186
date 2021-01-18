import java.util.Set;

public class PodRaceTest extends TestSuite {
    public static void main(String[] args) {
        TestSuite.run(new PodRaceTest());
    }

    public Test[] getTests() {
        return new Test[] {
            new Test("Race has no winners if no one shows up", () -> {
                var winners = PodRace.race(100, Set.of(), 1, 10);
                expectEqual(winners, Set.of());
            }),
            new Test("Race has one winner if only one participant that actually goes", () -> {
                var racer = new ConstantVelocityPod("alice", 10);
                var winners = PodRace.race(100, Set.of(racer), 1, 10);
                expectEqual(winners, Set.of(racer));
            }),
            new Test("Race has no winners if only participant don't go forward", () -> {
                var racer1 = new ConstantVelocityPod("alice", 0);
                var racer2 = new ConstantVelocityPod("bob", -3);
                var winners = PodRace.race(100, Set.of(racer1, racer2), 1, 10);
                expectEqual(winners, Set.of());
            }),
            new Test("Race can produce multiple winners on a tie", () -> {
                var racer1 = new ConstantVelocityPod("alice", 5);
                var racer2 = new ConstantVelocityPod("bob", 3);
                var racer3 = new ConstantVelocityPod("carolyne", 5);
                var winners = PodRace.race(100, Set.of(racer1, racer2, racer3), 1, 1000);
                expectEqual(winners, Set.of(racer1, racer3));
            })
        };
    }
}
