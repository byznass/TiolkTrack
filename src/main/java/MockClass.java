import java.util.Optional;

public class MockClass {

    public int doSomething(int seed) {

        return seed + 5;
    }

    public void doSomething () {
        Optional<String> optional = getOptional();
        if (optional != null) {  // Noncompliant
            // do something with optional...
        }
    }

    public Optional<String> getOptional() {
        // ...
        return null;  // Noncompliant
    }
}