import org.junit.jupiter.api.Test;

public class StartMenuTest {
    Menu menu = new MainChatBot();

    @Test
    private void prepareTest() {
        menu.setIO(new FakeIo());
    }
}
