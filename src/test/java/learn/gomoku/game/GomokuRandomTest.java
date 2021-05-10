package learn.gomoku.game;

import learn.gomoku.players.RandomPlayer;
import org.junit.jupiter.api.Test;

class GomokuRandomTest {

    RandomPlayer one = new RandomPlayer();
    RandomPlayer two = new RandomPlayer();
    Gomoku game = new Gomoku(one, two);

    @Test
    void shouldFinish() {
        // Random behavior can't really be tested.
        // This "test" verifies that a game will eventually end with two RandomPlayers.
        while (!game.isOver()) {
            Result result;
            do {
                // Get the current (random) player and generate a random
                // stone from the existing game moves.
                Stone stone = game.getCurrent().generateMove(game.getStones());
                result = game.place(stone);
                System.out.println(result);
            } while (!result.isSuccess());
        }
    }

    @Test
    void makeNames() {
        for (int i = 0; i < 100; i++) {
            RandomPlayer player = new RandomPlayer();
            System.out.println(player.getName());
        }
    }
}
