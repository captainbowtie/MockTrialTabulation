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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author captainbowtie
 */
public class TeamDialog extends Stage {

    private final TextField[] plaintiffAttorneyNames = new TextField[6];
    private final TextField[] plaintiffWitnessNames = new TextField[6];
    private final TextField[] defenseAttorneyNames = new TextField[6];
    private final TextField[] defenseWitnessNames = new TextField[6];
    private final TextField[][] plaintiffAttorneyRanks = new TextField[6][4];
    private final TextField[][] plaintiffWitnessRanks = new TextField[6][4];
    private final TextField[][] defenseAttorneyRanks = new TextField[6][4];
    private final TextField[][] defenseWitnessRanks = new TextField[6][4];
    private boolean saveChanges = false;

    public TeamDialog(Team team) {
        this.setTitle(team.getTeamNumber() + " " + team.getTeamName());
        this.initModality(Modality.APPLICATION_MODAL);
        final GridPane megaGrid = new GridPane();
        final GridPane plaintiffAttorneyGrid = new GridPane();
        final GridPane plaintiffWitnessGrid = new GridPane();
        final GridPane defenseAttorneyGrid = new GridPane();
        final GridPane defenseWitnessGrid = new GridPane();
        //Initialize fields to default values (blank & zero)
        for (int a = 0; a < 6; a++) {
            plaintiffAttorneyNames[a] = new TextField("");
            plaintiffAttorneyNames[a].setPrefColumnCount(16);
            plaintiffWitnessNames[a] = new TextField("");
            plaintiffWitnessNames[a].setPrefColumnCount(16);
            defenseAttorneyNames[a] = new TextField("");
            defenseAttorneyNames[a].setPrefColumnCount(16);
            defenseWitnessNames[a] = new TextField("");
            defenseWitnessNames[a].setPrefColumnCount(16);
            for (int b = 0; b < 4; b++) {
                plaintiffAttorneyRanks[a][b] = new TextField("0");
                plaintiffAttorneyRanks[a][b].setPrefColumnCount(1);
                plaintiffWitnessRanks[a][b] = new TextField("0");
                plaintiffWitnessRanks[a][b].setPrefColumnCount(1);
                defenseAttorneyRanks[a][b] = new TextField("0");
                defenseAttorneyRanks[a][b].setPrefColumnCount(1);
                defenseWitnessRanks[a][b] = new TextField("0");
                defenseWitnessRanks[a][b].setPrefColumnCount(1);
            }
        }
        //Fill in fields with data from team
        for (int a = 0; a < team.getMembers().size(); a++) {
            //Plaintiff attorney ranks
            if (hasRanks(team.getMember(a).getPlaintiffAttorneyRanks())) {
                for (int b = 0; b < 6; b++) {
                    if (plaintiffAttorneyNames[b].getText().equals("")) {
                        plaintiffAttorneyNames[b].setText(team.getMember(a).getName());
                        for (int c = 0; c < plaintiffAttorneyRanks[b].length; c++) {
                            plaintiffAttorneyRanks[b][c].setText(Integer.toString(team.getMember(a).getPlaintiffAttorneyRanks()[c]));
                        }
                        b = 6;
                    }
                }
            }
            //Plaintiff witness ranks
            if (hasRanks(team.getMember(a).getPlaintiffWitnessRanks())) {
                for (int b = 0; b < 6; b++) {
                    if (plaintiffWitnessNames[b].getText().equals("")) {
                        plaintiffWitnessNames[b].setText(team.getMember(a).getName());
                        for (int c = 0; c < plaintiffWitnessRanks[b].length; c++) {
                            plaintiffWitnessRanks[b][c].setText(Integer.toString(team.getMember(a).getPlaintiffWitnessRanks()[c]));
                        }
                        b = 6;
                    }
                }
            }
            //Defense attorney ranks
            if (hasRanks(team.getMember(a).getDefenseAttorneyRanks())) {
                for (int b = 0; b < 6; b++) {
                    if (defenseAttorneyNames[b].getText().equals("")) {
                        defenseAttorneyNames[b].setText(team.getMember(a).getName());
                        for (int c = 0; c < defenseAttorneyRanks[b].length; c++) {
                            defenseAttorneyRanks[b][c].setText(Integer.toString(team.getMember(a).getDefenseAttorneyRanks()[c]));
                        }
                        b = 6;
                    }
                }
            }
            //Defense witness ranks
            if (hasRanks(team.getMember(a).getDefenseWitnessRanks())) {
                for (int b = 0; b < 6; b++) {
                    if (defenseWitnessNames[b].getText().equals("")) {
                        defenseWitnessNames[b].setText(team.getMember(a).getName());
                        for (int c = 0; c < defenseWitnessRanks[b].length; c++) {
                            defenseWitnessRanks[b][c].setText(Integer.toString(team.getMember(a).getDefenseWitnessRanks()[c]));
                        }
                        b = 6;
                    }
                }
            }
        }
        //Add components to subgrids
        //Add headers
        plaintiffAttorneyGrid.add(new Label("Plaintiff Attorney"), 0, 0, 5, 1);
        plaintiffWitnessGrid.add(new Label("Plaintiff Witness"), 0, 0, 5, 1);
        defenseAttorneyGrid.add(new Label("Defense Attorney"), 0, 0, 5, 1);
        defenseWitnessGrid.add(new Label("Defense Witness"), 0, 0, 5, 1);
        //add data
        for (int a = 0; a < 6; a++) {
            plaintiffAttorneyGrid.add(plaintiffAttorneyNames[a], 0, a + 1);
            for (int b = 0; b < 4; b++) {
                plaintiffAttorneyGrid.add(plaintiffAttorneyRanks[a][b], b + 1, a + 1);
            }
            plaintiffWitnessGrid.add(plaintiffWitnessNames[a], 0, a + 1);
            for (int b = 0; b < 4; b++) {
                plaintiffWitnessGrid.add(plaintiffWitnessRanks[a][b], b + 1, a + 1);
            }
            defenseAttorneyGrid.add(defenseAttorneyNames[a], 0, a + 1);
            for (int b = 0; b < 4; b++) {
                defenseAttorneyGrid.add(defenseAttorneyRanks[a][b], b + 1, a + 1);
            }
            defenseWitnessGrid.add(defenseWitnessNames[a], 0, a + 1);
            for (int b = 0; b < 4; b++) {
                defenseWitnessGrid.add(defenseWitnessRanks[a][b], b + 1, a + 1);
            }
        }
        //add subgrids to grid
        megaGrid.add(plaintiffAttorneyGrid, 0, 0);
        megaGrid.add(plaintiffWitnessGrid, 0, 1);
        megaGrid.add(defenseAttorneyGrid, 1, 0);
        megaGrid.add(defenseWitnessGrid, 1, 1);
        Button discardButton = new Button("Discard Changes");
        discardButton.setOnAction(e -> {
            this.close();
        });
        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> {
            saveChanges = true;
            this.close();
        });
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(discardButton, saveButton);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(megaGrid, buttonBox);
        Scene scene = new Scene(vbox);
        this.setScene(scene);
    }

    
    public boolean isSaveChanges(){
        return saveChanges;
    }
    
    public ArrayList<String> getUniqueNames(){
        final ArrayList<String> memberNames = new ArrayList<>();
        for(int a = 0;a<6;a++){
            if(!memberNames.contains(plaintiffAttorneyNames[a].getText()) && !plaintiffAttorneyNames[a].getText().equals("")){
                memberNames.add(plaintiffAttorneyNames[a].getText());
            }
            if(!memberNames.contains(plaintiffWitnessNames[a].getText()) && !plaintiffWitnessNames[a].getText().equals("")){
                memberNames.add(plaintiffWitnessNames[a].getText());
            }
            if(!memberNames.contains(defenseAttorneyNames[a].getText()) && !defenseAttorneyNames[a].getText().equals("")){
                memberNames.add(defenseAttorneyNames[a].getText());
            }
            if(!memberNames.contains(defenseWitnessNames[a].getText()) && !defenseWitnessNames[a].getText().equals("")){
                memberNames.add(defenseWitnessNames[a].getText());
            }
        }
        return memberNames;
    }
    
    /**
     * Checks if specified int array has any number>0 in it
     * As used, effectively checks whether an array of ranks actually contains any ranks
     * @param ranks int array of arbitrary size
     * @return true if at least one member is > 0; otherwise returns false
     */
    private boolean hasRanks(int[] ranks) {
        boolean hasRanks = false;
        for (int a = 0; a < ranks.length; a++) {
            if (ranks[a] > 0) {
                return true;
            }
        }
        return false;
    }

    public int[] getPlaintiffAttorneyRanks(int i){
        int[] ranks = new int[4];
        for(int a = 0;a<4;a++){
            ranks[a]=Integer.parseInt(plaintiffAttorneyRanks[i][a].getText());
        }
        return ranks;
    }
    public int[] getDefenseAttorneyRanks(int i){
        int[] ranks = new int[4];
        for(int a = 0;a<4;a++){
            ranks[a]=Integer.parseInt(defenseAttorneyRanks[i][a].getText());
        }
        return ranks;
    }
    public int[] getPlaintiffWitnessRanks(int i){
        int[] ranks = new int[4];
        for(int a = 0;a<4;a++){
            ranks[a]=Integer.parseInt(plaintiffWitnessRanks[i][a].getText());
        }
        return ranks;
    }
    public int[] getDefenseWitnessRanks(int i){
        int[] ranks = new int[4];
        for(int a = 0;a<4;a++){
            ranks[a]=Integer.parseInt(defenseWitnessRanks[i][a].getText());
        }
        return ranks;
    }
}
