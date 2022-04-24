package Exception;

//import Model.Elections;
//
//public class CheckingIdValidation {
//	public CheckingIdValidation(String id, Elections fastRound) throws OurException {
//		if (id.length() != 9) {
//			throw new OurException("Error, incorrect number of digits.\nTry again");
//		}
//		for (int i = 0; i < id.length(); i++) {
//			if (!Character.isDigit(id.charAt(i))) {
//				throw new OurException("Error, not all characters are digits.\nTry again");
//			}
//		}
//		if (!fastRound.checkDuplicateId(id)) {
//			throw new OurException("Error, this ID number is already registered to another citizen.\nTry again");
//		}
//	}
//}
