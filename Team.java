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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author captainbowtie
 */
public class Team {

    private int teamNumber = 0;
    private String teamName = "N/A";
    private boolean round1Plaintiff = true;
    private boolean round3Plaintiff = true;
    private int round1Opponent = 0;
    private int round2Opponent = 0;
    private int round3Opponent = 0;
    private int round4Opponent = 0;
    private int round1Ballot1PD = 141;
    private int round1Ballot2PD = 141;
    private int round2Ballot1PD = 141;
    private int round2Ballot2PD = 141;
    private int round3Ballot1PD = 141;
    private int round3Ballot2PD = 141;
    private int round4Ballot1PD = 141;
    private int round4Ballot2PD = 141;
    private final List<Integer> impermissibleMatches;
    private final List<Member> members;

    /**
     * Creates a new Team object with the specified teamNumber, teamName, and
     * list of impermissible matches
     *
     * @param teamNumber the team's number for the tournament
     * @param teamName the team's name
     * @param impermissibleMatches List of all team numbers the Team cannot face
     */
    public Team(int teamNumber, String teamName, List impermissibleMatches) {
        this.teamNumber = teamNumber;
        this.teamName = teamName;
        this.impermissibleMatches = impermissibleMatches;
        members = new ArrayList<>();
    }

    /**
     * Returns the int team number value of the invoking Team
     *
     * @return the team number of the Team object on which the method is called
     */
    public int getTeamNumber() {
        return teamNumber;
    }

    /**
     * Sets the team number of the calling object to the specified int value
     *
     * @param teamNumber the team number to set the team to
     */
    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    /**
     * Returns the team name of the invoking Team in String form
     *
     * @return the name of the Team that invokes the method
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Sets the team name to specified String
     *
     * @param teamName the team name to which the Team will be set
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Returns true if the Team was plaintiff round 1; false if the Team was
     * defendant round 1
     *
     * @return whether during round 1 the invoking Team was plaintiff
     */
    public boolean getRound1Plaintiff() {
        return round1Plaintiff;
    }

    /**
     * Sets the side the invoking team competed on during round 1. Should be
     * true if they were plaintiff and false if they were defendant
     *
     * @param round1Plaintiff set true if plaintiff; false if defendant
     */
    public void setRound1Plaintiff(boolean round1Plaintiff) {
        this.round1Plaintiff = round1Plaintiff;
    }

    /**
     * Returns true if the Team was plaintiff round 3; false if the Team was
     * defendant round 3
     *
     * @return whether during round 3 the invoking Team was plaintiff
     */
    public boolean getRound3Plaintiff() {
        return round3Plaintiff;
    }

    /**
     * Sets the side the invoking team competed on during round 3. Should be
     * true if they were plaintiff and false if they were defendant
     *
     * @param round3Plaintiff set true if plaintiff; false if defendant
     */
    public void setRound3Plaintiff(boolean round3Plaintiff) {
        this.round3Plaintiff = round3Plaintiff;
    }

    /**
     * Returns the team number of the team the invoking Team faced during round
     * 1. Note that this does not return the Team object of the opposing team.
     * Rather it returns the int team number, which may then be used to look up
     * the opposing Team object, if needed
     *
     * @return int team number of the team faced round 1
     */
    public int getRound1Opponent() {
        return round1Opponent;
    }

    /**
     * Sets the team number of the round 1 opponent of the invoking Team to the
     * specified int
     *
     * @param round1Opponent team number of round 1 opponent
     */
    public void setRound1Opponent(int round1Opponent) {
        this.round1Opponent = round1Opponent;
    }

    /**
     * Returns the team number of the team the invoking Team faced during round
     * 2. Note that this does not return the Team object of the opposing team.
     * Rather it returns the int team number, which may then be used to look up
     * the opposing Team object, if needed
     *
     * @return int team number of the team faced round 2
     */
    public int getRound2Opponent() {
        return round2Opponent;
    }

    /**
     * Sets the team number of the round 2 opponent of the invoking Team to the
     * specified int
     *
     * @param round2Opponent team number of round 2 opponent
     */
    public void setRound2Opponent(int round2Opponent) {
        this.round2Opponent = round2Opponent;
    }

