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
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author captainbowtie
 */
public class MockTrialTabulation extends Application {

    private Tournament tournament = new Tournament();

    @Override
    public void start(Stage primaryStage) {
        displayTeamNumberPrompt(primaryStage);
    }

    public void displayTeamNumberPrompt(Stage primaryStage) {
        primaryStage.setTitle("Mock Trial Tabulation");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label numberPrompt = new Label("Number of teams:");
        grid.add(numberPrompt, 0, 1);

        TextField numberOfTeams = new TextField();
        numberOfTeams.setPrefColumnCount(2);
        grid.add(numberOfTeams, 1, 1);

        Button btn = new Button("Okay");
        btn.setOnAction((ActionEvent e) -> {
            displayTeamDataPrompt(primaryStage, Integer.parseInt(numberOfTeams.getText()));
        });
        grid.add(btn, 1, 4);
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void displayTeamDataPrompt(Stage primaryStage, int numberOfTeams) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label teamNumberLabel = new Label("Team Number:");
        grid.add(teamNumberLabel, 0, 0);

        TextField teamNumberField = new TextField();
        teamNumberField.setPrefColumnCount(4);
        grid.add(teamNumberField, 1, 0);

        Label teamNameLabel = new Label("Team Name:");
        grid.add(teamNameLabel, 0, 1);

        TextField teamNameField = new TextField();
        teamNameField.setPrefColumnCount(12);
        grid.add(teamNameField, 1, 1);

        Button addImpermissibleButton = new Button("Add Impermissible Match...");
        addImpermissibleButton.setOnAction((ActionEvent e) -> {
            int gridSize = grid.getChildren().size();
            if (gridSize == 6) {
                GridPane.setRowIndex(grid.getChildren().get(5), 4);
                GridPane.setRowIndex(grid.getChildren().get(4), 3);
                grid.add(new Label("Impermissible Match 1:"), 0, 2);
                grid.add(new TextField(), 1, 2);
            } else {
                GridPane.setRowIndex(grid.getChildren().get(5), gridSize / 2 + 1);
                GridPane.setRowIndex(grid.getChildren().get(4), gridSize / 2);
                grid.add(new Label("Impermissible Match " + (gridSize / 2 - 2) + ":"), 0, gridSize / 2 - 1);
                grid.add(new TextField(), 1, gridSize / 2 - 1);
                primaryStage.sizeToScene();
            }

        });
        grid.add(addImpermissibleButton, 0, 2);

        Button okayButton = new Button("Next Team");
        okayButton.setOnAction((ActionEvent e) -> {
            final List<Integer> impermissibleMatches = new ArrayList<>();
            for (int i = 0; i < grid.getChildren().size() - 6; i = i + 2) {
                impermissibleMatches.add(Integer.parseInt(((TextField) grid.getChildren().get(i + 7)).getText()));
            }
            tournament.addTeam(Integer.parseInt(teamNumberField.getText()), teamNameField.getText(), impermissibleMatches);
            if (tournament.getTeams().size() == numberOfTeams) {
                displayTabulationWindow(primaryStage);
            } else {

                for (int i = grid.getChildren().size() - 1; i > 5; i--) {
                    grid.getChildren().remove(i);
                }
                teamNumberField.setText("");
                teamNameField.setText("");
            }
            if (tournament.getTeams().size() == numberOfTeams - 1) {
                okayButton.setText("Continue");
            }
        });
        grid.add(okayButton, 3, 3);

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
    }

