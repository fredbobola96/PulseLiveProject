package com.pulselive.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        List<LeagueTableEntry> list = testMatchData();
        tableOutput(list);
    }

    /**
     * Outputs final table to console
     * */
    private static void tableOutput(List<LeagueTableEntry> leagueTableEntries)
    {
        System.out.printf("%-5s %-25s %5s %5s %5s %5s %5s %5s %5s %5s\n", //Header format
                "Pos", "Club", "MP", "W", "D", "L", "F", "A", "GD", "Pts");
        int i = 0;
        if (i < leagueTableEntries.size())
        {
            do
            {
                LeagueTableEntry leagueTableEntry = leagueTableEntries.get(i);
                System.out.printf("%-5d %-25s %5d %5d %5d %5d %5d %5d %+5d %5d\n",
                        (i + 1), //prints team position in league
                        leagueTableEntry.getTeamName(), leagueTableEntry.getPlayed(), leagueTableEntry.getWon(),
                        leagueTableEntry.getDrawn(), leagueTableEntry.getLost(), leagueTableEntry.getGoalsFor(),
                        leagueTableEntry.getGoalsAgainst(), leagueTableEntry.getGoalDifference(), leagueTableEntry.getPoints());
                i++;
            } while (i < leagueTableEntries.size());
        }
    }

    /**
     * Data to be displayed in example league table
     * */
    public static List<LeagueTableEntry> testMatchData()
    {
        /*
          With the current set of fixtures, the final points table should read
          Arsenal - 18pts
          Manchester City - 8pts
          Chelsea - 4pts
          Manchester United - 2pts*/

        // Test Match Data
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

        // Add matches to ArrayList
        ArrayList<Match> matches = new ArrayList<>(Arrays.asList(game1, game2, game3, game4, game5, game6, game7, game8, game9, game10, game11, game12));

        LeagueTable table = new LeagueTable(matches);
        return table.getTableEntries();
    }
}