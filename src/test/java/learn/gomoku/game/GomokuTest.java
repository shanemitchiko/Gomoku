package learn.gomoku.game;

import learn.gomoku.players.HumanPlayer;
import learn.gomoku.players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GomokuTest {

    HumanPlayer one = new HumanPlayer("Dori");
    HumanPlayer two = new HumanPlayer("Nemo");
    Gomoku game = new Gomoku(one, two);

    @Test
    void blackShouldStart() {
        assertTrue(game.isBlacksTurn());
    }

    @Test
    void shouldSwap() {
        Player previous = game.getCurrent();
        game.swap();
        Player next = game.getCurrent();
        assertNotEquals(previous, next);
    }

    @Test
    void shouldNotPlayOffTheBoard() {
        Result expected = new Result("Stone is off the board.");
        Result actual = game.place(new Stone(55, 4, true));
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotPlayOutOfTurn() {
        Result expected = new Result("Wrong player.");
        Result actual = game.place(new Stone(5, 5, false)); // invalid move, it's black's turn
        assertEquals(expected, actual);

        actual = game.place(new Stone(5, 5, true)); // valid move
        assertTrue(actual.isSuccess());

        actual = game.place(new Stone(6, 6, true)); // invalid move, it's white's turn
        assertEquals(expected, actual);
    }

    @Test
    void blackShouldWinInFiveMoves() {
        Player black = game.getCurrent();

        game.place(new Stone(0, 0, true));
        game.place(new Stone(1, 0, false));
        game.place(new Stone(0, 1, true));
        game.place(new Stone(1, 1, false));
        game.place(new Stone(0, 2, true));
        game.place(new Stone(1, 2, false));
        game.place(new Stone(0, 3, true));
        game.place(new Stone(1, 3, false));
        Result actual = game.place(new Stone(0, 4, true));

        assertTrue(actual.isSuccess());
        assertTrue(game.isOver());
        assertEquals(black, game.getWinner());
    }

    @Test
    void stoneCountShouldMatch() {
        game.place(new Stone(0, 0, true));
        game.place(new Stone(1, 0, false));
        game.place(new Stone(0, 1, true));
        game.place(new Stone(1, 1, false));
        game.place(new Stone(0, 2, true));
        assertEquals(5, game.getStones().size());

        game.place(new Stone(10, 0, false));
        game.place(new Stone(11, 0, true));
        game.place(new Stone(10, 1, false));
        game.place(new Stone(11, 1, true));
        assertEquals(9, game.getStones().size());
    }

    @Test
    void shouldEndInDraw() {

        boolean isBlack = true;
        int[] rows = {0, 2, 1, 3, 4, 6, 5, 7, 8, 10, 9, 11, 12, 14, 13};
        for (int row : rows) {
            for (int col = 0; col < Gomoku.WIDTH; col++) {
                game.place(new Stone(row, col, isBlack));
                isBlack = !isBlack;
            }
        }

        assertTrue(game.isOver());
        assertNull(game.getWinner());
    }
}