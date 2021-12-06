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
     * Instantiates a new league table with List of matches
     *
     * @param matches list of matches
     */
    public LeagueTable(final List<Match> matches)
    {
        leagueTable = new HashMap<>();
        matches.forEach(this::addMatchToLeagueTable);
    }
    
    /**
     * Process individual match record and update the table accordingly
     *
     * @param match single match result
     */
    private void addMatchToLeagueTable(Match match)
    {
        //Placeholders for keys
        String homeTeamName = match.getHomeTeam();
        String awayTeamName = match.getAwayTeam();

        //variables to collect data changes
        LeagueTableEntry homeTableUpdate = leagueTable.get(homeTeamName);
        LeagueTableEntry awayTableUpdate = leagueTable.get(awayTeamName);

        if (homeTableUpdate == null) //entry not found, initialised for first match
        {
            homeTableUpdate = new LeagueTableEntry(homeTeamName, 0, 0, 0, 0, 0, 0, 0, 0);
        }
        if (awayTableUpdate == null) //entry not found, initialised for first match
        {
            awayTableUpdate = new LeagueTableEntry(awayTeamName, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        matchResultProcessor(match, homeTableUpdate, awayTableUpdate);

        updateTable(match, homeTeamName, awayTeamName, homeTableUpdate, awayTableUpdate);
    }

    private void matchResultProcessor(Match match, LeagueTableEntry homeTableUpdate, LeagueTableEntry awayTableUpdate)
    {
        if (match.getHomeScore() > match.getAwayScore()) //HOME WIN
        {
            homeTableUpdate.setWon(homeTableUpdate.getWon() + 1);
            awayTableUpdate.setLost(awayTableUpdate.getLost() + 1);
            homeTableUpdate.setPoints(homeTableUpdate.getPoints() + 3);
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
            awayTableUpdate.setDrawn(awayTableUpdate.getDrawn() + 1);
            homeTableUpdate.setPoints(homeTableUpdate.getPoints() + 1);
            awayTableUpdate.setPoints(awayTableUpdate.getPoints() + 1);
        }
    }

    private void updateTable(Match match, String homeTeamName, String awayTeamName, LeagueTableEntry homeTableUpdate, LeagueTableEntry awayTableUpdate)
    {
        //Update games played
        homeTableUpdate.setPlayed(homeTableUpdate.getPlayed() + 1);
        awayTableUpdate.setPlayed(awayTableUpdate.getPlayed() + 1);

        //update goal tally
        homeTableUpdate.setGoalsFor(homeTableUpdate.getGoalsFor() + match.getHomeScore());
        awayTableUpdate.setGoalsFor(awayTableUpdate.getGoalsFor() + match.getAwayScore());

        homeTableUpdate.setGoalsAgainst(homeTableUpdate.getGoalsAgainst() + match.getAwayScore());
        awayTableUpdate.setGoalsAgainst(awayTableUpdate.getGoalsAgainst() + match.getHomeScore());

        //Goal diff calculator and table update
        homeTableUpdate.setGoalDifference(homeTableUpdate.getGoalDifference() + match.getHomeScore() - match.getAwayScore());
        awayTableUpdate.setGoalDifference(awayTableUpdate.getGoalDifference() + match.getAwayScore() - match.getHomeScore());

        //update league table with home and away data. Overwrites previous entries
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