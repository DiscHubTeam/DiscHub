package com.dischub.views;

import static com.gluonhq.charm.glisten.afterburner.DefaultDrawerManager.DRAWER_LAYER;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.dischub.DiscHub;
import com.dischub.scoring.Point;
import com.dischub.tournament.Player;
import com.dischub.tournament.Team;
import com.dischub.tournament.Tournament;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ScoringPanelPresenter extends GluonPresenter<DiscHub> {

    @FXML
    private View scoringPanel;

    @FXML
    private Button btnTeamAScore;

    @FXML
    private Button btnTeamBScore;

    @FXML
    private Button btnUndo;

    @FXML
    private Button btnEndOfGame;

    @FXML
    private Label lblTeamAScore;

    @FXML
    private Label lblTeamAName;

    @FXML
    private Label lblTeamBName;

    @FXML
    private Label lblTeamBScore;

    @FXML
    private Label lblFeedback;

    @FXML
    private Label lblTime;

    @FXML
    private VBox vbxPlayerGrid;

    @FXML
    private HBox hbxPreGame;

    @FXML
    private ResourceBundle resources; //pulls the strings in from .properties file

    private GridPane playerGrid;
    private Button btnStart;

    private Team teamA = new Team("Heat");
    private Team teamB = new Team("Poole");

    private int teamAScore = 0;
    private int teamBScore = 0;

    private int secsRemaining;

    private Player[] teamARoster;
    private Player[] teamBRoster;

    private Point tempPoint;

    private ArrayList<Point> points = new ArrayList<>();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");

    public void initialize() {
        scoringPanel.setShowTransitionFactory(BounceInRightTransition::new);

        secsRemaining = getCompetitionRules().getDefaultMatchDurationSecs();
        lblTime.setText(Integer.toString(secsRemaining));
        btnStart = new Button(resources.getString("start"));
        btnStart.setPrefHeight(50);

        btnStart.setOnAction((event) -> {
            Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler() {

                @Override
                public void handle(Event event) {
                    secsRemaining--;

                    if (secsRemaining < 0) {
                        //if out of time, make the clock flash for a few seconds'
                        if (secsRemaining % 2 == 0) {
                            lblTime.setStyle("-fx-font-weight:bold; -fx-font-size:16;");
                        } else {
                            lblTime.setStyle("-fx-font-weight:normal; -fx-font-size:16;");
                        }

                        if (secsRemaining < -5) {
                            timeline.stop();
                        }

                    } else {
                        //time remaining to format properly, concat a 0 if the seconds is <9 avoiding eg: 14:5
                        if (secsRemaining % 60 > 9) {
                            lblTime.setText(Integer.toString(secsRemaining / 60) + ":" + Integer.toString(secsRemaining % 60));
                        } else {
                            lblTime.setText(Integer.toString(secsRemaining / 60) + ":0" + Integer.toString(secsRemaining % 60));
                        }
                    }
                }
            }));
            timeline.playFromStart();

            btnTeamAScore.setDisable(false);
            btnTeamBScore.setDisable(false);
            btnUndo.setDisable(false);
            btnEndOfGame.setDisable(false);
            vbxPlayerGrid.getChildren().remove(hbxPreGame);
        });

        lblTeamAName.setText(teamA.getTeamName());
        lblTeamBName.setText(teamB.getTeamName());

        btnTeamAScore.setText(teamA.getTeamName() + " " + resources.getString("score"));
        btnTeamBScore.setText(teamB.getTeamName() + " " + resources.getString("score"));

        teamARoster = getTeamRoster(teamA);
        teamBRoster = getTeamRoster(teamB);

        scoringPanel.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = getApp().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> getApp().showLayer(DRAWER_LAYER)));
                appBar.setTitleText(resources.getString("scoring") + " " + teamA.getTeamName() + " vs " + teamB.getTeamName());

            }
        });
    }

    @FXML
    private void cancelScoring() {
        AppViewManager.PRIMARY_VIEW.switchView();
    }

    @FXML
    private void agreeToScoreGame() {
        //show the start timer button, clear other pre-game items
        hbxPreGame.getChildren().remove(0, hbxPreGame.getChildrenUnmodifiable().size());
        hbxPreGame.getChildren().add(btnStart);
    }

    @FXML
    private void teamAScore() {
        btnTeamAScore.setDisable(true);
        btnTeamBScore.setDisable(true);
        drawPlayerSelectionGrid(teamA);
    }

    @FXML
    private void teamBScore() {
        btnTeamAScore.setDisable(true);
        btnTeamBScore.setDisable(true);
        drawPlayerSelectionGrid(teamB);
    }

    @FXML
    private void endGame() {
        System.out.println("The Game Ended " + teamA.getTeamName() + ":" + teamAScore + " v " + teamB.getTeamName() + ":" + teamBScore);
        for (Point pnt : points) {
            System.out.println(pnt.toString());
        }
    }

    @FXML
    private void undo() {
        System.out.println("Undoing last point");
        if (vbxPlayerGrid.getChildren().isEmpty() && teamAScore == 0 && teamBScore == 0) {
            //Do nothing as undo has no effect on a 0-0 game with no player selected
        } else if (vbxPlayerGrid.getChildren().isEmpty()) {
            //we're not selecting a player, so undo the entire last score
            if (points.get(points.size() - 1).getTeam() == teamA) {
                teamAScore--;
                lblTeamAScore.setText(Integer.toString(teamAScore));
            } else {
                teamBScore--;
                lblTeamBScore.setText(Integer.toString(teamBScore));
            }
            lblFeedback.setText(resources.getString("feedback.removed"));
            points.remove(points.size() - 1);
        } else {
            //we're mid-score, so better undo the assister, not the previous point.
            tempPoint = null;
            vbxPlayerGrid.getChildren().remove(playerGrid);
            btnTeamAScore.setDisable(false);
            btnTeamBScore.setDisable(false);
            lblFeedback.setText(resources.getString("feedback.cancelled"));
        }
    }

    private void drawPlayerSelectionGrid(Team team) {
        playerGrid = new GridPane();
        lblFeedback.setText(resources.getString("feedback.assister"));
        Button btnCallaghan = new Button(resources.getString("callaghan"));
        btnCallaghan.setPrefWidth(1000);
        btnCallaghan.setPrefHeight(75);
        //special case: callaghan is like an own goal, the intercepting catcher is accredited with both score and assist
        btnCallaghan.setOnAction((ActionEvent ae) -> {
            playerClicked(null, team);
            btnCallaghan.setDisable(true);//if a callaghan is scored, you must select the scorer
        });

        for (int i = 0; i < getTeamRoster(team).length; i++) {
            Player player = getTeamRoster(team)[i];
            Button b = new Button(player.getShirtNum());
            b.setPrefWidth(1000);
            b.setPrefHeight(75);
            b.setOnAction((ActionEvent event) -> {
                playerClicked(player, team);
                b.setDisable(true);//player can't assist and then score (see callaghan above)
                btnCallaghan.setDisable(true);//if there is an assister, can't be a callaghan
            });
            playerGrid.add(b, i % 4, (int) i / 4);
        }

        playerGrid.add(btnCallaghan, 3, getTeamRoster(team).length / 4);//far-right button on bottom row is always callaghan
        AnchorPane.setLeftAnchor(playerGrid, 0.0);
        AnchorPane.setRightAnchor(playerGrid, 0.0);
        vbxPlayerGrid.getChildren().add(playerGrid);
    }

    private void playerClicked(Player p, Team team) {

        if (tempPoint == null) {
            //is null and thus must be an assist
            tempPoint = new Point();
            tempPoint.setAssister(p);//p is null for callaghan, but as temppoint is not null, this block will not be executed again
            lblFeedback.setText(resources.getString("feedback.scorer"));
        } else {
            //already have an assist, so need an scorer
            if (tempPoint.getAssister() == null) {
                //callaghan, so set score and assist as same
                tempPoint.setAssister(p);
                tempPoint.setScorer(p);
                tempPoint.setTeam(team);
            } else {
                //normal score
                tempPoint.setScorer(p);
                tempPoint.setTeam(team);
            }

            points.add(tempPoint);

            if (team == teamA) {
                teamAScore++;
                lblTeamAScore.setText(Integer.toString(teamAScore));
            } else {
                teamBScore++;
                lblTeamBScore.setText(Integer.toString(teamBScore));
            }

            lblFeedback.setText("");
            tempPoint = null;//reset the temp ready for next score
            //hide selection grid
            vbxPlayerGrid.getChildren().remove(playerGrid);
            btnTeamAScore.setDisable(false);//enable the user to log another score
            btnTeamBScore.setDisable(false);
            playerGrid = null;
        }
    }

    /**
     * If the team roster has not been loaded, loads the roster from the
     * database. If it is held locally, the database is not queried.
     *
     * @param team the team whose roster is requested
     * @return an array of players (aka a roster)
     */
    private Player[] getTeamRoster(Team team) {
        //Note: currently generates an array of players if roster not held locally.
        //This is a temporary measure for testing purposes
        if (team == teamA && teamARoster != null) {
            return teamARoster;
        } else if (team == teamB && teamBRoster != null) {
            return teamBRoster;
        } else {
            //queryDatabase for players;
            Player[] roster;
            if (team.getTeamName().equals("Poole")) {
                roster = new Player[10];
                int i = 0;
                for (Player p : roster) {
                    p = new Player("Poole Player " + i, Integer.toString(i + 10));
                    roster[i] = p;
                    i++;
                }
            } else {
                roster = new Player[7];
                int i = 0;
                for (Player p : roster) {
                    p = new Player("Heat Player " + i, Integer.toString(i + 40));
                    roster[i] = p;
                    i++;
                }
            }
            return roster;
        }
    }

    private Tournament getCompetitionRules() {
        //This either needs to be passed as a parameter (as with teams) or held as a static variable.
        // This is hardcoded at 20secs for testing
        return new Tournament(8, /*60**/ 20);
    }
}
