package com.dischub.tournament;

import java.util.ArrayList;

/**
 *
 * @author Rowan
 */
public class FantasySelection {

    private int userID;//the fantasy frisbee player's id
    private Player captain;//the ID of the 'captain' - for if playing double pts for captain.
    private ArrayList<Player> selection;
    private CompetitionRules competitionRules;

    public FantasySelection(int uid, CompetitionRules rules) {
        this.userID = uid;
        selection = new ArrayList<>();
        competitionRules = rules;
    }

    public String addPlayer(Player player) {
        String errorString = validateSelection(player);
        if (errorString == null) {//if the error string is empty
            //player selection is valid, add to selection
            selection.add(player);
            System.out.println("adding player");
        }

        return errorString; // we return either a message explaining why it can't be added or null (meaning it has been added)
    }

    private String validateSelection(Player player) {
        boolean playerValid = true;
        int maleCount = 0;
        int femaleCount = 0;
        ArrayList<Team> teamList = new ArrayList<Team>();
        String errorString = null;

        for (Player p : selection) {
            if (p.getGender() == Gender.FEMALE) {
                femaleCount++;
            } else {
                maleCount++;//according to ultimate guidelines, 'not female' classes as 'open' in mixed classification
            }
            teamList.add(p.getTeam());
        }
        System.out.println("Selection: "+ selection.size()+" rules say "+ competitionRules.getTeamSize());
        if (selection.size() >= competitionRules.getTeamSize()) {
            playerValid = false;
            errorString="Maximum amount of players selected";
        }

        if (competitionRules.getMixedRule() != MixedRule.OPEN) {
            //TODO check genders

            if (competitionRules.getMixedRule() == MixedRule.MIXED && playerValid) {
                if (player.getGender() == Gender.FEMALE && femaleCount == (competitionRules.getTeamSize() / 2) + 1) {
                    playerValid = false;
                    errorString = "Maximum amount of females for mixed reached";
                } else if ((player.getGender() == Gender.MALE || player.getGender() == Gender.UNKNOWN) && maleCount == (competitionRules.getTeamSize() / 2) + 1) {
                    playerValid = false;
                    errorString = "Maximum amount of non-females for mixed reached";
                }
            } else if (competitionRules.getMixedRule() == MixedRule.LOOSE_MIXED) {
                if ((player.getGender() == Gender.MALE || player.getGender() == Gender.UNKNOWN) && maleCount == competitionRules.getTeamSize() - 1) {
                    playerValid = false;
                    errorString = "Maximum amount of non-females for loose mixed reached";
                } else if (player.getGender() == Gender.FEMALE && femaleCount == competitionRules.getTeamSize() - 1) {
                    playerValid = false;
                    errorString = "Maximum amount of females for loose mixed reached";
                }
            }

        }
        if (competitionRules.getTeamRule() == TeamRule.ONE_PER_TEAM && playerValid) {
            //TODO check if we have somebody from the same team
            for (Team t : teamList) {
                if (t == player.getTeam()) {
                    errorString = "You have already selected a player from this team";
                    break;
                }
            }
        }
        return errorString;
    }
    
    public ArrayList<Player> getSelection(){
        return selection;
    }
}
