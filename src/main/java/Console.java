import java.util.Scanner;


class Console implements Io
{
    private final Scanner in = new Scanner(System.in);

    @Override
    public String read() {
        return in.nextLine();
    }

    @Override
    public void print(Object text) {
        System.out.println(text.toString());
    }
}
