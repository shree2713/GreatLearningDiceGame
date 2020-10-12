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

        int numOfPlayer = Integer.parseInt(args[0]);
        int targetNumber = Integer.parseInt(args[1]);

        if (numOfPlayer <= 0 || targetNumber <= 0) {
            System.out.println(ApplicationConstants.INVALID_ARGUMENTS);
        } else {
            String[] playersName = new String[numOfPlayer];
            boolean[] flagTurn = new boolean[numOfPlayer];

            for (int i = 0; i < numOfPlayer; i++) {
                flagTurn[i] = true;
            }

            for (int i = 0; i < numOfPlayer; i++) {
                playersName[i] = String.format(ApplicationConstants.PLAYER_NAME, i + 1);
            }

            for (String player : playersName) {
                result.put(player, 0);
            }

            int turn = random.nextInt(numOfPlayer);
            while (count < numOfPlayer) {
                if (flagTurn[turn]) {
                    System.out.println(String.format(ApplicationConstants.DISPLAY_MESSAGE, playersName[turn]));
                    String input = scan.nextLine();

                    if (input.equals("r")) {
                        int currentNumber = random.nextInt(6) + 1;
                        System.out.println(String.format(ApplicationConstants.DICE_VALUE, currentNumber));

                        int tempResult = currentNumber + result.get(playersName[turn]);

                        switch (currentNumber) {
                            case 1:
                                diceObject.diceFaceOne(playersName, turn, targetNumber, flagTurn, tempResult);
                                turn = (turn + 1) % numOfPlayer;
                                break;
                            case 6:
                                diceObject.diceFaceSix(playersName, turn, targetNumber, flagTurn, tempResult);
                                if (tempResult > targetNumber) {
                                    turn = (turn + 1) % numOfPlayer;
                                }
                                break;
                            default:
                                previous_State.remove(playersName[turn]);
                                diceObject.countMove(playersName, turn, targetNumber, flagTurn, tempResult);
                                turn = (turn + 1) % numOfPlayer;
                                break;
                        }
                    } else {
                        System.out.println(ApplicationConstants.INVALID_KEY);
                    }
                } else {
                    if (result.get(playersName[turn]) != targetNumber) {
                        flagTurn[turn] = true;
                    }
                    turn = (turn + 1) % numOfPlayer;
                }
            }
        }
    }

    private void diceFaceOne(String[] playersName, int turn, int targetNumber, boolean[] flagTurn, int tempResult)
    {
        countMove(playersName, turn, targetNumber, flagTurn, tempResult);
        if (previous_State.contains(playersName[turn])) {
            previous_State.remove(playersName[turn]);
            flagTurn[turn] = false;
            System.out.println(ApplicationConstants.PENALITY);
        } else {
            previous_State.add(playersName[turn]);
        }
    }

    private void diceFaceSix(String[] playersName, int turn, int targetNumber, boolean[] flagTurn, int tempResult)
    {
        previous_State.remove(playersName[turn]);
        countMove(playersName, turn, targetNumber, flagTurn, tempResult);
        if (tempResult < targetNumber) {
            System.out.println(String.format(ApplicationConstants.MORE_CHANCE, playersName[turn]) + "\n");
        }
    }

    private void countMove(String[] playersName, int turn, int targetNumber, boolean[] flagTurn, int tempResult )
    {
        if (tempResult < targetNumber) {
            result.put(playersName[turn], tempResult);
        } else if (tempResult == targetNumber) {
            flagTurn[turn] = false;
            result.put(playersName[turn], tempResult);
            System.out.println(String.format(ApplicationConstants.WON_MESSAGE, playersName[turn], ++count));
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
