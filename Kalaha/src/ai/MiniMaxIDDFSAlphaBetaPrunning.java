/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package ai;

import static ai.MiniMax.DEPTH;
import java.util.HashMap;
import java.util.Map;
import kalaha.GameState;

/**
 * @author Optimistic
 *
 * Using minimax depth-first-search to find the best move for AI client
 */
class MiniMaxIDDFSAlphaBetaPrunning extends MiniMax {

    public MiniMaxIDDFSAlphaBetaPrunning(int MAX) {
        super(MAX);
    }

    /**
     * works through valid game states to find the best move for the current
     * game state
     *
     * @param gameState
     * @param depth
     *
     * @return utilityValue;
     */
    public int findBestMove(GameState board, int depth, int maxDepth, long endTime, int ALPHA, int BETA) {
        if (board.gameEnded() || depth >= maxDepth) {
            return getUtilityValue(board);
        }

        int boardUtility = (board.getNextPlayer() == MAX) ? -100 : 100;
        int bestBoardMove = -1;
        Map<Integer, Integer> utils = new HashMap<>();

        long currentTime = System.currentTimeMillis();

        if ((endTime - currentTime) > 0) {

            while (maxDepth < DEPTH) {
                for (int move : this.getPossibleMoves(board)) {
                    GameState clone = board.clone();
                    clone.makeMove(move);

                    int moveUtility = findBestMove(clone, depth + 1, maxDepth, endTime, ALPHA, BETA);
                    utils.put(move, moveUtility);

                    if (board.getNextPlayer() == MAX) {
                        if (boardUtility < moveUtility) {
                            boardUtility = moveUtility;
                            bestBoardMove = move;
                        }

                        if (moveUtility >= BETA) {
                            return moveUtility;
                        }

                        if (moveUtility > ALPHA) {
                            ALPHA = moveUtility;
                        }
                    }

                    if (board.getNextPlayer() == MIN) {
                        if (boardUtility > moveUtility) {
                            boardUtility = moveUtility;
                            bestBoardMove = move;
                        }

                        if (moveUtility <= ALPHA) {
                            return moveUtility;
                        }

                        if (moveUtility < BETA) {
                            BETA = moveUtility;
                        }
                    }
                }

//                System.out.println("MAX DEPTH " + maxDepth + " DEPTH " + depth);
//                System.out.println(utils);
                this.bestMove = bestBoardMove;
                maxDepth++;
            }
        } else {
            System.out.println("Exiting function and returning the existing best move at maxdepth " + maxDepth);
        }

        return boardUtility;
    }
}
