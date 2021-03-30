package aoka.sample;

public class PyJniusExample {
    private long jtid = -1;
    private int ptid = -1;

    public PyJniusExample(int pyThreadId) {
        this.jtid = Thread.currentThread().getId();
        this.ptid = pyThreadId;
    }

    // dummy process
    public String process(int pyThreadId) {
        String str = "a";
        String ret = "";
        for (int i = 0; i < 10000; i++) {
            ret += str;
        }
        return ret;
    }

    public void validate(int pyThreadId) throws Exception {
        long javaThreadId = Thread.currentThread().getId();
        if (this.ptid == pyThreadId && this.jtid == javaThreadId) {
            // do nothing
        } else {
            // Here should not be executed. 
            String errorMessage = String.format("Expected java thread id is %d, but actual is %d.", this.jtid, javaThreadId);
            errorMessage += System.lineSeparator();
            errorMessage += String.format("Expected python thread id is %d, but actual is %d.", this.ptid, pyThreadId);
            System.out.println(errorMessage);
            throw new Exception(errorMessage);
        }
    }
}
