package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
import java.util.Scanner;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int HEX_NUM = 19;
    private static final long SEED = 5201314;
    private static final Random RANDOM = new Random(SEED);

    public static TETile randomTile() {
        int tileNum = RANDOM.nextInt(7);
        switch (tileNum) {
            case 0:
                return Tileset.TREE;
            case 1:
                return Tileset.MOUNTAIN;
            case 2:
                return Tileset.FLOWER;
            case 3:
                return Tileset.SAND;
            case 4:
                return Tileset.GRASS;
            case 5:
                return Tileset.WATER;
            case 6:
                return Tileset.FLOOR;
            default:
                return Tileset.NOTHING;
        }
    }

    public static TETile[][][] drawComponents(int size) {
        int width = size + 2 * (size - 1);
        int height = 2 * size;
        TETile[][][] hexComponents = new TETile[HEX_NUM][width][height];
        int midColumn = width % 2 == 0 ? width / 2 - 1 : width / 2;

        for (TETile[][] component : hexComponents) {
            int columnPosLeft = size - 1;
            TETile thisRandom = randomTile();
            for (int row = 0; row < height / 2; row += 1) {
                for (int column = 0; column <= midColumn; column += 1) {
                    if (column >= columnPosLeft) {
                        component[column][row]  = thisRandom;
                        component[column][height - 1 - row] = thisRandom;
                        component[width - 1 - column][row]  = thisRandom;
                        component[width - 1 - column][height - 1 - row] = thisRandom;
                    } else {
                        component[column][row]  = Tileset.NOTHING;
                        component[column][height - 1 - row] = Tileset.NOTHING;
                        component[width - 1 - column][row] = Tileset.NOTHING;
                        component[width - 1 - column][height - 1 - row] = Tileset.NOTHING;
                    }
                }
                columnPosLeft  -= 1;
            }
        }
        return hexComponents;
    }

    public static void fillBluePrint(TETile[][] bluePrint, int size) {
        TETile[][][] hexComponents = drawComponents(size);
        for (int i = 0; i < HEX_NUM; i += 1) {
            // TODO: 合成到蓝图中
        }
    }

    public static void drawHexWorld(int size) {
        int width = 5 * size + 6 * (size - 1);
        int height = size * 10;
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

        TETile[][] hexWorldTiles = new TETile[width][height];
        fillBluePrint(hexWorldTiles, size);

        ter.renderFrame(hexWorldTiles);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        drawHexWorld(size);
    }
}
