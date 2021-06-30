import games.Matches;

class Games extends Menu
{
    public Games(Io cl_) {
        cl = cl_;
    }

    @Override
    public void putHelps() {
        helps.add("Игровое меню\nВы можете сыграть:");
        helps.add("    matches - вам нельзя забрать последнюю спичку для победы (x - выход)");
    }

    @Override
    public void putCommands() {
        commands.put("matches", s -> matches());
    }

    private void matches() {
        var game = new Matches();
        do {
            cl.print(game.getMessage());
        } while (game.step(cl.read()));
        cl.print(game.getMessage());
    }

    @Override
    public void exit() { }
}
