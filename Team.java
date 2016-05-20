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

import java.util.List;

/**
 *
 * @author captainbowtie
 */
public class Team {
    private int teamNumber;
    private String teamName;
    private Boolean round1Plaintiff;
    private Boolean round3Plaintiff;
    private int round1Opponent;
    private int round2Opponent;
    private int round3Opponent;
    private int round4Opponent;
    private int round1Ballot1PD;
    private int round1Ballot2PD;
    private int round2Ballot1PD;
    private int round2Ballot2PD;
    private int round3Ballot1PD;
    private int round3Ballot2PD;
    private int round4Ballot1PD;
    private int round4Ballot2PD;
    private List impermissibleMatches;

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Boolean getRound1Plaintiff() {
        return round1Plaintiff;
    }

    public void setRound1Plaintiff(Boolean round1Plaintiff) {
        this.round1Plaintiff = round1Plaintiff;
    }

    public Boolean getRound3Plaintiff() {
        return round3Plaintiff;
    }

    public void setRound3Plaintiff(Boolean round3Plaintiff) {
        this.round3Plaintiff = round3Plaintiff;
    }

    public int getRound1Opponent() {
        return round1Opponent;
    }

    public void setRound1Opponent(int round1Opponent) {
        this.round1Opponent = round1Opponent;
    }

    public int getRound2Opponent() {
        return round2Opponent;
    }

    public void setRound2Opponent(int round2Opponent) {
        this.round2Opponent = round2Opponent;
    }

    public int getRound3Opponent() {
        return round3Opponent;
    }

    public void setRound3Opponent(int round3Opponent) {
        this.round3Opponent = round3Opponent;
    }

    public int getRound4Opponent() {
        return round4Opponent;
    }

    public void setRound4Opponent(int round4Opponent) {
        this.round4Opponent = round4Opponent;
    }

    public int getRound1Ballot1PD() {
        return round1Ballot1PD;
    }

    public void setRound1Ballot1PD(int round1Ballot1PD) {
        this.round1Ballot1PD = round1Ballot1PD;
    }

    public int getRound1Ballot2PD() {
        return round1Ballot2PD;
    }

    public void setRound1Ballot2PD(int round1Ballot2PD) {
        this.round1Ballot2PD = round1Ballot2PD;
    }

    public int getRound2Ballot1PD() {
        return round2Ballot1PD;
    }

    public void setRound2Ballot1PD(int round2Ballot1PD) {
        this.round2Ballot1PD = round2Ballot1PD;
    }

    public int getRound2Ballot2PD() {
        return round2Ballot2PD;
    }

    public void setRound2Ballot2PD(int round2Ballot2PD) {
        this.round2Ballot2PD = round2Ballot2PD;
    }

    public int getRound3Ballot1PD() {
        return round3Ballot1PD;
    }

    public void setRound3Ballot1PD(int round3Ballot1PD) {
        this.round3Ballot1PD = round3Ballot1PD;
    }

    public int getRound3Ballot2PD() {
        return round3Ballot2PD;
    }

    public void setRound3Ballot2PD(int round3Ballot2PD) {
        this.round3Ballot2PD = round3Ballot2PD;
    }

    public int getRound4Ballot1PD() {
        return round4Ballot1PD;
    }

    public void setRound4Ballot1PD(int round4Ballot1PD) {
        this.round4Ballot1PD = round4Ballot1PD;
    }

    public int getRound4Ballot2PD() {
        return round4Ballot2PD;
    }

    public void setRound4Ballot2PD(int round4Ballot2PD) {
        this.round4Ballot2PD = round4Ballot2PD;
    }

    public List getImpermissibleMatches() {
        return impermissibleMatches;
    }

    public void setImpermissibleMatches(List impermissibleMatches) {
        this.impermissibleMatches = impermissibleMatches;
    }
}
