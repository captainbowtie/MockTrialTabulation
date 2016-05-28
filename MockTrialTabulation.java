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
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.geometry.HPos.RIGHT;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
                GridPane.setRowIndex(grid.getChildren().get(5), gridSize/2+1);
                GridPane.setRowIndex(grid.getChildren().get(4), gridSize/2);
                grid.add(new Label("Impermissible Match " + (gridSize/2-2) + ":"), 0, gridSize/2-1);
                grid.add(new TextField(), 1, gridSize/2-1);
                primaryStage.sizeToScene();
            }

        });
        grid.add(addImpermissibleButton, 0, 2);

        Button okayButton = new Button("Next Team");
        okayButton.setOnAction((ActionEvent e) -> {
            final List<Integer> impermissibleMatches = new ArrayList<>();
            for(int i=0;i<grid.getChildren().size()-6;i=i+2){
                impermissibleMatches.add(Integer.parseInt(((TextField)grid.getChildren().get(i+7)).getText()));
            }
            tournament.addTeam(Integer.parseInt(teamNumberField.getText()),teamNameField.getText(),impermissibleMatches);
            if(tournament.getTeams().size()==numberOfTeams){
                //whateverComesNext();
            }else{
                
                for(int i=grid.getChildren().size()-1;i>5;i--){
                    grid.getChildren().remove(i);
                }
                teamNumberField.setText("");
                teamNameField.setText("");
            }
            if(tournament.getTeams().size()==numberOfTeams-1){
                okayButton.setText("Continue");
            }
        });
        grid.add(okayButton, 3, 3);

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
    }
}
