package Exception;

//import Model.Candidate;
//import Model.Citizen;
//import Model.Elections;
//
//public class CheckingValidPartyIndex {
//	public CheckingValidPartyIndex(Elections fastRound, int partyIndex, Citizen chosenCitizen) throws OurException {
//		boolean partyFound = false;
//		for (int i = 0; i < fastRound.getAllParties().size(); i++) {
//			if ((fastRound.getAllParties().get(i) != null) && partyIndex == i) {
//				if (chosenCitizen instanceof Candidate
//						&& ((Candidate) chosenCitizen).getParty() == fastRound.getAllParties().get(i)) {
//					throw new OurException("It is your current party... try again...");
//				}
//				partyFound = true;
//				break;
//			}
//		}
//		if (!partyFound) {
//			throw new OurException("Error, party number in the list was not found, please try again");
//		}
//	}
//}
