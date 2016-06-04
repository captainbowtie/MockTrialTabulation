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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author captainbowtie
 */
public class MockTrialTabulation extends Application {

    final MenuBar menuBar = new MenuBar();
    final Menu fileMenu = new Menu("File");
    final MenuItem save = new MenuItem("Save...");
    final MenuItem open = new MenuItem("Open...");
    private Tournament tournament = new Tournament();
    private Stage primaryStage = new Stage();

    @Override
    public void start(Stage primaryStage) {
        displayTeamNumberPrompt();
        fileMenu.getItems().add(open);
        fileMenu.getItems().add(save);
        menuBar.getMenus().add(fileMenu);
        menuBar.setUseSystemMenuBar(true);
        open.setOnAction(e -> {
            loadTournament();
        });
        open.setAccelerator(KeyCombination.keyCombination("Meta+O"));
        save.setOnAction(e -> {
            saveTournament();
        });
        save.setAccelerator(KeyCombination.keyCombination("Meta+S"));
    }

    public void displayTeamNumberPrompt() {
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
            displayTeamDataPrompt(Integer.parseInt(numberOfTeams.getText()));
        });
        grid.add(btn, 1, 4);
        VBox vbox = new VBox();
        vbox.getChildren().add(menuBar);
        vbox.getChildren().add(grid);
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void displayTeamDataPrompt(int numberOfTeams) {
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

        CheckBox isByeTeam = new CheckBox("This team is the bye-team");
        grid.add(isByeTeam,0,2);
        
        Button addImpermissibleButton = new Button("Add Impermissible Match...");
        addImpermissibleButton.setOnAction((ActionEvent e) -> {
            int gridSize = grid.getChildren().size();
            if (gridSize == 7) {
                GridPane.setRowIndex(grid.getChildren().get(6), 5);
                GridPane.setRowIndex(grid.getChildren().get(5), 4);
                grid.add(new Label("Impermissible Match 1:"), 0, 3);
                grid.add(new TextField(), 1, 3);
            } else {
                GridPane.setRowIndex(grid.getChildren().get(6), gridSize / 2 + 2);
                GridPane.setRowIndex(grid.getChildren().get(5), gridSize / 2 + 1);
                grid.add(new Label("Impermissible Match " + (gridSize / 2 - 2) + ":"), 0, gridSize / 2 );
                grid.add(new TextField(), 1, gridSize / 2);
                primaryStage.sizeToScene();
            }

        });
        grid.add(addImpermissibleButton, 0, 3);

        Button okayButton = new Button("Next Team");
        okayButton.setOnAction((ActionEvent e) -> {
            final List<Integer> impermissibleMatches = new ArrayList<>();
            for (int i = 0; i < grid.getChildren().size() - 7; i = i + 2) {
                impermissibleMatches.add(Integer.parseInt(((TextField) grid.getChildren().get(i + 8)).getText()));
            }
            tournament.addTeam(Integer.parseInt(teamNumberField.getText()), teamNameField.getText(), impermissibleMatches, isByeTeam.isSelected());
            if (tournament.getTeams().size() == numberOfTeams) {
                displayTabulationWindow();
            } else {

                for (int i = grid.getChildren().size() - 1; i > 6; i--) {
                    grid.getChildren().remove(i);
                }
                teamNumberField.setText("");
                teamNameField.setText("");
                isByeTeam.setSelected(false);
            }
            if (tournament.getTeams().size() == numberOfTeams - 1) {
                okayButton.setText("Continue");
            }
        });
        grid.add(okayButton, 1, 4);
        primaryStage.getScene().setRoot(grid);
        primaryStage.sizeToScene();
    }

    private void displayTabulationWindow() {
        final Button[] teamNumberButtons = new Button[tournament.getTeams().size()];
        final Label[] teamNameLabels = new Label[tournament.getTeams().size()];
        final Label[][] teamSideLabels = new Label[tournament.getTeams().size()][4];
        final Label[][] teamOpponentLabels = new Label[tournament.getTeams().size()][4];
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
                teamSideLabels[a][b] = new Label();
                teamOpponentLabels[a][b] = new Label();
                teamPDFields[a][b] = new TextField();
                teamPDFields[a][b + 4] = new TextField();
                teamPDFields[a][b].setPrefColumnCount(2);
                teamPDFields[a][b + 4].setPrefColumnCount(2);
            }
            if (tournament.getTeam(a).getRound1Plaintiff()) {
                teamSideLabels[a][0].setText("π vs.");
                teamSideLabels[a][1].setText("∆ vs.");
            } else {
                teamSideLabels[a][0].setText("∆ vs.");
                teamSideLabels[a][1].setText("π vs.");
            }
            if (tournament.getTeam(a).getRound3Plaintiff()) {
                teamSideLabels[a][2].setText("π vs.");
                teamSideLabels[a][3].setText("∆ vs.");
            } else {
                teamSideLabels[a][2].setText("∆ vs.");
                teamSideLabels[a][3].setText("π vs.");
            }

            teamOpponentLabels[a][0].setText(Integer.toString(tournament.getTeam(a).getRound1Opponent()));
            teamOpponentLabels[a][1].setText(Integer.toString(tournament.getTeam(a).getRound2Opponent()));
            teamOpponentLabels[a][2].setText(Integer.toString(tournament.getTeam(a).getRound3Opponent()));
            teamOpponentLabels[a][3].setText(Integer.toString(tournament.getTeam(a).getRound4Opponent()));

            teamPDFields[a][0].setText(Integer.toString(tournament.getTeam(a).getRound1Ballot1PD()));
            teamPDFields[a][1].setText(Integer.toString(tournament.getTeam(a).getRound1Ballot2PD()));
            teamPDFields[a][2].setText(Integer.toString(tournament.getTeam(a).getRound2Ballot1PD()));
            teamPDFields[a][3].setText(Integer.toString(tournament.getTeam(a).getRound2Ballot2PD()));
            teamPDFields[a][4].setText(Integer.toString(tournament.getTeam(a).getRound3Ballot1PD()));
            teamPDFields[a][5].setText(Integer.toString(tournament.getTeam(a).getRound3Ballot2PD()));
            teamPDFields[a][6].setText(Integer.toString(tournament.getTeam(a).getRound4Ballot1PD()));
            teamPDFields[a][7].setText(Integer.toString(tournament.getTeam(a).getRound4Ballot2PD()));

            teamRecordLabels[a] = new Label(tournament.getTeam(a).getWins() + "-" + tournament.getTeam(a).getLoses() + "-" + tournament.getTeam(a).getTies());
            teamCSLabels[a] = new Label("CS: " + tournament.getTeamCS(tournament.getTeam(a).getTeamNumber()));
            teamPDLabels[a] = new Label("PD: " + tournament.getTeam(a).getPD());
        }

        for (int a = 0; a < tournament.getTeams().size(); a++) {
            grid.add(teamNumberButtons[a], 0, a * 2);
            grid.add(teamNameLabels[a], 0, a * 2 + 1);
            for (int b = 0; b < 4; b++) {
                grid.add(teamSideLabels[a][b], b * 2 + 1, a * 2);
                grid.add(teamOpponentLabels[a][b], b * 2 + 2, a * 2);
                grid.add(teamPDFields[a][b * 2], b * 2 + 1, a * 2 + 1);
                grid.add(teamPDFields[a][b * 2 + 1], b * 2 + 2, a * 2 + 1);
            }
            grid.add(teamRecordLabels[a], 9, a * 2, 2, 1);
            grid.add(teamCSLabels[a], 9, a * 2 + 1);
            grid.add(teamPDLabels[a], 10, a * 2 + 1);
        }

        //Upper Info Display
        final Label teamNumberHighLowLabel = new Label();
        if (tournament.isLowerTeamNumberIsHigherRank()) {
            teamNumberHighLowLabel.setText("Lower Team Numbers Are Ranked Better");
        } else {
            teamNumberHighLowLabel.setText("Higher Team Numbers Are Ranked Better");
        }
        final Label roundOddEvenPlaintiffLabel = new Label();
        if (tournament.isRound3Rank1IsPlaintiff()) {
            roundOddEvenPlaintiffLabel.setText("In round 3, even pairings are flip sides");
        } else {
            roundOddEvenPlaintiffLabel.setText("In round 3, odd pairings flip sides");
        }
        Button savePDsToTournament = new Button("Write Point Differentials");
        savePDsToTournament.setOnAction(e -> {
            for (int a = 0; a < tournament.getTeams().size(); a++) {
                tournament.getTeam(a).setRound1Ballot1PD(Integer.parseInt(teamPDFields[a][0].getText()));
                tournament.getTeam(a).setRound1Ballot2PD(Integer.parseInt(teamPDFields[a][1].getText()));
                tournament.getTeam(a).setRound2Ballot1PD(Integer.parseInt(teamPDFields[a][2].getText()));
                tournament.getTeam(a).setRound2Ballot2PD(Integer.parseInt(teamPDFields[a][3].getText()));
                tournament.getTeam(a).setRound3Ballot1PD(Integer.parseInt(teamPDFields[a][4].getText()));
                tournament.getTeam(a).setRound3Ballot2PD(Integer.parseInt(teamPDFields[a][5].getText()));
                tournament.getTeam(a).setRound4Ballot1PD(Integer.parseInt(teamPDFields[a][6].getText()));
                tournament.getTeam(a).setRound4Ballot2PD(Integer.parseInt(teamPDFields[a][7].getText()));
            }
            displayTabulationWindow();
        });
        HBox upperHBox = new HBox();
        upperHBox.getChildren().add(teamNumberHighLowLabel);
        upperHBox.getChildren().add(roundOddEvenPlaintiffLabel);
        upperHBox.getChildren().add(savePDsToTournament);
        upperHBox.setSpacing(5);

        //Pairing Buttons
        //TODO: consider moving pairing buttons to a menu?
        Button pairRound1 = new Button("Pair Round 1");
        Button pairRound2 = new Button("Pair Round 2");
        Button pairRound3 = new Button("Pair Round 3");
        Button pairRound4 = new Button("Pair Round 4");
        Button generateTabSummary = new Button("Generate Tab Summary");

        pairRound1.setOnAction(e -> {
            displayPairingConfirmationDialog(tournament.pairRound1(), 1);
        });
        pairRound2.setOnAction(e -> {
            displayPairingConfirmationDialog(tournament.pairRound2(), 2);
        });
        pairRound3.setOnAction(e -> {
            displayPairingConfirmationDialog(tournament.pairRound3(), 3);
        });
        pairRound4.setOnAction(e -> {
            displayPairingConfirmationDialog(tournament.pairRound4(), 4);
        });        

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(pairRound1, pairRound2, pairRound3, pairRound4, generateTabSummary);
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        VBox mainVBox = new VBox();
        mainVBox.getChildren().add(menuBar);
        mainVBox.getChildren().add(upperHBox);
        mainVBox.getChildren().add(scrollPane);
        mainVBox.getChildren().add(buttonBox);
        primaryStage.hide();
        primaryStage.getScene().setRoot(mainVBox);
        primaryStage.show();
    }

    private void displayPairingConfirmationDialog(int[][] proposedPairings, int roundNumber) {
        final ComboBox[][] teamSelectors = new ComboBox[proposedPairings.length][2];
        final Label[] vsText = new Label[proposedPairings.length];
        final Stage pairingConfirmation = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        for (int a = 0; a < proposedPairings.length; a++) {
            teamSelectors[a][0] = new ComboBox();
            teamSelectors[a][1] = new ComboBox();
            vsText[a] = new Label("vs.");
            for (int b = 0; b < tournament.getTeams().size(); b++) {

                teamSelectors[a][0].getItems().add(tournament.getTeam(b).getTeamNumber());
                teamSelectors[a][1].getItems().add(tournament.getTeam(b).getTeamNumber());
            }
        }
        grid.add(new Label("Proposed Round " + roundNumber + " Pairings"), 0, 0, 3, 1);
        for (int a = 0; a < teamSelectors.length; a++) {
            teamSelectors[a][0].getSelectionModel().select(Integer.valueOf(proposedPairings[a][0]));
            teamSelectors[a][1].getSelectionModel().select(Integer.valueOf(proposedPairings[a][1]));
            grid.add(teamSelectors[a][0], 0, a + 1);
            grid.add(vsText[a], 1, a + 1);
            grid.add(teamSelectors[a][1], 2, a + 1);
        }
        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(e -> {
            pairingConfirmation.hide();
        });
        Button acceptButton = new Button("Accept");
        acceptButton.setDefaultButton(true);
        acceptButton.setOnAction(e -> {
            int[][] pairings = new int[proposedPairings.length][2];
            for (int a = 0; a < pairings.length; a++) {
                pairings[a][0] = teamSelectors[a][0].getSelectionModel().getSelectedIndex();
                pairings[a][1] = teamSelectors[a][1].getSelectionModel().getSelectedIndex();
            }
            tournament.writePairingsToTournament(pairings, roundNumber);
            pairingConfirmation.hide();
            displayTabulationWindow();
        });
        grid.add(cancelButton, 0, teamSelectors.length + 1);
        grid.add(acceptButton, 2, teamSelectors.length + 1);
        Scene gridDisplay = new Scene(grid);
        pairingConfirmation.setScene(gridDisplay);
        pairingConfirmation.initModality(Modality.WINDOW_MODAL);
        pairingConfirmation.showAndWait();
    }

    /**
     * Prompts user for file location to save tournament data to, and then
     * passes that location on to a class which writes the tournament to the
     * location
     *
     * @param primaryStage included until I figure out how to better pass stages
     * around
     */
    private void saveTournament() {
        FileChooser saveLocationChooser = new FileChooser();
        saveLocationChooser.setTitle("Save Tournament");
        File saveLocation = saveLocationChooser.showSaveDialog(new Stage());
        SpreadsheetHandler.saveToSpreadsheet(tournament, saveLocation);
    }

    /**
     * Prompts user for file location to load tournament data from, and then
     * passes that location on to a class which reads the tournament from the
     * location
     *
     * @param primaryStage included until I figure out how to better pass stages
     * around
     */
    private void loadTournament() {
        FileChooser openLocationChooser = new FileChooser();
        openLocationChooser.setTitle("Open Tournament");
        File tournamentFileLocation = openLocationChooser.showOpenDialog(new Stage());
        tournament = SpreadsheetHandler.loadFromSpreadsheet(tournamentFileLocation);
        displayTabulationWindow();
    }
}
