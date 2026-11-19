#include <array>
#include <iostream>
#include <limits>

constexpr int ROWS = 6;
constexpr int COLS = 7;
constexpr char DIVIDER = '|';

enum class Cell { Empty, Red, Yellow };

char toChar(Cell c) {
  switch (c) {
    case Cell::Empty:  return ' ';
    case Cell::Red:    return 'R';
    case Cell::Yellow: return 'Y';
  }
  return '?';
}

class Board {
public:
  Board() { cells_.fill(Cell::Empty); }

  Cell at(int r, int c) const { return cells_[r * COLS + c]; }

  void print() const {
    for (int r = 0; r < ROWS; ++r) {
      std::cout << DIVIDER;
      for (int c = 0; c < COLS; ++c) {
        std::cout << ' ' << toChar(at(r, c)) << ' ' << DIVIDER;
      }
      std::cout << '\n';
    }
    for (int c = 0; c < COLS; ++c) {
      std::cout << DIVIDER << ' ' << c + 1 << ' ';
    }
    std::cout << DIVIDER << '\n';
  }
    
  bool drop(int col, Cell player) {
      if (col < 0 || col >= COLS) { return false; }
      for (int r = ROWS - 1; r >= 0; --r) {
          if (at(r, col) == Cell::Empty) {
              cells_[r * COLS + col] = player;
              return true;
          }
      }
      return false;
  }

  bool hasWon(Cell player) const {
    constexpr int dirs[4][2] = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
    for (int r = 0; r < ROWS; ++r) {
      for (int c = 0; c < COLS; ++c) {
        if (at(r, c) != player) { continue; }
        for (const auto& d : dirs) {
          int k = 1;
          for (; k < 4; ++k) {
            int rr = r + d[0] * k;
            int cc = c + d[1] * k;
            if (rr < 0 || rr >= ROWS || cc < 0 || cc >= COLS || at(rr, cc) != player) { break; }
          }
          if (k == 4) { return true; }
        }
      }
    }
    return false;
  }

private:
  std::array<Cell, ROWS * COLS> cells_;
};

int main() {
    Board board;
    Cell current = Cell::Red;
    int moves = 0;

    board.print();

    while (moves < ROWS * COLS) {
        std::cout << (current == Cell::Red ? "Red" : "Yellow")
                  << ", pick a column (1-" << COLS << "): ";

        int input = 0;
        if (!(std::cin >> input)) {
            if (std::cin.eof()) { break; }
            std::cin.clear();
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            std::cout << "Enter a number.\n";
            continue;
        }

        if (!board.drop(input - 1, current)) {
            std::cout << "Invalid move (out of range or column full).\n";
            continue;
        }

        ++moves;
        board.print();
        if (board.hasWon(current)) {
          std::cout << (current == Cell::Red ? "Red" : "Yellow") << " wins!\n";
          return 0;
        }
        current = (current == Cell::Red) ? Cell::Yellow : Cell::Red;
    }

    if (moves == ROWS * COLS) { std::cout << "Draw!\n"; }
    return 0;
}
