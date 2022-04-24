package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import Model.Ballot.eBallotType;
import Model.Candidate.Roles;
import Model.Party.eFaction;
import View.ManageUI;

@SuppressWarnings("serial")
public class Model implements ManageUI, Serializable {

	private ArrayList<Party> parties;
	private ArrayList<Ballot<?>> ballots;
	private MySet<Citizen> citizens;
	private Elections fastRound;

	private int ballotSerialNum;
	private String citizenName, partyName;
	@SuppressWarnings("rawtypes")
	private Ballot chosenBallotBox;
	private Citizen chosenCitizen;
	private Party chosenParty;
	private int citizenIndex, candidateIndex, partyIndex, ballotIndex;
	StringBuffer ballotResults, partyResults;

	public Model() throws FileNotFoundException, ClassNotFoundException, IOException {
		parties = hardCodedParties();
		ballots = hardCodedBallots();
		citizens = hardCodedCitizens(ballots, parties);
		Collections.sort(parties, ComparePartyByName);
		Collections.sort(ballots, CompareBallotBySerialNumber);
		fastRound = new Elections();
		citizenIndex = 0;
		candidateIndex = -1;
		partyIndex = -1;
	}

	public Elections getObject() {
		return fastRound;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	Comparator<Party> ComparePartyByName = new Comparator<Party>() {

		@Override
		public int compare(Party p1, Party p2) {
			return p1.getPartyName().compareTo(p2.getPartyName());
		}
	};

	@SuppressWarnings("rawtypes")
	Comparator<Ballot> CompareBallotBySerialNumber = new Comparator<Ballot>() {

		@Override
		public int compare(Ballot b1, Ballot b2) {
			return Double.compare(b1.getSerialNumber(), b2.getSerialNumber());
		}
	};

	private static ArrayList<Ballot<?>> hardCodedBallots() {
		ArrayList<Ballot<?>> ballots = new ArrayList<>();
		ballots.add(new Ballot<Candidate>("Hod Hasharon, Sheshet Hayamim, 29", eBallotType.regularBallot));
		ballots.add(new Ballot<Candidate>("Ashdod, Rosh Shlomo, 62", eBallotType.regularBallot));
		ballots.add(new Ballot<Candidate>("Tel aviv, Ben David, 25", eBallotType.regularCoronaBallot));
		ballots.add(new Ballot<Soldier>("Haifa, Ben Gurion, 15", eBallotType.militaryBallot));
		ballots.add(new Ballot<Soldier>("Naharya, Trumpeldor, 32", eBallotType.militaryCoronaBallot));
		return ballots;
	}

	private static ArrayList<Party> hardCodedParties() {
		ArrayList<Party> parties = new ArrayList<>();
		ArrayList<Integer> arr = new ArrayList<>();
		arr.add(3);
		arr.add(4);
		arr.add(1965);
		parties.add(new Party("The Work", eFaction.Left, arr, null));
		arr.set(0, 25);
		arr.set(1, 4);
		arr.set(2, 12);
		parties.add(new Party("The Free", eFaction.Right, arr, null));
		arr.set(0, 12);
		arr.set(1, 6);
		arr.set(2, 1980);
		parties.add(new Party("The Peace", eFaction.Middle, arr, null));
		arr.set(0, 17);
		arr.set(1, 2);
		arr.set(2, 1987);
		parties.add(new Party("Daydream", eFaction.Middle, arr));

		return parties;
	}

	private static MySet<Citizen> hardCodedCitizens(ArrayList<Ballot<?>> ballots, ArrayList<Party> parties) {
		MySet<Citizen> citizens = new MySet<>();
		Citizen citizen0 = new Citizen("Ron Beker", "115443879", 1990, ballots.get(0), 0);
		Citizen citizen1 = new Citizen("Michelle Mayer", "564429902", 1996, ballots.get(1), 0);
		Citizen citizen2 = new Citizen("Alon Shahar", "334222981", 1994, ballots.get(1), 0);
		Citizen citizen3 = new Citizen("Ganz Manilik", "232265773", 1980, ballots.get(2), 4);
		Citizen citizen4 = new Citizen("Moshe Oren", "112875849", 1964, ballots.get(2), 4);
		Citizen citizen5 = new Citizen("Viki Ben-Hal", "236534974", 1991, ballots.get(0), 0);
		Citizen citizen8 = new Citizen("Yossi Benayoun", "456456456", 1950, ballots.get(1), 0);
		Citizen citizen9 = new Citizen("Yovel Blyzar", "789789789", 1993, ballots.get(0), 0);
		citizens.add(new Candidate(citizen0, parties.get(0), Roles.PartyLeader));
		citizens.add(new Candidate(citizen1, parties.get(1), Roles.PartyLeader));
		citizens.add(new Candidate(citizen2, parties.get(2), Roles.PartyLeader));
		citizens.add(new Candidate(citizen3, parties.get(3), Roles.PartyLeader));
		citizens.add(new Candidate(citizen4, parties.get(0), Roles.Member));
		citizens.add(new Candidate(citizen5, parties.get(1), Roles.Member));
		citizens.add(new Soldier("Shelly Shminov", "312846854", 2002, ballots.get(3), 0, false));
		citizens.add(new Citizen("Shimon Cohen", "123123123", 1955, ballots.get(1), 0));
		citizens.add(new Candidate(citizen8, parties.get(2), Roles.Member));
		citizens.add(new Candidate(citizen9, parties.get(3), Roles.Member));
		citizens.add(new Citizen("Guy Gershon", "367834179", 1991, ballots.get(0), 0));
		citizens.add(new Citizen("Gabriel Hason", "245368763", 1987, ballots.get(1), 0));
		citizens.add(new Citizen("Yuval Ramon", "217854271", 1983, ballots.get(2), 7));
		return citizens;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void readFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("elections.dat"));
		Elections startRound = (Elections) inFile.readObject();

		fastRound.setCitizensList(startRound.getAllCitizens());
		fastRound.setBallotBoxes(startRound.getAllBallotBoxes());

		fastRound.setPartiesList(startRound.getAllParties());
		fastRound.setPreviousElectionsYear(startRound.getElectionYear());
		fastRound.setPreviousElectionsMonth(startRound.getElectionMonth());
		Ballot.setSERIAL_NUMBER(
				fastRound.getAllBallotBoxes().get(startRound.getAllBallotBoxes().size() - 1).getSerialNumber());

		inFile.close();
	}

