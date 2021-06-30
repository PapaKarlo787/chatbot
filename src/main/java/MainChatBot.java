import java.io.IOException;
import java.util.Date;

public class MainChatBot extends Menu {
    @Override
    public void putHelps() {
        helps.add("Привет!\nВы можете использовать команды:");
        helps.add("    echo - печать вашего ввода");
        helps.add("    authors - имена авторов");
        helps.add("    games - игровое меню");
        helps.add("    3d - 3D меню");
        helps.add("    date - печать текущей даты");
        helps.add("    run - запуск указанного приложения");
    }

    private void run(String str) {
        try {
            Runtime.getRuntime().exec(str);
        } catch (IOException s) {
            s.printStackTrace();
        }
    }

    private void auths() {
        cl.print("Authors:\n    PapaKarlo787");
    }

    @Override
    public void putCommands() {
        commands.put("echo", s -> cl.print(String.join(" ", s)));
        commands.put("authors", s -> auths());
        commands.put("games", s -> (new Games(cl)).main(s));
        commands.put("run", s -> run(String.join(" ", s)));
        commands.put("date", s -> cl.print(new Date()));
        commands.put("3d", s -> (new My3D(cl)).main(s));
    }

    @Override
    public void exit() { }
}
