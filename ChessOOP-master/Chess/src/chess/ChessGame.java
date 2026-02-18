private String[][] board;
	private boolean isChess960;

	public ChessGame(boolean isChess960) {
		this.isChess960 = isChess960;
		this.board = new String[8][8];
		setupBoard();
	}

	public void setupBoard() {
		for (String[] row : board) Arrays.fill(row, null);
		initializeBoard();
	}

    private void initializeBoard()
    {
        String[] backRank = isChess960 ? getRandomChess960Rank() : new String[]{"R", "N", "B", "Q", "K", "B", "N", "R"};

        for (int i = 0; i < 8; i++)
        {
            board[7][i] = "W" + backRank[i];
            board[0][i] = "B" + backRank[i];
        }

        Arrays.fill(board[6], "WP");
        Arrays.fill(board[1], "BP");
    }

    private String[] getRandomChess960Rank()
    {
        Random rand = new Random();
        String[] rank = new String[8];
        List<Integer> freeSquares = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));

        // White Squares
        int b1 = rand.nextInt(4) * 2;
        // Black Squares
        int b2 = rand.nextInt(4) * 2 + 1;

        rank[b1] = "B";
        rank[b2] = "B";
        freeSquares.remove(Integer.valueOf(b1));
        freeSquares.remove(Integer.valueOf(b2));

        // Space for Queen
        rank[freeSquares.remove(rand.nextInt(freeSquares.size()))] = "Q";

        // Space for knights
        rank[freeSquares.remove(rand.nextInt(freeSquares.size()))] = "N";
        rank[freeSquares.remove(rand.nextInt(freeSquares.size()))] = "N";

        // Remaining spaces for Rooks and King
        Collections.sort(freeSquares);
        rank[freeSquares.get(0)] = "R";
        rank[freeSquares.get(1)] = "K";
        rank[freeSquares.get(2)] = "R";

        return rank;
    }

	private void setupChess960() {
		String[] pieces = {"R","N","B","Q","K","B","N","R"};
		String[] backRank = new String[8];
		Random rand = new Random();

		// 1. Place bishops on opposite colors
		int b1 = rand.nextInt(4) * 2; // even
		int b2 = rand.nextInt(4) * 2 + 1; // odd
		backRank[b1] = "B";
		backRank[b2] = "B";

		// 2. Place queen
		int q;
		do { q = rand.nextInt(8); } while (backRank[q] != null);
		backRank[q] = "Q";

		// 3. Place knights
		int n1, n2;
		do { n1 = rand.nextInt(8); } while (backRank[n1] != null);
		backRank[n1] = "N";
		do { n2 = rand.nextInt(8); } while (backRank[n2] != null);
		backRank[n2] = "N";

		// 4. Place rooks and king (king between rooks)
		int[] empty = new int[3];
		int idx = 0;
		for (int i = 0; i < 8; i++) if (backRank[i] == null) empty[idx++] = i;
		Arrays.sort(empty);
		backRank[empty[0]] = "R";
		backRank[empty[1]] = "K";
		backRank[empty[2]] = "R";

		// Place on board
		for (int i = 0; i < 8; i++) {
			board[7][i] = "W" + backRank[i];
			board[6][i] = "WP";
			board[1][i] = "BP";
			board[0][i] = "B" + backRank[i];
		}
	}

	public String[][] getBoard() {
		return board;
	}

	public boolean isChess960() {
		return isChess960;
	}