    private void displayTabulationWindow(Stage primaryStage) {
        final Button[] teamNumberButtons = new Button[tournament.getTeams().size()];
        final Label[] teamNameLabels = new Label[tournament.getTeams().size()];
        final ComboBox[][] teamSideSelectors = new ComboBox[tournament.getTeams().size()][4];
        final TextField[][] teamOpponentFields = new TextField[tournament.getTeams().size()][4];
        final TextField[][] teamPDFields = new TextField[tournament.getTeams().size()][8];
        final Label[] teamRecordLabels = new Label[tournament.getTeams().size()];
        final Label[] teamCSLabels = new Label[tournament.getTeams().size()];
        final Label[] teamPDLabels = new Label[tournament.getTeams().size()];

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        for (int a = 0; a < tournament.getTeams().size(); a++) {
            teamNumberButtons[a] = new Button(Integer.toString(tournament.getTeam(a).getTeamNumber()));
            teamNameLabels[a] = new Label(tournament.getTeam(a).getTeamName());
            for (int b = 0; b < 4; b++) {
                ObservableList<String> sideStrings = FXCollections.observableArrayList(
                        "π vs.",
                        "∆ vs."
                );
                teamSideSelectors[a][b] = new ComboBox(sideStrings);
                teamOpponentFields[a][b] = new TextField();
                teamOpponentFields[a][b].setPrefColumnCount(4);
                teamPDFields[a][b] = new TextField();
                teamPDFields[a][b + 4] = new TextField();
                teamPDFields[a][b].setPrefColumnCount(2);
                teamPDFields[a][b + 4].setPrefColumnCount(2);
            }
            if (tournament.getTeam(a).getRound1Plaintiff()) {
                teamSideSelectors[a][0].getSelectionModel().select(0);
                teamSideSelectors[a][1].getSelectionModel().select(1);
            }else{
                teamSideSelectors[a][0].getSelectionModel().select(1);
                teamSideSelectors[a][1].getSelectionModel().select(0);
            }
            if (tournament.getTeam(a).getRound3Plaintiff()) {
                teamSideSelectors[a][2].getSelectionModel().select(0);
                teamSideSelectors[a][3].getSelectionModel().select(1);
            }else{
                teamSideSelectors[a][2].getSelectionModel().select(1);
                teamSideSelectors[a][3].getSelectionModel().select(0);
            }
            
            teamOpponentFields[a][0].setText(Integer.toString(tournament.getTeam(a).getRound1Opponent()));
            teamOpponentFields[a][1].setText(Integer.toString(tournament.getTeam(a).getRound2Opponent()));
            teamOpponentFields[a][2].setText(Integer.toString(tournament.getTeam(a).getRound3Opponent()));
            teamOpponentFields[a][3].setText(Integer.toString(tournament.getTeam(a).getRound4Opponent()));
            
            teamPDFields[a][0].setText(Integer.toString(tournament.getTeam(a).getRound1Ballot1PD()));
            teamPDFields[a][1].setText(Integer.toString(tournament.getTeam(a).getRound1Ballot2PD()));
            teamPDFields[a][2].setText(Integer.toString(tournament.getTeam(a).getRound2Ballot1PD()));
            teamPDFields[a][3].setText(Integer.toString(tournament.getTeam(a).getRound2Ballot2PD()));
            teamPDFields[a][4].setText(Integer.toString(tournament.getTeam(a).getRound3Ballot1PD()));
            teamPDFields[a][5].setText(Integer.toString(tournament.getTeam(a).getRound3Ballot2PD()));
            teamPDFields[a][6].setText(Integer.toString(tournament.getTeam(a).getRound4Ballot1PD()));
            teamPDFields[a][7].setText(Integer.toString(tournament.getTeam(a).getRound4Ballot2PD()));
            
            teamRecordLabels[a] = new Label(tournament.getTeam(a).getWins()+"-"+tournament.getTeam(a).getLoses()+"-"+tournament.getTeam(a).getTies());
            teamCSLabels[a] = new Label("CS: "+tournament.getTeamCS(tournament.getTeam(a).getTeamNumber()));
            teamPDLabels[a] = new Label("PD: "+tournament.getTeam(a).getPD());
        }

        for (int a = 0; a < tournament.getTeams().size(); a++) {
            grid.add(teamNumberButtons[a], 0, a * 2);
            grid.add(teamNameLabels[a], 0, a * 2 + 1);
            for (int b = 0; b < 4; b++) {
                grid.add(teamSideSelectors[a][b], b * 2 + 1, a * 2);
                grid.add(teamOpponentFields[a][b], b * 2 + 2, a * 2);
                grid.add(teamPDFields[a][b], b + 1, a * 2 + 1);
                grid.add(teamPDFields[a][b + 4], b + 5, a * 2 + 1);
            }
            grid.add(teamRecordLabels[a], 9, a * 2, 2, 1);
            grid.add(teamCSLabels[a], 9, a * 2 + 1);
            grid.add(teamPDLabels[a], 10, a * 2 + 1);
        }

        Button pairRound1 = new Button("Pair Round 1");
        Button pairRound2 = new Button("Pair Round 2");
        Button pairRound3 = new Button("Pair Round 3");
        Button pairRound4 = new Button("Pair Round 4");
        Button generateTabSummary = new Button("Generate Tab Summary");
        Button saveTournament = new Button("Save...");

        pairRound1.setOnAction((ActionEvent e) -> {
            tournament.pairRound1();
            displayTabulationWindow(primaryStage);
        });
        
        saveTournament.setOnAction((ActionEvent e) -> {
            FileChooser saveLocationChooser = new FileChooser();
            saveLocationChooser.setTitle("Save Tournament");
            File saveLocation = saveLocationChooser.showSaveDialog(new Stage());
            SpreadsheetHandler.saveToSpreadsheet(tournament, saveLocation);
        });

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(pairRound1, pairRound2, pairRound3, pairRound4, generateTabSummary, saveTournament);
        ScrollPane scrollPane = new ScrollPane(grid);
        VBox mainVBox = new VBox();
        mainVBox.getChildren().add(scrollPane);
        mainVBox.getChildren().add(buttonBox);
        Scene scene = new Scene(mainVBox);
        primaryStage.setScene(scene);
    }
}
