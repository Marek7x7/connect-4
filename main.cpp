#include <iostream>
#include <algorithm>

static const int ROW = 8;
static const int COL = 8;

static const char EMPTY_SPACE = ' ';
static const char DEVIDER = '|';

void printBoard(const char board[ROW][COL]) {
  for (int r = 0; r < ROW; ++r) {
    std::cout << DEVIDER;
    for (int c = 0; c < COL; ++c) {
      std::cout << " " << board[r][c] << " " << DEVIDER;
    }
    
    std::cout << "\n";
  }
  for (int i = 0; i < COL; ++i) {
    std::cout << DEVIDER << EMPTY_SPACE << i + 1 << EMPTY_SPACE;
  }
  std::cout << DEVIDER << "\n";
}

int main() {
  char board[ROW][COL];
  std::fill(&board[0][0], &board[0][0] + ROW * COL, EMPTY_SPACE);

  


  printBoard(board);

  return 0;
}
