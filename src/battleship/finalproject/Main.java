package battleship.finalproject;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Player player1, player2;
		int dificulty;
		String coordinate, computerStart;
		boolean isHorizontal, validInput, computerHorizontal, haveWinner;
		
		//Build player1
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter the name of player 1");
		String name = keyboard.nextLine();
		try {
		System.out.println("Choose your difficulty - (Enter the corresponding number)-\n"
				+ "1 - Very Easy. Carrier with 3 guns and 5 spaces\n"
				+ "2 - Easy. Battleship with 2 guns and 4 spaces\n"
				+ "3 - Normal (Default). Crusier with 2 guns and 3 spaces\n"
				+ "4 - Hard. Submarine with 1 gun and 3 spaces\n"
				+ "5 - Very Hard. Destroyer with 1 gun and two spaces"
				);
		dificulty = keyboard.nextInt();
		}catch(Exception e) {
			dificulty = 3;
		}
		if (dificulty == 1){
			player1 = new Player(name, "Carrier", 5, 3);
			player2 = new Player("Computer","Carrier", 5, 3);
		}else if (dificulty == 2) {
			player1 = new Player(name, "Battleship", 4, 2);
			player2 = new Player("Computer", "Battleship", 4, 2);
		}else if (dificulty == 4) {
			player1 = new Player(name, "Submarine", 3, 1);
			player2 = new Player("Computer", "Submarine", 3, 1);
		}else if (dificulty == 5) {
			player1 = new Player(name, "Destroyer", 2, 1);
			player2 = new Player("Computer", "Destroyer", 2, 1);
		}else {
			player1 = new Player(name, "Crusier", 3, 2);
			player2 = new Player("Computer", "Crusier", 3, 2);
		}
		System.out.println(player1.getShipTracker());
		//Place player1 ship
		try {
		System.out.println("This is your part of the ocean.\n"
				+ "Place your ship 1 - horizontal(Default)\n"
				+ "or 2 - vertcal");
		isHorizontal = keyboard.nextInt() == 2 ? false : true;
		keyboard.nextLine();
		}catch (Exception e) {
			isHorizontal = true;
		}
		validInput = false;
		do {
		System.out.println("Enter the Collumn/Row to place your ship.\n"
				+ "Horizontal ships will be placed West to East  from that point.\n"
				+ "Vertical ships will be placed North to South from that point.");
		coordinate = keyboard.nextLine();
		validInput = Gameboard.coordinatesValid(coordinate);
			if(!validInput) {
				System.out.println(coordinate + " is not a valid option.");
			}
		validInput = Player.willFit(coordinate, player1.getSize(), isHorizontal);
			if(!validInput) {
				System.out.println("Your ship will not fit if you use the starting coordinate "+ coordinate + ".");
			}
		}while(!validInput);
		
		player1.placeShip(coordinate, player1.getSize(), isHorizontal, player1.getShipTracker());
		validInput = false;
		
		//Randomly place player2 ship
		do {
			computerStart = Player.generateRandomCoordinate();
			computerHorizontal = Player.generateRandomOneTwo() == 1 ? true : false;
			validInput = Player.willFit(computerStart, player2.getSize(), computerHorizontal);		
		}while(!validInput);
		player2.placeShip(computerStart, player2.getSize(), computerHorizontal, player2.getShipTracker());
		
		//Start game loop
		System.out.println(player1.displayGameboards());
		haveWinner = false;
		do {
			for(int i = 1; i<= player1.getGuns(); i++) {
				validInput = false;
				System.out.println("Enter attack cordinate " + i);
				coordinate = keyboard.nextLine();
				validInput = Gameboard.coordinatesValid(coordinate);
				player1.attackOpponent(coordinate, player2);
			}
			if(player1.getScore() == player2.getSize()) {
				haveWinner = true;
				System.out.println(player1.displayGameboards());
				System.out.println("You Win!");
			}
			System.out.println(player1.displayGameboards());
			if(!haveWinner) {
				System.out.println(player2.getName() + " is attacking!");
				for( int i= 1; i <= player2.getGuns(); i++) {
					if (i== 1) {
						coordinate = player2.getComputerAttackCoordinates(player2.getLastAttack1Cords(), player2.getLastAttack1());
						player2.attackOpponent(coordinate, player1);
						player2.setLastAttack1Cords(coordinate);
						player2.setLastAttack1(player2.getAttackTracker().getCoordinateValue(Gameboard.convertRow(coordinate), Gameboard.convertColumn(coordinate)));
						
					}else if (i==2) {
						coordinate = player2.getComputerAttackCoordinates(player2.getLastAttack2Cords(), player2.getLastAttack2());
						player2.attackOpponent(coordinate, player1);
						player2.setLastAttack2Cords(coordinate);
						player2.setLastAttack2(player2.getAttackTracker().getCoordinateValue(Gameboard.convertRow(coordinate), Gameboard.convertColumn(coordinate)));
					}else if (i == 3) {
						coordinate = player2.getComputerAttackCoordinates(player2.getLastAttack3Cords(), player2.getLastAttack3());
						player2.attackOpponent(coordinate, player1);
						player2.setLastAttack3Cords(coordinate);
						player2.setLastAttack3(player2.getAttackTracker().getCoordinateValue(Gameboard.convertRow(coordinate), Gameboard.convertColumn(coordinate)));
					}
				}
				if(player2.getScore() == player1.getSize()) {
					haveWinner = true;
					System.out.println(player1.displayGameboards());
					System.out.println(player2.getName() + " Wins!");
				}
				System.out.println(player1.displayGameboards());

			}
		}while(!haveWinner);
		
		keyboard.close();
	}
	
}
