import org.junit.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FirstTaskTest {
    @Test
    public void dateTest() {
        var menu = new MainChatBot();
        var io = new FakeIo();
        menu.setIO(io);
        io.setMessage("date");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(io.getMessage(), (new Date()).toString());
    }

    @Test
    public void infoTest() {
        var menu = new MainChatBot();
        var io = new FakeIo();
        menu.setIO(io);
        io.setMessage("authors");
        io.setMessage("help");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(io.getMessage(), "Authors:\n    PapaKarlo787");
        io.getMessage();
        io.getMessage();
        io.getMessage();
    }

    @Test
    public void runTest() {
        var menu = new MainChatBot();
        var io = new FakeIo();
        menu.setIO(io);
        io.setMessage("run");
        io.setMessage("run calc");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(io.getMessage(), "No such command or arguments are wrong!");
        assertEquals(io.getMessage(), null);
    }

    @Test
    public void nextMenuTest() {
        var menu = new MainChatBot();
        var io = new FakeIo();
        menu.setIO(io);
        io.setMessage("games");
        io.setMessage("exit");
        io.setMessage("exit");
        menu.main(new String[] {});
    }

    @Test
    public void startFormGamesTest() {
        var io = new FakeIo();
        var menu = new Games(io);
        io.setMessage("games");
        io.setMessage("exit");
        io.setMessage("exit");
        menu.main(new String[] {});
    }

    @Test
    public void matchesTest() {
        var menu = new MainChatBot();
        var io = new FakeIo();
        var number = "3";
        menu.setIO(io);
        io.setMessage("games");
        io.setMessage("matches");
        io.setMessage(number);
        io.setMessage("w");
        io.setMessage("100");
        io.setMessage("x");
        io.setMessage("exit");
        io.setMessage("exit");
        menu.main(new String[] {});
        var msg = io.getMessage().split(" ");
        var count = Integer.parseInt(msg[0]);
        var max = Integer.parseInt(msg[7]);
        msg = io.getMessage().split(" ");
        var compNum = Integer.parseInt(msg[2]);
        var humNum = Integer.parseInt(number);
        assertEquals(count -= compNum + humNum, Integer.parseInt(msg[3].substring(1)));
        assertTrue(max >= compNum);
        assertTrue(max >= humNum);
        msg = io.getMessage().split(" ");
        assertEquals(msg[0], "wrong");
        assertEquals(Integer.parseInt(msg[2].substring(1)), count);
        msg = io.getMessage().split(" ");
        assertEquals(msg[0], "wrong");
        assertEquals(Integer.parseInt(msg[2].substring(1)), count);
        assertEquals(io.getMessage(), "End game");
    }
}
