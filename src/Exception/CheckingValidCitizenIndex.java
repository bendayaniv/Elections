package Exception;

//import Model.Elections;
//
//public class CheckingValidCitizenIndex {
//	public CheckingValidCitizenIndex(Elections fastRound, int citizenIndex) throws OurException {
//		boolean citizenFound = false;
//		for (int i = 0; i < fastRound.getAllCitizens().size(); i++) {
//			if (fastRound.getAllCitizens().getObject(i) != null && citizenIndex == i) {
//				if (fastRound.getCitizenAge(fastRound.getAllCitizens().getObject(i)) <= 21) {
//					throw new OurException((fastRound.getAllCitizens().getObject(i)).getName()
//							+ " is a soldiers and can't be candidate... Try again...");
//				}
//				citizenFound = true;
//				break;
//			}
//		}
//		if (!citizenFound) {
//			throw new OurException("Couldn't find the citizen in the list, please try again");
//		}
//	}
//}
