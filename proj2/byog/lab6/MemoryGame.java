package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private final int width;
    private final int height;
    private int round;
    private final Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }
        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        if (n == 1) {
            return String.valueOf(CHARACTERS[rand.nextInt(26)]);
        }
        return String.valueOf(CHARACTERS[rand.nextInt(26)]) + generateRandomString(n - 1);
    }

    public void drawFrame(String s, Color fontColor) {
        int midWidth = width / 2;
        int midHeight = height / 2;

        StdDraw.clear(Color.BLACK);

        // Draw the GUI
        if (!gameOver) {
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            StdDraw.text(midWidth, height - 1, playerTurn ? "Type!" : "Watch!");
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height - 2, width, height - 2);
        }

        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(fontColor);
        StdDraw.text(midWidth, midHeight, s);
        StdDraw.show();

        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i += 1) {
            drawFrame(letters.substring(i, i + 1), Color.WHITE);
            StdDraw.pause(1000);
            drawFrame("", Color.WHITE);
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        StringBuilder res = new StringBuilder();
        int count = 0;
        drawFrame(res.toString(), Color.WHITE);
        while (count < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char typedIn = StdDraw.nextKeyTyped();
            res.append(typedIn);
            drawFrame(res.toString(), Color.YELLOW);
            count += 1;
        }
        StdDraw.pause(300);
        return res.toString();
    }

    public void startGame() {
        round = 0;
        playerTurn = false;
        String userInput = "";
        String expectInput = "";

        while (expectInput.equals(userInput)) {
            round += 1;
            playerTurn = false;
            drawFrame("Round:" + round, Color.green);
            StdDraw.pause(500);
            expectInput = generateRandomString(round);
            flashSequence(expectInput);
            playerTurn = true;
            userInput = solicitNCharsInput(round);
        }

        gameOver = true;
        drawFrame("Game Over! You made it to round: " + round, Color.green);
    }
}
