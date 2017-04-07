import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver 
{
	private static int[][] stats = new int[3][10];
	private static boolean gameOn = true;
	private static boolean humanWon = false;
	private static int stickCount;
	private static List<Integer[]> computerPlays = new ArrayList<Integer[]>();
	
	public static void createFile()
	{
		try
		{
			File file = new File("stats.txt");
			
			if(file.createNewFile())
			{
				stats = new int[][]{{1, -1, -1, 0, 0, 0, 0, 0, 0, 0}, //1
									{-1, 1, -1, 0, 0, 0, 0, 0, 0, 0}, //2
									{-1, -1, 1, 0, 0, 0, 0, 0, 0, 0}};//3
			}
			else
			{
				stats = new int[][]{{1, -1, -1, 0, 0, 0, 0, 0, 0, 0}, //1
					{-1, 1, -1, 0, 0, 0, 0, 0, 0, 0}, //2
					{-1, -1, 1, 0, 0, 0, 0, 0, 0, 0}};//3
					
				Scanner scanner = new Scanner(file);

				for(int i = 0; i < 3; i++)
				{
					for(int j = 0; j < 10; j++)
					{
						stats[i][j] = scanner.nextInt();
					}
				}
			}			
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Unable to open the file.");
		}
		catch(IOException e)
		{
			System.out.println("Could not read.");
		}
	}
	
	public static void playNIM()
	{
		Scanner scanner = new Scanner(System.in);
		int move = 0;
		int humanPlay;
		
		System.out.println("Welcome to the NIM playing machine!");
		System.out.println("You and I will take turns picking up a number of sticks from a pile, and the winner is whoever picks up the last stick from the pile.");
		System.out.println("There are 10 sticks in the pile to begin. You must pick up between 1 - 3 sticks at a time.");
		
		System.out.println("I will begin picking up the first sticks.");
		
		while(gameOn)
		{
			move = makeMove() + 1;
//			stickCount -= move;
			System.out.println("Adding: " + (move-1) + " " + (stickCount - 1));
			Integer[] a = {move - 1, stickCount - 1};
			computerPlays.add(a);
			stickCount -= move;
			System.out.println("I am picking up " + move + " sticks.");
			if (stickCount == 0)
			{
				gameOn = false;
				break;
			}
			System.out.println("\nThere are " + stickCount + " left. How many would you like to pick up?");
			humanPlay = scanner.nextInt();
			if (humanPlay > 3 || humanPlay < 1)
			{
				System.out.println("Not valid. Please pick 1, 2, or 3");
			}
			else 
			{
				stickCount -= humanPlay;
				System.out.println("You picked " + humanPlay + " sticks.");
				
				if (stickCount == 0)
				{
					humanWon = true;
					gameOn = false;
				}
				System.out.println("There are now " + stickCount + " left.");
			}
		}
		if (humanWon)
		{
			System.out.println("Congratulations! You won!");
		}
		else 
		{
			System.out.println("I have conquered the game of NIM!");
		}
	}
	
	public static int makeMove()
	{
		int temp, index = 0, largest = Integer.MIN_VALUE;
		
		for(int i = 0; i < 3; i++)
		{
			temp = stats[i][stickCount - 1];
			if (temp > largest)
			{
				largest = temp;
				index = i;
			}
		}
		System.out.println("Index: " + index);
		return index;
	}
	
	public static void updateFile()
	{
		for (Integer[] coordinates : computerPlays)
		{
			int i = coordinates[0];
			int j = coordinates[1];
			if (humanWon)
			{
				System.out.println("Before: " + stats[i][j] + " i: " + i + " j: " + j);
				stats[i][j] -= 1;
				System.out.println("After: " + stats[i][j]);
			}
			else
			{
				stats[i][j] += 1;
			}
		}
		
		try
		{
			PrintWriter writer = new PrintWriter("stats.txt");
			
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 10; j++)
				{
					writer.print(stats[i][j] + " ");
				}
				writer.println();
			}
			writer.close();
		}
		catch(IOException e)
		{
			System.out.println("Could not write");
		}

	}
	
	public static void main(String[] args)
	{
		stickCount = 10;
		createFile();
		playNIM();
		updateFile();
		
		for(int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				System.out.print(stats[i][j] + " ");
			}
			System.out.println();
		}
	}
}
