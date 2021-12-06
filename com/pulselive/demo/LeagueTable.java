package com.pulselive.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author Adesoji Frederick Akinbobola
 */

public class LeagueTable
{
    private final HashMap<String, LeagueTableEntry> leagueTable; //Table hashmap, teamName as key

    /**
     * Initiates a new league table with List of matches
     *
     * @param matches list of matches
     */
    public LeagueTable(final List<Match> matches)
    {
        leagueTable = new HashMap<>();
        matches.forEach(this::matchResultParser);
    }

    /**
     * Parse individual match results and updates the table accordingly
     *
     * @param match individual match result
     */
    private void matchResultParser(Match match)
    {
        String homeTeamName = match.getHomeTeam();
        String awayTeamName = match.getAwayTeam();
        LeagueTableEntry homeTableUpdate = leagueTable.get(homeTeamName);//variables to collect data changes
        LeagueTableEntry awayTableUpdate = leagueTable.get(awayTeamName);

        if (homeTableUpdate == null) //if home team not found, added for first match
        {
            homeTableUpdate = new LeagueTableEntry(homeTeamName, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        if (awayTableUpdate == null) //if away team not found, added for first match
        {
            awayTableUpdate = new LeagueTableEntry(awayTeamName, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        resultProcessor(match, homeTableUpdate, awayTableUpdate);
        updateTable(match, homeTeamName, awayTeamName, homeTableUpdate, awayTableUpdate);
    }

    /**
     * Processes match results for points table update
     * */
    private void resultProcessor(Match match, LeagueTableEntry homeTableUpdate, LeagueTableEntry awayTableUpdate)
    {
        if (match.getHomeScore() > match.getAwayScore()) //HOME WIN
        {
            homeTableUpdate.setWon(homeTableUpdate.getWon() + 1);
            homeTableUpdate.setPoints(homeTableUpdate.getPoints() + 3);
            awayTableUpdate.setLost(awayTableUpdate.getLost() + 1);
        }
        else if (match.getHomeScore() < match.getAwayScore()) //AWAY WIN
        {
            homeTableUpdate.setLost(homeTableUpdate.getLost() + 1);
            awayTableUpdate.setWon(awayTableUpdate.getWon() + 1);
            awayTableUpdate.setPoints(awayTableUpdate.getPoints() + 3);
        }
        else //DRAW
        {
            homeTableUpdate.setDrawn(homeTableUpdate.getDrawn() + 1);
            homeTableUpdate.setPoints(homeTableUpdate.getPoints() + 1);
            awayTableUpdate.setDrawn(awayTableUpdate.getDrawn() + 1);
            awayTableUpdate.setPoints(awayTableUpdate.getPoints() + 1);
        }
    }

    /**
     * Updates league table with match data inc. goal diff
     * */
    private void updateTable(Match match, String homeTeamName, String awayTeamName, LeagueTableEntry homeTableUpdate, LeagueTableEntry awayTableUpdate)
    {
        //Update games played
        homeTableUpdate.setPlayed(homeTableUpdate.getPlayed() + 1);
        awayTableUpdate.setPlayed(awayTableUpdate.getPlayed() + 1);

        //update goal scored/against tally
        homeTableUpdate.setGoalsFor(homeTableUpdate.getGoalsFor() + match.getHomeScore());
        homeTableUpdate.setGoalsAgainst(homeTableUpdate.getGoalsAgainst() + match.getAwayScore());
        awayTableUpdate.setGoalsAgainst(awayTableUpdate.getGoalsAgainst() + match.getHomeScore());
        awayTableUpdate.setGoalsFor(awayTableUpdate.getGoalsFor() + match.getAwayScore());

        //Goal diff calculator
        homeTableUpdate.setGoalDifference(homeTableUpdate.getGoalDifference() + match.getHomeScore() - match.getAwayScore());
        awayTableUpdate.setGoalDifference(awayTableUpdate.getGoalDifference() + match.getAwayScore() - match.getHomeScore());

        //update league table with home and away data.
        leagueTable.put(homeTeamName, homeTableUpdate);
        leagueTable.put(awayTeamName, awayTableUpdate);
    }

    /**
     * Get the ordered list of league table entries for this league table.
     *
     * @return leagueTableList sorted by points, goal difference, goals for and team name in descending order
     */
    public List<LeagueTableEntry> getTableEntries()
    {
        ArrayList<LeagueTableEntry> leagueTableList = new ArrayList<>(leagueTable.values());
        leagueTableList.sort(Comparator.comparing((LeagueTableEntry leagueTableEntry) -> leagueTableEntry.getPoints()).reversed()
                .thenComparing(leagueTableEntry1 -> leagueTableEntry1.getGoalDifference(), Comparator.reverseOrder())
                .thenComparing(leagueTableEntry2 -> leagueTableEntry2.getGoalsFor(), Comparator.reverseOrder())
                .thenComparing(leagueTableEntry3 -> leagueTableEntry3.getTeamName()));

        return leagueTableList;
    }
}