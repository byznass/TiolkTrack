
public class MockClass {

    /**
     * cioc
     * @param seed
     * @return
     */
    public int doSomething(int seed) {

        return seed + 5;
    }

    public static void main(String args[]) {
        try {
            doNothing();
        } catch (Throwable t) {
            System.exit(1);  // Default exit code, 0, indicates success. Non-zero value means failure.
        }
    }

    private static void doNothing() {

    }
}