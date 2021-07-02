import java.util.HashMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;


public abstract class Menu
{
    public Menu() {
        this.commands = new HashMap<>();
        this.helps = new LinkedList<>();
        commands.put("help", s -> help());
        commands.put("exit", s -> exit = true);
        putCommands();
        putHelps();
        helps.add("help - печать этих строк");
        helps.add("exit - выход из меню");
        setIO(new Console());
    }

    public void setIO(Io io) {
        cl = io;
    }

    private void executor(String[] args) {
        if (args.length != 0) {
            try {
                commands.get(args[0]).accept(Arrays.copyOfRange(args, 1, args.length));
            } catch (Exception e) {
                cl.print("No such command or arguments are wrong!");
            }
        }
    }

    public void main(String[] args) {
        executor(args);
        while(!exit) {
            try {
                var line = cl.read();
                executor(line.split(" "));
            } catch (Exception e) {
                exit = true;
            }
        }
        exit();
    }

    public void help() {
        for (var l : helps) {
            cl.print(l);
        }
    }

    protected abstract void exit();
    protected abstract void putCommands();
    protected abstract void putHelps();
    private boolean exit;
    public Io cl;
    protected final HashMap<String, Consumer<String[]>> commands;
    protected final List<String> helps;
}
