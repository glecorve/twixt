package logic;

import java.util.LinkedList;
import java.util.List;

public class LogicManager {

	private Moves moves;
	private Moves movesBack;
	private Connections connectionsWhite;
	private Connections connectionsBlack;
	private int[][] board;
	private boolean fw;

	public LogicManager() {
		super();
		reinit();
	}

	public LogicManager clone() {
		LogicManager l_clone = new LogicManager();
		l_clone.moves = moves.clone();
		l_clone.movesBack = movesBack.clone();
		l_clone.connectionsBlack = connectionsBlack.clone();
		l_clone.connectionsWhite = connectionsWhite.clone();
		l_clone.board = board.clone();
		l_clone.fw = fw;

		return l_clone;
	}

	public void reinit() {
		moves = new Moves();
		movesBack = new Moves();
		connectionsWhite = new Connections();
		connectionsBlack = new Connections();
		board = new int[24][24];
		fw = false;
	}

	public void add(int row, int col) {
		add(new int[] { row, col });
	}

	public void add(int[] point) {
		if (isPossibleMove(point)) {
			moves.add(point[0], point[1]);
			if (!fw)
				movesBack = new Moves();
			createBoard();

			if (board[point[0]][point[1]] == 0)
				return;
			// calculate possible connections
			for (int i = -2; i <= 2; ++i) {
				if (i == 0)
					++i;
				for (int j = -2; j <= 2; ++j) {
					if (j == 0)
						++j;
					if (point[0] + i <= 23 && point[0] + i >= 0 && point[1] + j <= 23 && point[1] + j >= 0
							&& Math.abs(i) + Math.abs(j) == 3 && board[point[0] + i][point[1] + j] == moves.getColor()
							&& isPossibleConnection(point, new int[] { point[0] + i, point[1] + j },
									moves.getColor())) {
						getConnections(moves.getColor()).add(point, new int[] { point[0] + i, point[1] + j });
					}
				}
			}
		}
	}

	/**
	 * Validity of the move checker
	 *
	 * @param point
	 *            coordinates of the move
	 * @param playerIndex
	 *            the index of the current player
	 *
	 * @return true the point is valid or false if not
	 */
	public boolean isPossibleMove(int[] point, int color) {
		return isPossibleMove(board, point, color);
	}

	/**
	 * Validity of the move checker
	 *
	 * @param point
	 *            coordinates of the move
	 * @param playerIndex
	 *            the index of the current player
	 * @param swap
	 *            swap flag
	 *
	 * @return true the point is valid or false if not
	 */
	public List<int[]> getAllPossibleMoves(int color) {
		return getAllPossibleMoves(board, color);
	}

	/**
	 * Static method to check the validity of a move
	 *
	 * @param board
	 *            the board
	 * @param point
	 *            coordinates of the move
	 * @param playerIndex
	 *            the index of the current player
	 * @param swap
	 *            swap flag
	 *
	 * @return true the point is valid or false if not
	 */
	public static boolean isPossibleMove(int[][] board, int[] point, int color) {
		return (((color == 1 && point[0] >= 0 && point[0] <= 23 && point[1] > 0 && point[1] < 23)
				|| (color == 2 && point[0] > 0 && point[0] < 23 && point[1] >= 0 && point[1] <= 23))
				&& board[point[0]][point[1]] == 0);
	}

	/**
	 * Validity of the move checker
	 *
	 * @param board
	 *            the board
	 * @param point
	 *            coordinates of the move
	 * @param playerIndex
	 *            the index of the current player
	 * @param swap
	 *            swap flag
	 *
	 * @return true the point is valid or false if not
	 */
	public static List<int[]> getAllPossibleMoves(int[][] board, int color) {
		List<int[]> validMoves = new LinkedList<int[]>();
		int[] point = new int[2];
		for (int i = 0; i < board.length; i++) {
			point[0] = i;
			for (int j = 0; j < board.length; j++) {
				point[1] = j;
				if (isPossibleMove(board, point, color)) {
					validMoves.add(point.clone());
				}
			}
		}
		return validMoves;
	}

	private boolean isPossibleConnection(int[] point1, int[] point2, int color) {
		for (int i = 0; i < getConnections(3 - color).getSize(); ++i) {
			int[] c = getConnections(3 - color).getConnection(i);
			if ((Math
					.signum(point1[1]
							- (point1[0] - c[0])
									* (1.0 * (c[3] - c[1]) / (c[2] - c[0]))
							- c[1]) != Math
									.signum(point2[1]
											- (point2[0] - c[0])
													* (1.0 * (c[3] - c[1]) / (c[2] - c[0]))
											- c[1]))
					&& (Math.signum(
							c[1] - (c[0] - point1[0]) * (1.0 * (point2[1] - point1[1]) / (point2[0] - point1[0]))
									- point1[1]) != Math
											.signum(c[3]
													- (c[2] - point1[0])
															* (1.0 * (point2[1] - point1[1]) / (point2[0] - point1[0]))
													- point1[1])))
				return false;
		}
		return true;
	}