	public void resetingResults() {
		fastRound.resetingResults();
	}

	public void setElectionsData() {
		fastRound = new Elections(ballots, citizens, parties);
		fastRound.setPreviousElectionsYear(2020);
		fastRound.setPreviousElectionsMonth(12);
	}

	// Checking existence of file with old data
	public boolean checkingExistenseOfOldData() {
		File file = new File("elections.dat");
		if (file.exists() && !file.isDirectory()) {
			return true;
		}
		return false;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setYear(int electionYear) {
		fastRound.setYear(electionYear);
	}

	public int getPreviousElectionsYear() {
		return fastRound.getPreviousElectionsYear();
	}

	public int getElectionYear() {
		return fastRound.getElectionYear();
	}

	public void setMonth(int electionMonth) {
		fastRound.setMonth(electionMonth);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void creatingBallotBox(String fullAddress, int type) {
		fastRound.createAndAddBallotBox(fullAddress, type);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getBallotType(String ballotType) {
		int type = 0;
		eBallotType[] allBallotTypes = eBallotType.values();
		for (int i = 0; i < allBallotTypes.length; i++)
			if (ballotType.equals(allBallotTypes[i].toString()))
				type = i;
		return type;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createCitizen(String citizenName, String id, int birthYear, boolean carryWeapon,
			int numOfDaysInIsolation) {
		fastRound.addCitizen(citizenName, id, birthYear, carryWeapon, numOfDaysInIsolation);

	}

	public boolean checkingValidId(String s) {
		if (s.length() != 9)
			return true;
		else
			return false;
	}

	public boolean checkDuplicateId(String id) {
		return !fastRound.checkDuplicateId(id);
	}

	public boolean checkValidBirthDate(int birthYear) {
		if ((fastRound.getElectionYear() - birthYear) <= 17)
			return true;
		else
			return false;
	}

	public int checkAge(int age) {

		return fastRound.getElectionYear() - age;
	}

	public boolean checkingID(String id) {
		if (fastRound.checkDuplicateId(id))
			return true;
		else
			return false;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createParty(String partyName, eFaction faction, ArrayList<Integer> fullDate) {
		Party newParty = new Party(partyName, faction, fullDate);
		fastRound.addParty(newParty);

	}

	public boolean checkEstablishmentDate(ArrayList<Integer> fullDate) {
		if (fullDate.get(1) >= fastRound.getElectionMonth() && fullDate.get(2) == fastRound.getElectionYear())
			return false;
		else if (fullDate.get(2) > fastRound.getElectionYear())
			return false;
		else
			return true;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createCandidate(Elections elections, String citizenName, String partyName) {
		MySet<Citizen> citizens = fastRound.getAllCitizens();
		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.getObject(i).getName().equals(citizenName)) {
				candidateIndex = i;
//				citizenIndex = i;
				chosenCitizen = citizens.getObject(i);
			}
		}
		ArrayList<Party> parties = fastRound.getAllParties();
		for (int i = 0; i < parties.size(); i++)
			if (parties.get(i).getPartyName().equals(partyName))
				chosenParty = parties.get(i);

		if (chosenCitizen instanceof Candidate) {
			((Candidate) chosenCitizen).getParty().removeCandidateFromParty(chosenCitizen, 0);
		}

		fastRound.givingRoleToNewCandidate(chosenCitizen, chosenParty, candidateIndex, -1);
	}

	public Citizen getChosenCitizen() {
		return chosenCitizen;
	}

	public boolean samePartyForCandidate() {
		if (chosenCitizen instanceof Candidate && ((Candidate) chosenCitizen).getPartyName() == partyName)
			return true;
		else
			return false;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void printBallotListDetails() {
		for (int i = 0; i < fastRound.getAllBallotBoxes().size(); i++) {
			if (fastRound.getAllBallotBoxes().get(i).getSerialNumber() == (ballotSerialNum)) {
				chosenBallotBox = fastRound.getAllBallotBoxes().get(i);
				break;
			}
		}
	}

	public ArrayList<Ballot<?>> getAllBallotBox() {
		return fastRound.getAllBallotBoxes();
	}

	public void setChosenBallotSerialNum(int chosenSerialNum) {
		ballotSerialNum = chosenSerialNum;
	}

	public String getChosenBallotBoxInformation() {
		return chosenBallotBox.toString();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void printCitizenListDetails() {
		for (int i = 0; i < fastRound.getAllCitizens().size(); i++) {
			if (fastRound.getAllCitizens().getObject(i).getName().equals(citizenName)) {
				chosenCitizen = fastRound.getAllCitizens().getObject(i);
			}
		}
	}

	public MySet<Citizen> getAllCitizens() {
		return fastRound.getAllCitizens();
	}

	public void setChosenCitizenName(String chosenCitizenName) {
		citizenName = chosenCitizenName;
	}

	public String getChosenCitizenInformation() {
		return chosenCitizen.toString();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void printPartyListDetails(boolean printEmptyParty) {
		for (int i = 0; i < fastRound.getAllParties().size(); i++) {
			if (fastRound.getAllParties().get(i).getPartyName().equals(partyName)) {
				chosenParty = fastRound.getAllParties().get(i);
			}
		}
	}

	public ArrayList<Party> getAllParties() {
		return fastRound.getAllParties();
	}

	public void setChosenPartyName(String ChosenPartyName) {
		partyName = ChosenPartyName;
	}

	public String getChosenPartyInformation() {
		return chosenParty.toString();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void voting(int choosenPartyNum) {
		(fastRound.getAllCitizens().getObject(getCitizenIndex())).getBallotBox().setNewResults(choosenPartyNum);
	}

	public void setHowManyChoices() {
		for (int i = 0; i < fastRound.getAllBallotBoxes().size(); i++) {
			(fastRound.getAllBallotBoxes().get(i)).setHowManyChoices(fastRound.getAllParties().size());
		}
	}

	public void addAnotherChoice() {
		for (int i = 0; i < fastRound.getAllBallotBoxes().size(); i++) {
			(fastRound.getAllBallotBoxes().get(i)).addAnotherChoice();
		}
	}

	public int sampleForResultsSize() {
		return (fastRound.getAllBallotBoxes().get(0)).getResultsSize();
	}
	
	public Party getPartyInfo(String partyName) {
		for(int i = 0; i < fastRound.getAllParties().size(); i++) {
			if(fastRound.getAllParties().get(i).getPartyName().equals(partyName)) {
				return fastRound.getAllParties().get(i);
			}
		}
		return null;
	}

	public int getCitizensListSize() {
		return getAllCitizens().size();
	}

	public int getCitizenIndex() {
		return citizenIndex;
	}

	public int getPartyIndex() {
		return partyIndex;
	}

	public void setPartyIndex(String partyName) {
		ArrayList<Party> parties = fastRound.getAllParties();
		for (int i = 0; i < parties.size(); i++) {
			if (parties.get(i).getPartyName().equals(partyName)) {
				partyIndex = i;
			}
		}
	}

	public void setCitizenIndex(int num) {
		citizenIndex = num;
	}

	public void getToTheNextCitizen() {
		citizenIndex++;
	}

	public boolean ifCitizenInQuarentine() {
		if (getAllCitizens().getObject(getCitizenIndex()).getNumOfDaysInQuarantine() != 0) {
			return true;
		} else {
			return false;
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void printResultsForEachBallot() {
		int sizeParty = fastRound.getAllParties().size();
		StringBuffer msg;
		if(fastRound.getAllBallotBoxes().get(ballotIndex).getResults().size() == 0){
			msg = new StringBuffer("Cant show results for a ballot that \n was created after the elections");
		}else {	
			msg = new StringBuffer("The results for  ballot number "
				+ fastRound.getAllBallotBoxes().get(ballotIndex).getSerialNumber() + ":\n");
			(fastRound.getAllBallotBoxes().get(ballotIndex)).setVotersPercentage();
			msg.append("Ballot S.R. : " + (fastRound.getAllBallotBoxes().get(ballotIndex)).getSerialNumber() + "\n");
			if ((fastRound.getAllBallotBoxes().get(ballotIndex)).getNumOfVoters() == 0)
				msg.append("No voters assagined to it.\n");
			else {
				msg.append("The results: \nPercentage of voters: "
					+ (fastRound.getAllBallotBoxes().get(ballotIndex)).getVotersPercentage() + "\n");
				for (int j = 0; j < sizeParty; j++) {
					if (fastRound.getAllParties().get(j) != null) {
						msg.append((j + 1) + ". party: " + (fastRound.getAllParties().get(j)).getPartyName() + " they got: "
							+ (fastRound.getAllBallotBoxes().get(ballotIndex)).getResults().get(j) + " votes\n");
					}
				}
			}
		}
		setBallotResults(msg);
	}

	private void setBallotResults(StringBuffer msg) {
		ballotResults = msg;
	}

	public StringBuffer getBallotResults() {
		return ballotResults;
	}

	public void setBallotIndex(int serialNumber) {
		int size = fastRound.getAllBallotBoxes().size();
		for (int i = 0; i < size; i++) {
			if (fastRound.getAllBallotBoxes().get(i).getSerialNumber() == serialNumber) {
				ballotIndex = i;
			}
		}
	}

	@Override
	public void printResultsForEachParty() {
		StringBuffer msg = new StringBuffer();
		int sizeBallot = fastRound.getAllBallotBoxes().size();
		int sumForEachParty = 0;
		for (int j = 0; j < sizeBallot; j++) {
			if (fastRound.getAllBallotBoxes().get(j) != null) {
				sumForEachParty += ((int) (fastRound.getAllBallotBoxes().get(j)).getResults().get(partyIndex));
			}
		}
		msg.append("The party " + (fastRound.getAllParties().get(partyIndex)).getPartyName() + ", got - "
				+ sumForEachParty + " votes\n");

		setChosenPartyResults(msg);

	}

	private void setChosenPartyResults(StringBuffer msg) {
		partyResults = msg;
	}

	public StringBuffer getPartyResults() {
		return partyResults;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void endingElections() throws FileNotFoundException, IOException {
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("elections.dat"));
		outFile.writeObject(fastRound);
		outFile.close();
	}

	public Elections getElectionsObject() {
		return fastRound;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}