    /**
     * Returns the team number of the team the invoking Team faced during round
     * 3. Note that this does not return the Team object of the opposing team.
     * Rather it returns the int team number, which may then be used to look up
     * the opposing Team object, if needed
     *
     * @return int team number of the team faced round 3
     */
    public int getRound3Opponent() {
        return round3Opponent;
    }

    /**
     * Sets the team number of the round 3 opponent of the invoking Team to the
     * specified int
     *
     * @param round3Opponent team number of round 3 opponent
     */
    public void setRound3Opponent(int round3Opponent) {
        this.round3Opponent = round3Opponent;
    }

    /**
     * Returns the team number of the team the invoking Team faced during round
     * 4. Note that this does not return the Team object of the opposing team.
     * Rather it returns the int team number, which may then be used to look up
     * the opposing Team object, if needed
     *
     * @return int team number of the team faced round 4
     */
    public int getRound4Opponent() {
        return round4Opponent;
    }

    /**
     * Sets the team number of the round 4 opponent of the invoking Team to the
     * specified int
     *
     * @param round4Opponent team number of round 4 opponent
     */
    public void setRound4Opponent(int round4Opponent) {
        this.round4Opponent = round4Opponent;
    }

    /**
     * Returns the invoking Team's point differential on one of the round 1
     * ballots. Invoking team means that if the team won, the returned value
     * will be positive, and if the team lost, the value will be negative. Ties
     * are zeros.
     *
     * @return point differential on the first of the two round 1 ballots
     */
    public int getRound1Ballot1PD() {
        if(round1Ballot1PD<141){
            return round1Ballot1PD;
        }else{
            return 0;
        }
    }

    /**
     * Sets the invoking Team's point differential for one of the round 1
     * ballots. Invoking team means that if the team won, the value should be
     * positive, and if the team lost, the value should be negative. Ties are
     * zeros.
     *
     * @param round1Ballot1PD point differential on the first of the two round 1
     * ballots
     */
    public void setRound1Ballot1PD(int round1Ballot1PD) {
        this.round1Ballot1PD = round1Ballot1PD;
    }

    /**
     * Returns the invoking Team's point differential on one of the round 1
     * ballots. Invoking team means that if the team won, the returned value
     * will be positive, and if the team lost, the value will be negative. Ties
     * are zeros.
     *
     * @return point differential on the second of the two round 1 ballots
     */
    public int getRound1Ballot2PD() {
        if(round1Ballot2PD<141){
            return round1Ballot2PD;
        }else{
            return 0;
        }
    }

    /**
     * Sets the invoking Team's point differential for one of the round 1
     * ballots. Invoking team means that if the team won, the value should be
     * positive, and if the team lost, the value should be negative. Ties are
     * zeros.
     *
     * @param round1Ballot2PD point differential on the second of the two round
     * 1 ballots
     */
    public void setRound1Ballot2PD(int round1Ballot2PD) {
        this.round1Ballot2PD = round1Ballot2PD;
    }

    /**
     * Returns the invoking Team's point differential on one of the round 2
     * ballots. Invoking team means that if the team won, the returned value
     * will be positive, and if the team lost, the value will be negative. Ties
     * are zeros.
     *
     * @return point differential on the first of the two round 2 ballots
     */
    public int getRound2Ballot1PD() {
        if(round2Ballot1PD<141){
            return round2Ballot1PD;
        }else{
            return 0;
        }
    }

    /**
     * Sets the invoking Team's point differential for one of the round 2
     * ballots. Invoking team means that if the team won, the value should be
     * positive, and if the team lost, the value should be negative. Ties are
     * zeros.
     *
     * @param round2Ballot1PD point differential on the first of the two round 2
     * ballots
     */
    public void setRound2Ballot1PD(int round2Ballot1PD) {
        this.round2Ballot1PD = round2Ballot1PD;
    }

    /**
     * Returns the invoking Team's point differential on one of the round 2
     * ballots. Invoking team means that if the team won, the returned value
     * will be positive, and if the team lost, the value will be negative. Ties
     * are zeros.
     *
     * @return point differential on the second of the two round 2 ballots
     */
    public int getRound2Ballot2PD() {
        if(round2Ballot2PD<141){
            return round2Ballot2PD;
        }else{
            return 0;
        }
    }

