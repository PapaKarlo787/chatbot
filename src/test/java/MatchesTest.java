import org.junit.Test;

public class MatchesTest {
    @Test
    public void prepareTest() {
        var menu = new MainChatBot();
        var io = new FakeIo();
        menu.setIO(io);
        io.setMessage("help");
        io.setMessage("authors");
        io.setMessage("date");
        io.setMessage("run calc");
        io.setMessage("exit");
        menu.main(new String[] {});
        io.getMessage();
        io.getMessage();
    }
}
