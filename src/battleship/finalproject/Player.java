package battleship.finalproject;
import java.util.Random;

public class Player extends Ship {
	
	private String name;
	private Gameboard shipTracker;
	private Gameboard attackTracker;
	private int score;
	private char firstAttack;
	private String firstAttackCords;
	private char secondAttack;
	private String secondAttackCords;
	private char thirdAttack;
	private String thirdAttackCords;
	
	//Constructor
	public Player(String playerName, String shipName, int shipSize, int shipGuns) {
		super(shipName, shipSize, shipGuns);
		this.name = playerName;
		this.shipTracker = new Gameboard();
		this.attackTracker = new Gameboard();
		this.score = 0;
		this.firstAttack = '~';
		this.firstAttackCords = "";
		this.secondAttack = '~';
		this.secondAttackCords = "";
		this.thirdAttack = '~';
		this.thirdAttackCords = "";
	}
	
	//Getters and Setters
	public int getScore() {
		return score;
	}

	public void incrementScore() {
		this.score ++;
	}

	public String getName() {
		return name;
	}

	public Gameboard getShipTracker() {
		return shipTracker;
	}

	public Gameboard getAttackTracker() {
		return attackTracker;
	}
	

	public char getFirstAttack() {
		return firstAttack;
	}

	public void setFirstAttack(char firstAttack) {
		this.firstAttack = firstAttack;
	}

	public String getFirstAttackCords() {
		return firstAttackCords;
	}

	public void setFirstAttackCords(String firstAttackCords) {
		this.firstAttackCords = firstAttackCords;
	}

	public char getSecondAttack() {
		return secondAttack;
	}

	public void setSecondAttack(char secondAttack) {
		this.secondAttack = secondAttack;
	}

	public String getSecondAttackCords() {
		return secondAttackCords;
	}

	public void setSecondAttackCords(String secondAttackCords) {
		this.secondAttackCords = secondAttackCords;
	}

	public char getThirdAttack() {
		return thirdAttack;
	}

	public void setThirdAttack(char thirdAttack) {
		this.thirdAttack = thirdAttack;
	}

	public String getThirdAttackCords() {
		return thirdAttackCords;
	}

	public void setThirdAttackCords(String thirdAttackCords) {
		this.thirdAttackCords = thirdAttackCords;
	}

	//Test if ship placement will fit on the board
	public static boolean willFit(String coordinates, int size, boolean isHorizontal) {
		int col = Gameboard.convertColumn(coordinates);
		int row = Gameboard.convertRow(coordinates);

		if (col < 1 || col > 10) {
            return false;
        }
		if (row < 0 || row > 9) {
			return false;
		}
		if (!isHorizontal) {
			if (row + size <= 9) {
				return true;
			}else {
				return false;
			}
		}else {

			if (col + size <= 10) {
				return true;
			}else {
				return false;
			}
		}
		
	}
	
	//Place the ship on the gameboard
	public void placeShip (String coordinates, int size, boolean isHorizontal, Gameboard gameboard) {
		int col = Gameboard.convertColumn(coordinates);
		int row = Gameboard.convertRow(coordinates);
		if(isHorizontal) {
			for(int j = col; j < col+size; j++) {
				gameboard.setCoordinateValue(row, j, 'S');
			}
		}else {
			for(int i = row; i < row + size; i++) {
				gameboard.setCoordinateValue(i, col, 'S');
			}
		}
	}
	
	//Generate coordinate for computer user
	public static String generateRandomCoordinate() {
		Random rand = new Random();
		int randInt = rand.nextInt(11)+ 1;		
		char letter = (char)('a' + randInt -1);
		randInt = rand.nextInt(10);
		
		String coordinate = ""+letter+randInt;
		return coordinate;
		
	}
	//Generate a 1 or 2 for computer user
	public static int generateRandomOneTwo() {
		Random rand = new Random();
		int randInt = rand.nextInt(3)+1;
		return randInt;
	}
	
	//Display the gameboards showing your ocean and a representation of the opponents ocean
	public String displayGameboards() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < shipTracker.getGridsize(); i++) {
            for (int j = 0; j < shipTracker.getGridsize(); j++) {
                result.append(shipTracker.getCoordinateValue(i, j)).append(" ");
            }
            result.append(" *|* ");
            for (int j = 0; j < attackTracker.getGridsize(); j++) {
                result.append(attackTracker.getCoordinateValue(i, j)).append(" ");
            }
            result.append("\n");
        }
        result.append("Player2 Attacks            Your Attacks");
        return result.toString();
    }
	
	//Update gameboards with attack results
	public void attackOpponent(String coordinate, Player opponent) {
		char coordinateResult;
		int col = Gameboard.convertColumn(coordinate);
		int row = Gameboard.convertRow(coordinate);
		coordinateResult = opponent.shipTracker.getCoordinateValue(row, col);
		if (coordinateResult == '~') {
			this.attackTracker.setCoordinateValue(row, col, 'X');	
			opponent.shipTracker.setCoordinateValue(row, col, 'X');
		}else if(coordinateResult == 'S') {
			this.attackTracker.setCoordinateValue(row, col, 'H');
			opponent.shipTracker.setCoordinateValue(row, col, 'H');
			this.incrementScore();
		}
	}
	
	//Attack decision maker
	public String getComputerAttackCoordinates(String lastAttackCords, char lastAttack) {
		String attackCord;
		boolean validAttack;
		int chooseDirection, row, col;
		Random rand = new Random();
		char letter;
		//If the computer hasn't attacked previously or missed on the last attack the attack a random coordinate
		if ( lastAttack == '~') {
			validAttack = false;
			do {
			attackCord = generateRandomCoordinate();
			col = Gameboard.convertColumn(attackCord);
			row = Gameboard.convertRow(attackCord);
			if (this.attackTracker.getCoordinateValue(row, col) == '~') {
				validAttack = true;
			}
			}while(!validAttack);
			return attackCord;
		}else {
			//If the prior attack hit use a semi random decision maker to attack in probable coordinates
			col = Gameboard.convertColumn(lastAttackCords);
			row = Gameboard.convertRow(lastAttackCords);
			validAttack = false;
			do {
				chooseDirection = rand.nextInt(4);
				if(chooseDirection == 0 && row -1 >0 && this.attackTracker.getCoordinateValue(row-1, col) == '~') {
					letter = (char)('a' + col - 1);
					attackCord = "" + letter + (row-1);
					validAttack = true;
				}else if (chooseDirection == 2 && row +1 <10 && this.attackTracker.getCoordinateValue(row+1, col) == '~') {
					letter = (char)('a' + col-1);
					attackCord = "" + letter + (row+1);
					validAttack = true;
				}else if(chooseDirection == 3 && col -1 >0 && this.attackTracker.getCoordinateValue(row, col-1) == '~') {
					letter = (char)('a' + col-2);
					attackCord = "" + letter + row;
					validAttack = true;
				}else if(chooseDirection == 4 && col +1 <10 && this.attackTracker.getCoordinateValue(row, col+1) == '~') {
					letter = (char)('a' + col);
					attackCord = "" + letter + row;
					validAttack = true;
				}else {
					attackCord = "";
				}

			}while(!validAttack);
			return attackCord;
		}
	}	
}
