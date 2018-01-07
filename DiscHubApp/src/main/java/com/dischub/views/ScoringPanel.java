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
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ScoringPanel extends GluonPresenter<DiscHub> {

    @FXML
    private View scoringPanel;

    @FXML
    private Button btnTeamAScore;

    @FXML
    private Button btnTeamBScore;

    @FXML
    private Button btnEndGame;

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
    private VBox vbxBackingPane;

    @FXML
    private AnchorPane ancPlayerGrid;

    private GridPane playerGrid;

    private Team teamA = new Team("Heat");
    private Team teamB = new Team("Poole");

    private int teamAScore = 0;
    private int teamBScore = 0;

    private Player[] teamARoster;
    private Player[] teamBRoster;

    private Point tempPoint;

    private ArrayList<Point> points = new ArrayList<>();

    public void initialize() {
        scoringPanel.setShowTransitionFactory(BounceInRightTransition::new);
        lblTeamAName.setText(teamA.getTeamName());
        lblTeamBName.setText(teamB.getTeamName());

        teamARoster = getTeamRoster(teamA);
        teamBRoster = getTeamRoster(teamB);

        scoringPanel.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = getApp().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> getApp().showLayer(DRAWER_LAYER)));
                appBar.setTitleText("Scoring " + teamA.getTeamName() + " v " + teamB.getTeamName());

            }
        });
    }

    @FXML
    private void teamAScore() {
        btnTeamAScore.setDisable(true);
        btnTeamBScore.setDisable(true);
        drawAssistGrid(teamA);
    }

    @FXML
    private void teamBScore() {
        btnTeamAScore.setDisable(true);
        btnTeamBScore.setDisable(true);
        drawAssistGrid(teamB);
    }

    @FXML
    private void endGame() {
        System.out.println("The Game Ended " + teamA.getTeamName() + ":" + teamAScore + " v " + teamB.getTeamName() + ":" + teamBScore);
        for (Point pnt : points) {
            System.out.println(pnt.toString());
        }
    }

    private void drawAssistGrid(Team team) {
        playerGrid = new GridPane();
        lblFeedback.setText("Select the Assister");
        Button btnCallaghan = new Button("Callaghan");
        btnCallaghan.setPrefWidth(1000);
        btnCallaghan.setOnAction((ActionEvent ae) -> {
            playerClicked(null, team);
            btnCallaghan.setDisable(true);
        });

        for (int i = 0; i < getTeamRoster(team).length - 1; i++) {
            Player player = getTeamRoster(team)[i];
            Button b = new Button(player.getShirtNum());
            b.setPrefWidth(1000);
            b.setOnAction((ActionEvent event) -> {
                playerClicked(player, team);
                b.setDisable(true);
                btnCallaghan.setDisable(true);
            });
            playerGrid.add(b, i % 4, (int) i / 4);
        }

        playerGrid.add(btnCallaghan, 3, getTeamRoster(team).length / 4);
        AnchorPane.setLeftAnchor(playerGrid, 0.0);
        AnchorPane.setRightAnchor(playerGrid, 0.0);
        ancPlayerGrid.getChildren().add(playerGrid);
    }

    private void playerClicked(Player p, Team team) {

        if (tempPoint == null) {
            //must be an assist
            tempPoint = new Point();
            tempPoint.setAssister(p);
            lblFeedback.setText("Select the Scorer");
        } else {
            //already have an assist, so need an scorer
            if (tempPoint.getAssister() == null) {
                //callaghan, so set score and assist as same
                tempPoint.setAssister(p);
                tempPoint.setScorer(p);
            } else {
                //normal score
                tempPoint.setScorer(p);
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
            ancPlayerGrid.getChildren().remove(playerGrid);
            btnTeamAScore.setDisable(false);
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
                roster = new Player[10];
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
}
