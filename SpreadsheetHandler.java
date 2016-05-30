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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author captainbowtie
 */
public abstract class SpreadsheetHandler {

    public static void saveToSpreadsheet(Tournament tournament, File outputFile) {
        //TODO: write real error handling
        try (PrintWriter out = new PrintWriter(outputFile)) {
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
