package com.chess.engine.player.ai;

import com.chess.engine.board.Board;

public interface BoardEvaluator {
    int evaluate(Board board, int depth);
    // heuristic nhận tổng số quân cờ của máy trên bàn cờ bằng cách tính điểm trừ các quân cờ
}
