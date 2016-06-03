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

/**
 *
 * @author captainbowtie
 */
public class Member {

    private String name = "N/A";
    private int[] plaintiffAttorneyRanks = {0, 0, 0, 0};
    private int[] defenseAttorneyRanks = {0, 0, 0, 0};
    private int[] plaintiffWitnessRanks = {0, 0, 0, 0};
    private int[] defenseWitnessRanks = {0, 0, 0, 0};

    /**
     * Creates a new member with the specified name
     *
     * @param name name for the new team member
     */
    public Member(String name) {
        this.name = name;
    }

    /**
     * Creates a Member with specified name and ranks. Intended for use when Member
     * data is being restored from a file. Ranks should be in a 16 int array, listed in
     * plaintiffAttorneyRanks, defenseAttorneyRanks, plaintiffWitnessRanks, defenseWitnessRanks order
     * @param name Name of the team member
     * @param ranks int[16] array of ranks in π Attorney, ∆ Attorney, π Witness, ∆ Witness order
     */
    public Member(String name, int[] ranks) {
        this.name = name;
        for (int a = 0; a < 4; a++) {
            plaintiffAttorneyRanks[a] = ranks[a];
            defenseAttorneyRanks[a] = ranks[a + 4];
            plaintiffWitnessRanks[a] = ranks[a + 8];
            defenseWitnessRanks[a] = ranks[a + 12];
        }
    }

    /**
     * Returns a four int array of the amount of points the Member received as a
     * plaintiff attorney from each ballot
     *
     * @return a four int array, with the ranks from each ballot where the
     * Member was a plaintiff attorney
     */
    public int[] getPlaintiffAttorneyRanks() {
        return plaintiffAttorneyRanks;
    }

    /**
     * Sets the ranks of the invoking Member for plaintiff attorney ranks. The
     * passed array should be four ints long.
     *
     * @param plaintiffAttorneyRanks four int array with ranks from each ballot
     */
    public void setPlaintiffAttorneyRanks(int[] plaintiffAttorneyRanks) {
        this.plaintiffAttorneyRanks = plaintiffAttorneyRanks;
    }

    /**
     * Returns a for int array of the amount of points the Member received as a
     * plaintiff witness from each ballot
     *
     * @return a four int array, with the ranks from each ballot where the
     * Member was a plaintiff witness
     */
    public int[] getPlaintiffWitnessRanks() {
        return plaintiffWitnessRanks;
    }

    /**
     * Sets the ranks of the invoking Member for plaintiff witness ranks. The
     * passed array should be four ints long.
     *
     * @param plaintiffWitnessRanks four int array with ranks from each ballot
     */
    public void setPlaintiffWitnessRanks(int[] plaintiffWitnessRanks) {
        this.plaintiffWitnessRanks = plaintiffWitnessRanks;
    }

    /**
     * Returns a for int array of the amount of points the Member received as a
     * defense attorney from each ballot
     *
     * @return a four int array, with the ranks from each ballot where the
     * Member was a defense attorney
     */
    public int[] getDefenseAttorneyRanks() {
        return defenseAttorneyRanks;
    }

    /**
     * Sets the ranks of the invoking Member for defense attorney ranks. The
     * passed array should be four ints long.
     *
     * @param defenseAttorneyRanks four int array with ranks from each ballot
     */
    public void setDefenseAttorneyRanks(int[] defenseAttorneyRanks) {
        this.defenseAttorneyRanks = defenseAttorneyRanks;
    }

    /**
     * Returns a for int array of the amount of points the Member received as a
     * defense witness from each ballot
     *
     * @return a four int array, with the ranks from each ballot where the
     * Member was a defense witness
     */
    public int[] getDefenseWitnessRanks() {
        return defenseWitnessRanks;
    }

    /**
     * Sets the ranks of the invoking Member for defense witness ranks. The
     * passed array should be four ints long.
     *
     * @param defenseWitnessRanks four int array with ranks from each ballot
     */
    public void setDefenseWitnessRanks(int[] defenseWitnessRanks) {
        this.defenseWitnessRanks = defenseWitnessRanks;
    }

    /**
     * Returns the name of the invoking member
     *
     * @return member name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of invoking member to the specified string
     *
     * @param name String to set the member's name to
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return (name + "," + plaintiffAttorneyRanks[0] + "," + plaintiffAttorneyRanks[1]
                + "," + plaintiffAttorneyRanks[2] + "," + plaintiffAttorneyRanks[3]
                + "," + plaintiffWitnessRanks[0] + "," + plaintiffWitnessRanks[1]
                + "," + plaintiffWitnessRanks[2] + "," + plaintiffWitnessRanks[3]
                + "," + defenseAttorneyRanks[0] + "," + defenseAttorneyRanks[1]
                + "," + defenseAttorneyRanks[2] + "," + defenseAttorneyRanks[3]
                + "," + defenseWitnessRanks[0] + "," + defenseWitnessRanks[1]
                + "," + defenseWitnessRanks[2] + "," + defenseWitnessRanks[3]);

    }

}
