class SimpleCounter {
    // write your code here
    private static final SimpleCounter INSTANCE = new SimpleCounter();

    public int counter;
    private SimpleCounter() { }

    public static SimpleCounter getInstance() {
        return INSTANCE;
    }
}
