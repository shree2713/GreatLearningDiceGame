package com.game.dice.controller;

import com.game.dice.constant.ApplicationConstants;
import com.game.dice.utils.SortUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class Dice
{
    private static Map<String, Integer> result = new HashMap<>();
    private static List<String> previous_State = new ArrayList<>();
    private static Random random = new Random();
    private static Scanner scan = new Scanner(System.in);

    private static SortUtils sortUtils = new SortUtils();

    private static int count  = 0;


    public static void main(String[] args)
    {
        Dice diceObject = new Dice();

        int NoOfPlayer = Integer.parseInt(args[0]);
        int targetNumber = Integer.parseInt(args[1]);
        int turn = random.nextInt(NoOfPlayer);

        boolean[] flag = new boolean[NoOfPlayer];
        String[] players = new String[NoOfPlayer];

        for (int i = 0; i < NoOfPlayer; i++)
            flag[i] = true;

        for (int i = 0; i < NoOfPlayer; i++)
        {
            players[i] = String.format(ApplicationConstants.PLAYER_NAME, i+1);
        }

        for (String player: players)
        {
            result.put(player, 0);
        }

        while (count < NoOfPlayer)
        {
            if (flag[turn]) {
                System.out.println(String.format(ApplicationConstants.DISPLAY_MESSAGE, players[turn]));
                String input = scan.nextLine();

                if (input.equals("r")) {
                    int currentNumber = random.nextInt(6) + 1;
                    System.out.println(String.format(ApplicationConstants.DICE_VALUE, currentNumber));

                    int tempResult = currentNumber + result.get(players[turn]);

                    switch (currentNumber) {
                        case 1 :
                            diceObject.diceFaceOne(players, turn, targetNumber, flag, tempResult);
                            turn = (turn+1) % NoOfPlayer;
                            break;
                        case 6 :
                            diceObject.diceFaceSix(players, turn, targetNumber, flag, tempResult);
                            if (tempResult > targetNumber){
                                turn = (turn+1) % NoOfPlayer;
                            }
                            break;
                        default:
                            previous_State.remove(players[turn]);
                            diceObject.moveDice(players, turn, targetNumber, flag, tempResult);
                            turn = (turn+1) % NoOfPlayer;
                            break;
                    }
                } else {
                    System.out.println(ApplicationConstants.INVALID_KEY);
                }
            } else {
                if (result.get(players[turn]) != targetNumber) {
                    flag[turn] = true;
                }
                turn = (turn+1) % NoOfPlayer;
            }
        }
    }

    private void diceFaceOne(String[] players, int turn, int targetNumber, boolean[] flag, int tempResult)
    {
        moveDice(players, turn, targetNumber, flag, tempResult);
        if (previous_State.contains(players[turn])) {
            previous_State.remove(players[turn]);
            flag[turn] = false;
            System.out.println(ApplicationConstants.PENALITY);
        } else {
            previous_State.add(players[turn]);
        }
    }

    private void diceFaceSix(String[] players, int turn, int targetNumber, boolean[] flag, int tempResult)
    {
        previous_State.remove(players[turn]);
        moveDice(players, turn, targetNumber, flag, tempResult);
        if (tempResult < targetNumber) {
            System.out.println(String.format(ApplicationConstants.MORE_CHANCE, players[turn]) + "\n");
        }
    }

    private void moveDice(String[] players, int turn, int targetNumber, boolean[] flag, int tempResult ) {
        if (tempResult < targetNumber) {
            result.put(players[turn], tempResult);
        } else if (tempResult == targetNumber) {
            flag[turn] = false;
            result.put(players[turn], tempResult);
            System.out.println(String.format(ApplicationConstants.WON_MESSAGE, players[turn], ++count));
        }
        result = sortUtils.sortMap(result);
        displayTable(result);
        System.out.println();
    }

    private void displayTable(Map<String, Integer> results)
    {
        System.out.println("-------------------------");
        System.out.println("PlayerName | Value | Rank");
        int rank = 1;
        for (Map.Entry<String, Integer> result : results.entrySet()) {
            System.out.println(result.getKey() + " | " + result.getValue() + " | " +  rank);
            rank++;
        }
    }
}
