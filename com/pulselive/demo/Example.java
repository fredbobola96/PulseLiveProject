package com.pulselive.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Example
{
    public static void main(String[] args)
    {
        List<LeagueTableEntry> list = testMatchData();
        tableOutput(list);
    }

    private static void tableOutput(List<LeagueTableEntry> tableEntries)
    {
        System.out.printf("%-4s %-25s %5s %5s %5s %5s %5s %5s %5s %5s\n",
                "#", "Team", "Pl", "W", "D", "L", "F", "A", "GD", "Pts");
        int i = 0;
        if (i < tableEntries.size())
        {
            do
            {
                LeagueTableEntry tableEntry = tableEntries.get(i);
                System.out.printf(stringFormat() + "\n",
                        (i + 1),
                        tableEntry.getTeamName(),
                        tableEntry.getPlayed(),
                        tableEntry.getWon(),
                        tableEntry.getDrawn(),
                        tableEntry.getLost(),
                        tableEntry.getGoalsFor(),
                        tableEntry.getGoalsAgainst(),
                        tableEntry.getGoalDifference(),
                        tableEntry.getPoints());
                i++;
            } while (i < tableEntries.size());
        }
    }

    private static String stringFormat()
    {
        return "%-4d %-25s %5d %5d %5d %5d %5d %5d %+5d %5d";
    }

    public static List<LeagueTableEntry> testMatchData()
    {
        /*
          With the current set of fixtures, the final points table should read
          Arsenal - 18pts
          Manchester City - 8pts
          Chelsea - 4pts
          Manchester United - 2pts*/

        // Test Data
        Match game1 = new Match("Arsenal", "Chelsea", 2, 1); //Arsenal win
        Match game2 = new Match("Chelsea", "Arsenal", 1, 3); //arsenal win
        Match game3 = new Match("Manchester United", "Manchester City", 1, 3); //city win
        Match game4 = new Match("Manchester City", "Manchester United", 1, 0); //city win
        Match game5 = new Match("Arsenal", "Manchester United", 4, 2); //arsenal win
        Match game6 = new Match("Manchester United", "Arsenal", 1, 3); //arsenal win
        Match game7 = new Match("Chelsea", "Manchester City", 3, 3); // chelsea city draw
        Match game8 = new Match("Manchester City", "Chelsea", 3, 3); // city chelsea draw
        Match game9 = new Match("Chelsea", "Manchester United", 1, 1); // united chelsea draw
        Match game10 = new Match("Manchester United", "Chelsea", 2, 2); // united chelsea draw
        Match game11 = new Match("Arsenal", "Manchester City", 1, 0); //arsenal win
        Match game12 = new Match("Manchester City", "Arsenal", 2, 4); //arsenal win

        // Add matches to list
        List<Match> matches = new ArrayList<>(Arrays.asList(game1, game2, game3, game4, game5, game6, game7, game8, game9, game10, game11, game12));

        LeagueTable table = new LeagueTable(matches);
        return table.getTableEntries();
    }
}