	private void remove() {
		getConnections(moves.getColor()).remove(moves.getMove());
		moves.remove();
		createBoard();
	}

	private void createBoard() {
		board = new int[24][24];
		for (int i = 0; i < moves.getSize(); ++i) {
			// if (moves.getColor(i) > 0 )
			// {
			// System.out.println("(" + moves.getMove(i)[0] + ", " +
			// moves.getMove(i)[1] + ") =>" + moves.getColor(i));
			// }
			board[moves.getMove(i)[0]][moves.getMove(i)[1]] = moves.getColor(i);
		}
	}

	public void back() {
		if (canMoveBack()) {
			movesBack.add(moves.getMove());
			remove();
		}
	}

	public boolean canMoveBack() {
		if (moves.getSize() > 0)
			return true;
		else
			return false;
	}

	public void forward() {
		if (canMoveForward()) {
			fw = true;
			add(movesBack.getMove());
			movesBack.remove();
			fw = false;
		}
	}

	public boolean canMoveForward() {
		if (movesBack.getSize() > 0)
			return true;
		else
			return false;
	}

	public boolean isPossibleMove(int[] point) {
		return isPossibleMove(board, point, moves.getColorOnTurn());
	}

	public Moves getMoves() {
		return moves;
	}

	public Connections getWhiteConnections() {
		return connectionsWhite;
	}

	public Connections getBlackConnections() {
		return connectionsBlack;
	}

	public Connections getConnections(int color) {
		if (color == 1)
			return connectionsWhite;
		else
			return connectionsBlack;
	}

	public Connections cloneConnections(int color) {
		if (color == 1)
			return connectionsWhite.clone();
		else
			return connectionsBlack.clone();
	}

	public int[][] getBoard() {
		return board;
	}
	
	public int getBoardSize() {
		return getBoard().length;
	}

	/**
	 * get the game status
	 *
	 * @return 0 no winner and possible move, 1 player one wins, 2 player two
	 *         wins, -1 draw game
	 */
	public int getStatus() {

		// look if theres a connection from last move to both boarders.
		int[] point = moves.getMove();
		boolean[][] b = new boolean[24][24];

		// Last move done by player one
		if (board[point[0]][point[1]] == 1) {
			b = conCalc(b, point[0], point[1], connectionsWhite);
			for (int i = 0; i < 2; ++i) {
				for (int j = 1; j < 23; ++j) {
					if (b[i][j]) {
						for (int k = 22; k <= 23; ++k) {
							for (int l = 1; l < 23; ++l) {
								if (b[k][l]) {
									return 1; /* Player 2 wins */
								}
							}
						}
						break;
					}
				}
			}

			/* Draw check */
		}
		// Last move done by player two
		else {
			b = conCalc(b, point[0], point[1], connectionsBlack);
			for (int i = 1; i < 23; ++i) {
				for (int j = 0; j < 2; ++j) {
					if (b[i][j]) {
						for (int k = 1; k < 23; ++k) {
							for (int l = 22; l <= 23; ++l) {
								if (b[k][l]) {
									return 2; /* Player 2 wins */
								}
							}
						}
						break;
					}
				}
			}

			/* Draw check */
		}

		/* Check draw */
		boolean is_draw = true;
		int i = 1;
		while ((i < 22) && is_draw) {
			int j = 1;
			while ((j < 22) && is_draw) {
				/* Free case => possible move! */
				if (board[i][j] == 0) {
					is_draw = false;
				}
				j++;
			}

			i++;
		}

		/* Check turn and limits */
		if (is_draw) {
			/* Get next player */
			int next_player = moves.getPlayer() + 1;
			/* Player one */
			if (next_player == 1) {
				i = 0;
				int j = 1;
				while ((j < 22) && is_draw) {
					/* Free case => possible move! */
					if (board[i][j] == 0) {
						is_draw = false;
					}
					j++;
				}

				i = 23;
				j = 1;
				while ((j < 22) && is_draw) {
					/* Free case => possible move! */
					if (board[i][j] == 0) {
						is_draw = false;
					}
					j++;
				}
			}
			/* Player two */
			else {
				int j = 0;
				i = 1;
				while ((i < 22) && is_draw) {
					/* Free case => possible move! */
					if (board[i][j] == 0) {
						is_draw = false;
					}
					i++;
				}

				j = 23;
				i = 1;
				while ((i < 22) && is_draw) {
					/* Free case => possible move! */
					if (board[i][j] == 0) {
						is_draw = false;
					}
					i++;
				}
			}
		}

		return 0;
	}

	/*
	 * public List get_path() { return ( }
	 */

	private boolean[][] conCalc(boolean[][] b, int row, int col, Connections c) {
		if (!b[row][col]) {
			b[row][col] = true;
			int[] z = new int[4];
			for (int i = 0; i < c.getSize(); ++i) {
				z = c.getConnection(i);
				if (row == z[0] && col == z[1]) {
					b = conCalc(b, z[2], z[3], c);
				} else if (row == z[2] && col == z[3]) {
					b = conCalc(b, z[0], z[1], c);
				}
			}
		}
		return b;
	}

}
