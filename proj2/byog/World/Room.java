package byog.World;

import byog.Core.Game;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {
    private static final int maxWidth = 7;
    private static final int maxHeight = 7;
    private int[] pos;  // pos[0] -> xPos pos[1] -> posY, (x, y) -> the bottom & left corner position
    private int width;
    private int height;
    private Random randomGenerator;

    public Room(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
        width = this.randomGenerator.nextInt(maxWidth) + Game.BIAS;
        height = this.randomGenerator.nextInt(maxHeight) + Game.BIAS;
        pos = new int[2];
        pos[0] = this.randomGenerator.nextInt(Game.WIDTH - 1 - width);
        pos[1] = this.randomGenerator.nextInt(Game.HEIGHT - 1 - height);
    }

    public void generate(TETile[][] world) {
        generateFrame(world);
        generateFloor(world);
    }

    private void generateFloor(TETile[][] world) {
        for (int row = pos[1] + 1; row < pos[1] - 1 + height; row += 1) {
            for (int column = pos[0] + 1; column < pos[0] - 1 + width; column += 1) {
                world[column][row] = Tileset.FLOOR;
            }
        }
    }

    private void generateFrame(TETile[][] world) {
        for (int i = pos[0]; i < pos[0] + width; i += 1) {  // ----
            world[i][pos[1]] = world[i][pos[1]].equals(Tileset.FLOOR) ? Tileset.FLOOR : Tileset.WALL;
            world[i][pos[1] + height - 1] = world[i][pos[1] + height - 1].equals(Tileset.FLOOR) ? Tileset.FLOOR : Tileset.WALL;
        }
        for (int i = pos[1] + 1; i < pos[1] + height - 1; i += 1) { // ||||
            world[pos[0]][i] = world[pos[0]][i].equals(Tileset.FLOOR) ? Tileset.FLOOR : Tileset.WALL;
            world[pos[0] + width - 1][i] = world[pos[0] + width - 1][i].equals(Tileset.FLOOR) ? Tileset.FLOOR : Tileset.WALL;
        }
    }
}
