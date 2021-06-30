package games;

import java.util.Random;

public class Matches {
    private final Random rnd = new Random(System.currentTimeMillis());
    private Integer count = 10 + rnd.nextInt(40);
    private final Integer max = 3 + rnd.nextInt(5);
    private String winnerMsg = "End game";
    private String message = "";
    private Integer num = 0;

    private boolean getNum(String input) {
        try {
            num = Integer.parseInt(input);
        } catch(NumberFormatException e) {
            num = 0;
        }
        return num < 1 || num > Math.min(max, count);
    }

    public String getMessage() {
        if (count < 1) {
            return winnerMsg;
        }
        return message + count.toString() + " matches left\nYou can take up to " + max.toString();
    }

    public boolean step(String input) {
        if (input.equals("x")) {
            count = 0;
            return false;
        }
        if (getNum(input)) {
            message = "wrong number\n";
            return true;
        }
        count -= num;
        if (count < 1) {
            winnerMsg = "You lose\nTook last match";
            return false;
        }
        if (machineStep()) {
            winnerMsg = "You win!";
            return false;
        }
        return true;
    }

    private boolean machineStep() {
        var machNum = (count - 1) % (max+1);
        if (machNum == 0) {
            machNum = 1 + rnd.nextInt(max);
        }
        message = "Machine step: " + machNum + "\n";
        count -= machNum;
        return count < 1;
    }
}