    /**
     * Sets the invoking Team's point differential for one of the round 2
     * ballots. Invoking team means that if the team won, the value should be
     * positive, and if the team lost, the value should be negative. Ties are
     * zeros.
     *
     * @param round2Ballot2PD point differential on the second of the two round
     * 2 ballots
     */
    public void setRound2Ballot2PD(int round2Ballot2PD) {
        this.round2Ballot2PD = round2Ballot2PD;
    }

    /**
     * Returns the invoking Team's point differential on one of the round 3
     * ballots. Invoking team means that if the team won, the returned value
     * will be positive, and if the team lost, the value will be negative. Ties
     * are zeros.
     *
     * @return point differential on the first of the two round 3 ballots
     */
    public int getRound3Ballot1PD() {
        if(round3Ballot1PD<141){
            return round3Ballot1PD;
        }else{
            return 0;
        }
    }

    /**
     * Sets the invoking Team's point differential for one of the round 3
     * ballots. Invoking team means that if the team won, the value should be
     * positive, and if the team lost, the value should be negative. Ties are
     * zeros.
     *
     * @param round3Ballot1PD point differential on the first of the two round 3
     * ballots
     */
    public void setRound3Ballot1PD(int round3Ballot1PD) {
        this.round3Ballot1PD = round3Ballot1PD;
    }

    /**
     * Returns the invoking Team's point differential on one of the round 3
     * ballots. Invoking team means that if the team won, the returned value
     * will be positive, and if the team lost, the value will be negative. Ties
     * are zeros.
     *
     * @return point differential on the second of the two round 3 ballots
     */
    public int getRound3Ballot2PD() {
        if(round3Ballot2PD<141){
            return round3Ballot2PD;
        }else{
            return 0;
        }
    }

    /**
     * Sets the invoking Team's point differential for one of the round 3
     * ballots. Invoking team means that if the team won, the value should be
     * positive, and if the team lost, the value should be negative. Ties are
     * zeros.
     *
     * @param round3Ballot2PD point differential on the second of the two round
     * 3 ballots
     */
    public void setRound3Ballot2PD(int round3Ballot2PD) {
        this.round3Ballot2PD = round3Ballot2PD;
    }

    /**
     * Returns the invoking Team's point differential on one of the round 4
     * ballots. Invoking team means that if the team won, the returned value
     * will be positive, and if the team lost, the value will be negative. Ties
     * are zeros.
     *
     * @return point differential on the first of the two round 4 ballots
     */
    public int getRound4Ballot1PD() {
        if(round4Ballot1PD<141){
            return round4Ballot1PD;
        }else{
            return 0;
        }
    }

    /**
     * Sets the invoking Team's point differential for one of the round 4
     * ballots. Invoking team means that if the team won, the value should be
     * positive, and if the team lost, the value should be negative. Ties are
     * zeros.
     *
     * @param round4Ballot1PD point differential on the first of the two round 4
     * ballots
     */
    public void setRound4Ballot1PD(int round4Ballot1PD) {
        this.round4Ballot1PD = round4Ballot1PD;
    }

    /**
     * Returns the invoking Team's point differential on one of the round 4
     * ballots. Invoking team means that if the team won, the returned value
     * will be positive, and if the team lost, the value will be negative. Ties
     * are zeros.
     *
     * @return point differential on the second of the two round 4 ballots
     */
    public int getRound4Ballot2PD() {
        if(round4Ballot2PD<141){
            return round4Ballot2PD;
        }else{
            return 0;
        }
    }

    /**
     * Sets the invoking Team's point differential for one of the round 4
     * ballots. Invoking team means that if the team won, the value should be
     * positive, and if the team lost, the value should be negative. Ties are
     * zeros.
     *
     * @param round4Ballot2PD point differential on the second of the two round
     * 4 ballots
     */
    public void setRound4Ballot2PD(int round4Ballot2PD) {
        this.round4Ballot2PD = round4Ballot2PD;
    }

    /**
     * Returns a List of the teams the invoking Team cannot be paired against.
     *
     * @return a List of all teams that cannot be faced
     */
    public List getImpermissibleMatches() {
        return impermissibleMatches;
    }

