
public class MockClass {

    public int doSomething(int seed) {

        return seed + 5;
    }

    public static void main(String[] args) {

        Day day = null;

        switch (day) {
            case MONDAY:
            case TUESDAY:
                WEDNESDAY:   // Noncompliant; syntactically correct, but behavior is not what's expected
                doSomething();
                break;
        }
    }

    private static void doSomething() {

    }

    private enum Day {
        MONDAY,
        TUESDAY,
        WEDNESDAY
    }
}