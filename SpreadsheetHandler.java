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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author captainbowtie
 */
public abstract class SpreadsheetHandler {

    /**
     * Writes tournament data to a CSV file, first listing whether lower team number
     * is higher rank and whether rank 1 or rank 3 is plaintiff in round three, followed by
     * all the teams with their fields listed in the following order:
     * teamNumber,teamName,round1Plaintiff,round3Plaintiff,round1Opponent,round2Opponent,
     * round3Opponent,round4Opponent,round1Ballot1PD,round1Ballot2PD,round2Ballot1PD,
     * round2Ballot2PD,round3Ballot1PD,round3Ballot2PD,round4Ballot1PD,round4Ballot2PD,
     * a list of all impermissible matches, a list of team members in the form
     * [memberName,plaintiffAttorneyRanks,defenseAttorneyRanks,plaintiffWitnessRanks,defenseWitnessRanks
     *
     * @param tournament tournament object to be written to a CSV
     * @param outputFile file location to which the file should be written
     */
    public static void saveToSpreadsheet(Tournament tournament, File outputFile) {
        //TODO: write real error handling
        try (PrintWriter out = new PrintWriter(outputFile)) {
            out.println(tournament.isLowerTeamNumberIsHigherRank()+","+tournament.isRound3Rank1IsPlaintiff());
            int[] maxes = getTeamMaxes(tournament);
            for (int a = 0; a < tournament.getTeams().size(); a++) {
                final Team team = tournament.getTeam(a);
                out.print(team.getTeamNumber() + "," + team.getTeamName() + ","
                        + team.getRound1Plaintiff() + "," + team.getRound3Plaintiff() + ","
                        + team.getRound1Opponent() + "," + team.getRound2Opponent() + ","
                        + team.getRound3Opponent() + "," + team.getRound4Opponent() + ","
                        + team.getRound1Ballot1PD() + "," + team.getRound1Ballot2PD() + ","
                        + team.getRound2Ballot1PD() + "," + team.getRound2Ballot2PD() + ","
                        + team.getRound3Ballot1PD() + "," + team.getRound3Ballot2PD() + ","
                        + team.getRound4Ballot1PD() + "," + team.getRound4Ballot2PD()
                );
                for (int b = 0; b < maxes[0]; b++) {
                    out.print(",");
                    for (int c = b; c < team.getImpermissibleMatches().size(); c++) {
                        out.print(team.getImpermissibleMatches().get(c) + ",");
                        b++;
                    }
                }
                for (int b = 0; b < maxes[1]; b++) {
                    for (int c = b; c < team.getMembers().size(); c++) {
                        Member member = team.getMember(c);
                        out.print(member.getName() + ","
                                + member.getPlaintiffAttorneyRanks()[0] + ","
                                + member.getPlaintiffAttorneyRanks()[1] + ","
                                + member.getPlaintiffAttorneyRanks()[2] + ","
                                + member.getPlaintiffAttorneyRanks()[3] + ","
                                + member.getDefenseAttorneyRanks()[0] + ","
                                + member.getDefenseAttorneyRanks()[1] + ","
                                + member.getDefenseAttorneyRanks()[2] + ","
                                + member.getDefenseAttorneyRanks()[3] + ","
                                + member.getPlaintiffWitnessRanks()[0] + ","
                                + member.getPlaintiffWitnessRanks()[1] + ","
                                + member.getPlaintiffWitnessRanks()[2] + ","
                                + member.getPlaintiffWitnessRanks()[3] + ","
                                + member.getDefenseWitnessRanks()[0] + ","
                                + member.getDefenseWitnessRanks()[1] + ","
                                + member.getDefenseWitnessRanks()[2] + ","
                                + member.getDefenseWitnessRanks()[3] + ",");
                    }
                    out.print(",,,,,,,,,,,,,,,,,");
                }
                out.println();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpreadsheetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Tournament loadFromSpreadsheet(File inputFile) {
        Tournament tournament = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader(inputFile));
            String line = in.readLine();
            final Pattern teamNumberREGEX = Pattern.compile("\\d{4}");
            final Pattern memberREGEX = Pattern.compile("[A-z\\d ]*,\\d,\\d,\\d,\\d,\\d,\\d,\\d,\\d,\\d,\\d,\\d,\\d,\\d,\\d,\\d,\\d,");
            final boolean lowerTeamNumberIsHigherRank = Boolean.parseBoolean(line.substring(0,line.indexOf(',')));
            line = line.substring(5);
            final boolean round3Rank1IsPlaintiff = Boolean.parseBoolean(line);
            line = in.readLine();
            tournament = new Tournament(lowerTeamNumberIsHigherRank,round3Rank1IsPlaintiff);
            while (line != null) {
                final int teamNumber = Integer.parseInt(line.substring(0, 4));
                line = line.substring(5);
                final String teamName = line.substring(0, line.indexOf(','));
                line = line.substring(teamName.length() + 1);
                final boolean round1Plaintiff = Boolean.parseBoolean(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                boolean round3Plaintiff = Boolean.parseBoolean(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round1Opponent = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round2Opponent = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round3Opponent = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round4Opponent = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round1Ballot1PD = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round1Ballot2PD = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round2Ballot1PD = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round2Ballot2PD = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round3Ballot1PD = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round3Ballot2PD = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round4Ballot1PD = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                final int round4Ballot2PD;
                if(line.contains(",")){
                    round4Ballot2PD = Integer.parseInt(line.substring(0, line.indexOf(',')));
                }else{
                    round4Ballot2PD = Integer.parseInt(line);
                }
                line = line.substring(line.indexOf(',') + 1);
                final Matcher teamNumberMatcher = teamNumberREGEX.matcher(line);
                ArrayList<Integer> impermissibleMatches = new ArrayList<>();
                while (teamNumberMatcher.find()) {
                    impermissibleMatches.add(Integer.parseInt(line.substring(teamNumberMatcher.start(), teamNumberMatcher.end() - 1)));
                }
                line = line.substring(impermissibleMatches.size() * 5);
                final Matcher memberMatcher = memberREGEX.matcher(line);
                ArrayList<Member> members = new ArrayList<>();
                while (memberMatcher.find()) {
                    String memberString = line.substring(memberMatcher.start(), memberMatcher.end());
                    String memberName = memberString.substring(0, memberString.indexOf(','));
                    memberString = memberString.substring(memberString.indexOf(',') + 1);
                    int[] ranks = new int[16];
                    for (int a = 0; a < 16; a++) {
                        ranks[a] = Integer.parseInt(memberString.substring(a * 2, a * 2));
                    }
                    members.add(new Member(memberName, ranks));
                }
                tournament.addTeam(new Team(teamNumber, teamName, round1Plaintiff, round3Plaintiff,
                        round1Opponent, round2Opponent, round3Opponent, round4Opponent,
                        round1Ballot1PD, round1Ballot2PD, round2Ballot1PD, round2Ballot2PD,
                        round3Ballot1PD, round3Ballot2PD, round4Ballot1PD, round4Ballot2PD,
                        impermissibleMatches, members));
            line=in.readLine();
            }

        } //TODO: real error handling
        catch (FileNotFoundException ex) {
            Logger.getLogger(SpreadsheetHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SpreadsheetHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return tournament;
    }

    /**
     * Returns the maximum number of impermissible matches and team members from
     * all teams in a given tournament
     *
     * @param tournament the tournament whose max. impermissible matches is
     * sought
     * @return array containing max impermissible matches in [0] and max team
     * members in [1]
     */
    private static int[] getTeamMaxes(Tournament tournament) {
        int[] maxes = {0, 0};
        for (int a = 0; a < tournament.getTeams().size(); a++) {
            if (tournament.getTeam(a).getImpermissibleMatches().size() > maxes[0]) {
                maxes[0] = tournament.getTeam(a).getImpermissibleMatches().size();
            }
            if (tournament.getTeam(a).getMembers().size() > maxes[1]) {
                maxes[1] = tournament.getTeam(a).getMembers().size();
            }
        }
        return maxes;
    }
}
