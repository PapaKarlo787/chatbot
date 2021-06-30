import java.util.ArrayDeque;

public class FakeIo implements Io {

    private final ArrayDeque<String> inputMessages = new ArrayDeque();
    private final ArrayDeque<String> outputMessages = new ArrayDeque();

    @Override
    public String read() {
        return inputMessages.remove();
    }

    @Override
    public void print(Object text) {
        outputMessages.add(text.toString());
    }

    public void setMessage(String message) {
        inputMessages.add(message);
    }

    public String getMessage() {
        return outputMessages.poll();
    }

    public void clearOutput() {
        outputMessages.clear();
    }
}
