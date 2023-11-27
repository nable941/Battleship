package battleship.finalproject;

public class Gameboard {
	private int gridSize;
	private char[][] gameboard;
	
	//Constructors
	public Gameboard() {
		this(11);
	}
	//This is a future use constructor for flex goals
	public Gameboard(int size) {
		if (size < 11) {
			throw new IllegalArgumentException("The gameboard size must be 10x10 or greater.");
		}
		initializeArray(size);
		gridSize = size;
	}
	
	//Generate base gameboard data
    private void initializeArray(int size) {
    	gameboard = new char[size][size];
    	for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0){
                    switch (j){
                        case 1: gameboard[i][j] = 'A'; break;
                        case 2: gameboard[i][j] = 'B'; break;
                        case 3: gameboard[i][j] = 'C'; break;
                        case 4: gameboard[i][j] = 'D'; break;
                        case 5: gameboard[i][j] = 'E'; break;
                        case 6: gameboard[i][j] = 'F'; break;
                        case 7: gameboard[i][j] = 'G'; break;
                        case 8: gameboard[i][j] = 'H'; break;
                        case 9: gameboard[i][j] = 'I'; break;
                        case 10: gameboard[i][j] = 'J'; break;
                        default: gameboard[i][j] = ' ';
                    }
                }
                else if(j == 0){
                    switch(i){
                        case 1: gameboard[i][j] = '0'; break;
                        case 2: gameboard[i][j] = '1'; break;
                        case 3: gameboard[i][j] = '2'; break;
                        case 4: gameboard[i][j] = '3'; break;
                        case 5: gameboard[i][j] = '4'; break;
                        case 6: gameboard[i][j] = '5'; break;
                        case 7: gameboard[i][j] = '6'; break;
                        case 8: gameboard[i][j] = '7'; break;
                        case 9: gameboard[i][j] = '8'; break;
                        case 10: gameboard[i][j] = '9'; break;
                        default: gameboard[i][j] = ' ';
                    }
                }else
                gameboard[i][j] = '~';
            }
        }
    }
	//Getters & Setters
	public int getGridsize() {
		return gridSize;
	}

	public void setGridsize(int gridsize) {
		this.gridSize = gridsize;
	}

	public char[][] getGameboard() {
		return gameboard;
	}

	public void setGameboard(char[][] gameboard) {
		this.gameboard = gameboard;
	}
	
	public char getCoordinateValue(int row, int col) {
		if (row <0 ||
				row >= gameboard.length ||
				col < 0 ||
				col >= gameboard.length) {
				throw new IllegalArgumentException("Invalid coordinates");
			}
		return gameboard[row][col];
	}
	
	public void setCoordinateValue(int row, int col, char state) {
		if (row <0 ||
			row >= gameboard.length ||
			col < 0 ||
			col >= gameboard.length) {
			throw new IllegalArgumentException("Invalid coordinates");
		}
		gameboard[row][col] = state;
	}
	
	@Override
    public String toString() {
    	StringBuilder result = new StringBuilder();
        for (int i = 0; i < gameboard.length; i++) {
            for (int j = 0; j < gameboard[i].length; j++) {
                result.append(gameboard[i][j]);
                if (j < gameboard[i].length - 1) {
                    result.append(" "); 
                }
            }

            if (i < gameboard.length - 1) {
                result.append("\n"); 
            }
        }

        return result.toString();
    }
	
	//Check if entered coordinate is a valid coordinate for the gameboard
	public static boolean coordinatesValid(String coordinates) {
		int col = convertColumn(coordinates);
		int row = convertRow(coordinates);
		if (col < 1 || col > 10 || row < 1 || row > 10) {
            return false;
        }else {
        	return true;
        }
		
	}
	//Extract letter from coordinate string and convert it to the corresponding column index
	public static int convertColumn(String coordinate) {
		char columnLetter = Character.toLowerCase(coordinate.charAt(0));
		return columnLetter -  'a' +1;
	}
	//Extract number from coordinate string and convert it to the corresponding row index
	public static int convertRow(String coordinate) {
		char row = coordinate.charAt(1);
		int intValue = Character.getNumericValue(row)+1;
		return intValue;
	}

	}
	
	

