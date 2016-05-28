/*
 * Copyright (C) 2016 captainbowtie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.allenbarr.MockTrialTabulation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author captainbowtie
 */
public class Tournament {
    private final List<Team> teams;
    /** 
     * Default constructor. Creates a Tournament and initializes the team list
     * 
     */
    public Tournament(){
        teams = new ArrayList<>();
    }
    /**
     * Constructor for when a tournament is being restored from a save state.
     * The save state should be either one generated by this class or one following
     * the format of this class, otherwise the restore will not succeed
     * @param csvFile file object where the save state is stored.
     */
    public Tournament(File csvFile){
        teams = new ArrayList<>();
        // @TODO write file importer
    }
    /**
     * Adds a new team with the specified number, name, and impermissible matches to that list
     * @param teamNumber team number of the team to be added
     * @param teamName team name of the team to be added
     * @param impermissibleMatches list of teams (by team number) the team to be added cannot face
     */
    public void addTeam(int teamNumber, String teamName, List impermissibleMatches){
        teams.add(new Team(teamNumber, teamName, impermissibleMatches));
    }
    /**
     * Writes the tournament, in CSV format, to the specified file location
     * @param saveLocation the location in the file system to save the tournament to
     */
    public void writeToCSV(File saveLocation){
        // @TODO determine CSV format and then write an exporter
    }
    /**
     * Returns the List of teams competing at the tournament
     * @return teams competing at the tournament in List form
     */
    public List<Team> getTeams() {
        return teams;
    }
    /**
     * Returns the Team at the specified index from the List of teams
     * @param index
     * @return Team at the specified index from the Team List
     */
    public Team getTeam(int index){
        return teams.get(index);
    }
    
}
