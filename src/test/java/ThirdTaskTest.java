import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThirdTaskTest {
    private final String error = "No such command or arguments are wrong!";

    @Test
    public void noSuchTypeFileTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("load qwe.ee");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(error, io.getMessage());
        assertEquals(null, io.getMessage());
    }

    @Test
    public void textStlTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("load text.stl");
        io.setMessage("remove");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(null, io.getMessage());
    }

    @Test
    public void objTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("load text.obj");
        io.setMessage("remove");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(null, io.getMessage());
    }

    @Test
    public void hexStlTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("load hex.stl");
        io.setMessage("remove");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(null, io.getMessage());
    }

    @Test
    public void likeTextStlTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("load solid_not_text.stl");
        io.setMessage("remove");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(error, io.getMessage());
        assertEquals(error, io.getMessage());
        assertEquals(null, io.getMessage());
    }

    @Test
    public void spacedNameTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("load text     spaced                  file.obj");
        io.setMessage("remove");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(null, io.getMessage());
    }
}
