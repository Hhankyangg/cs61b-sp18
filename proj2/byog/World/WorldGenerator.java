package byog.World;

import byog.Core.Game;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class WorldGenerator {
    private static final int maxRoomNum = 15;
    private final int width;
    private final int height;
    private final int roomNum;
    private final Random randomGenerator;
    private TETile[][] world;
    public Room[] rooms;

    public WorldGenerator(Random randomGenerator) {
        this.width = Game.WIDTH;
        this.height = Game.HEIGHT;
        this.randomGenerator = randomGenerator;
        roomNum = this.randomGenerator.nextInt(maxRoomNum) + Game.BIAS;
        rooms = new Room[roomNum];
        putAllNOTHING();
    }

    private void putAllNOTHING() {
        this.world = new TETile[width][height];
        for (int row = 0; row < this.height; row += 1) {
            for (int column = 0; column < this.width; column += 1) {
                this.world[column][row] = Tileset.NOTHING;
            }
        }
    }

    private void generateRooms() {
        for (int i = 0; i < roomNum; i += 1) {
            rooms[i] = new Room(randomGenerator);
            rooms[i].generate(world);
        }
    }


    public TETile[][] generator() {
        generateRooms();
        return world;
    }
}
