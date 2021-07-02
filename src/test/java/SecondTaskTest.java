import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecondTaskTest {
    private final String error = "No such command or arguments are wrong!";

    @Test
    public void startFrom3dTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("games");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(error, io.getMessage());
    }

    @Test
    public void drawingFiguresTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("cube");
        io.setMessage("sphere");
        io.setMessage("tetrahedron");
        io.setMessage("remove");
        io.setMessage("remove");
        io.setMessage("remove");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(null, io.getMessage());
    }

    @Test
    public void selectFiguresTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("cube");
        io.setMessage("next");
        io.setMessage("next");
        io.setMessage("next");
        io.setMessage("prev");
        io.setMessage("prev");
        io.setMessage("next");
        io.setMessage("remove");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(null, io.getMessage());
    }

    @Test
    public void setColorTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("cube");
        io.setMessage("color 200 100 30");
        io.setMessage("color 200    20           150         ");
        io.setMessage("color yellow        ");
        io.setMessage("color                   red         ");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(error, io.getMessage());
        assertEquals(error, io.getMessage());
        assertEquals(null, io.getMessage());
    }

    @Test
    public void moduloBigColorTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("cube");
        io.setMessage("color 400 100 100");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(null, io.getMessage());
    }

    @Test
    public void wrongColorTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("cube");
        io.setMessage("color color");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(error, io.getMessage());
    }

    @Test
    public void not3ColorTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("cube");
        io.setMessage("color 100");
        io.setMessage("color 100 200");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(error, io.getMessage());
        assertEquals(error, io.getMessage());
    }

    @Test
    public void changeSizeTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("cube");
        io.setMessage("size 10");
        io.setMessage("size 0.01");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(null, io.getMessage());
    }

    @Test
    public void changePositionTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("cube");
        io.setMessage("move 10 1 0.3");
        io.setMessage("rotate 90 60 90");
        io.setMessage("move -0.111 1000 -0.0003333");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(null, io.getMessage());
    }

    @Test
    public void not3CoordinatesTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("cube");
        io.setMessage("move 10");
        io.setMessage("move -0.111 -0.0003333");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(error, io.getMessage());
        assertEquals(error, io.getMessage());
    }

    @Test
    public void eyePositionTest() {
        var io = new FakeIo();
        var menu = new My3D(io);
        io.setMessage("cube");
        io.setMessage("eye 10");
        io.setMessage("eye 10 20");
        io.setMessage("eye 10 20 30");
        io.setMessage("exit");
        menu.main(new String[] {});
        assertEquals(error, io.getMessage());
        assertEquals(error, io.getMessage());
        assertEquals(null, io.getMessage());
    }
}