    /**
     * Adds a team number to the list of teams the invoking Team cannot face
     *
     * @param impermissibleMatches Team number to add to list of impermissible
     * matches
     */
    public void addImpermissibleMatch(Integer impermissibleMatch) {
        this.impermissibleMatches.add(impermissibleMatch);
    }

    /**
     * Returns the List of Members, in Member object form, that are on the team
     *
     * @return List of Member objects within the team
     */
    public List getMembers() {
        return members;
    }

    /**
     * Adds a new member to the team member list with the specified name
     *
     * @param name Name of the team member to be added to the team member list
     */
    public void addMember(String name) {
        members.add(new Member(name));
    }

    /**
     * Returns the Member object from the team Member ArrayList at the specified
     * index
     *
     * @param index Index number of the member to be returned
     * @return The member object at the specified index
     */
    public Member getMember(int index) {
        return members.get(index);
    }

    /**
     * Returns the number of ballots the team has won
     *
     * @return number of ballots won
     */
    public int getWins() {
        int wins = 0;
        if (round1Ballot1PD > 0 && round1Ballot1PD < 141) {
            wins++;
        }
        if (round1Ballot2PD > 0 && round1Ballot2PD < 141) {
            wins++;
        }
        if (round2Ballot1PD > 0 && round2Ballot1PD < 141) {
            wins++;
        }
        if (round2Ballot2PD > 0 && round2Ballot2PD < 141) {
            wins++;
        }
        if (round3Ballot1PD > 0 && round3Ballot1PD < 141) {
            wins++;
        }
        if (round3Ballot2PD > 0 && round3Ballot2PD < 141) {
            wins++;
        }
        if (round4Ballot1PD > 0 && round4Ballot1PD < 141) {
            wins++;
        }
        if (round4Ballot2PD > 0 && round4Ballot2PD < 141) {
            wins++;
        }
        return wins;
    }

    /**
     * Returns the number of ballots the team has lost
     *
     * @return number of ballots lost
     */
    public int getLoses() {
        int loses = 0;
        if (round1Ballot1PD < 0) {
            loses++;
        }
        if (round1Ballot2PD < 0) {
            loses++;
        }
        if (round2Ballot1PD < 0) {
            loses++;
        }
        if (round2Ballot2PD < 0) {
            loses++;
        }
        if (round3Ballot1PD < 0) {
            loses++;
        }
        if (round3Ballot2PD < 0) {
            loses++;
        }
        if (round4Ballot1PD < 0) {
            loses++;
        }
        if (round4Ballot2PD < 0) {
            loses++;
        }
        return loses;
    }

    /**
     * Returns the number of ballots the team has tied
     * @return number of ballots tied
     */
    public int getTies() {
        int ties = 0;
        if (round1Ballot1PD == 0) {
            ties++;
        }
        if (round1Ballot2PD == 0) {
            ties++;
        }
        if (round2Ballot1PD == 0) {
            ties++;
        }
        if (round2Ballot2PD == 0) {
            ties++;
        }
        if (round3Ballot1PD == 0) {
            ties++;
        }
        if (round3Ballot2PD == 0) {
            ties++;
        }
        if (round4Ballot1PD == 0) {
            ties++;
        }
        if (round4Ballot2PD == 0) {
            ties++;
        }
        return ties;
    }
    
    public int getPD(){
        int pd = 0;
        if (round1Ballot1PD < 141) {
            pd = pd + round1Ballot1PD;
        }
        if (round1Ballot2PD < 141) {
            pd = pd + round1Ballot2PD;
        }
        if (round2Ballot1PD < 141) {
            pd = pd + round2Ballot1PD;
        }
        if (round2Ballot2PD < 141) {
            pd = pd + round2Ballot2PD;
        }
        if (round3Ballot1PD < 141) {
            pd = pd + round3Ballot1PD;
        }
        if (round3Ballot2PD < 141) {
            pd = pd + round3Ballot2PD;
        }
        if (round4Ballot1PD < 141) {
            pd = pd + round4Ballot1PD;
        }
        if (round4Ballot2PD < 141) {
            pd = pd + round4Ballot2PD;
        }
        return pd;
    }
}
