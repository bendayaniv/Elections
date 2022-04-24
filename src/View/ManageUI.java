package View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import Model.Elections;
import Model.Party.eFaction;

public interface ManageUI extends Serializable {

	void readFile() throws FileNotFoundException, IOException, ClassNotFoundException;

	void creatingBallotBox(String fullAddress, int type);

	void createCitizen(String citizenName, String id, int birthYear, boolean carryWeapon, int numOfDaysInIsolation);

	void createParty(String partyName, eFaction faction, ArrayList<Integer> fullDate);

	void createCandidate(Elections elections, String citizenName, String partyName);

	void printBallotListDetails();

	void printCitizenListDetails();

	void printPartyListDetails(boolean printEmptyParty);

	void voting(int choosenPartyNum);

	void printResultsForEachBallot();

	void printResultsForEachParty();

	void endingElections() throws FileNotFoundException, IOException;

}
