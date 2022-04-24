package Exception;

import Model.Elections;

public class CheckingValidElectionYear {

	public CheckingValidElectionYear(Elections fastRound, int electionYear) throws OurException {
		if(fastRound.getPreviousElectionsYear() == electionYear) {
			if(fastRound.getPreviousElectionsMonth() == 12) {
				throw new OurException("Last elections was at " + fastRound.getPreviousElectionsMonth() + " in this year.\nTry again");
			}
		}
		if (fastRound.getPreviousElectionsYear() > electionYear) {
			throw new OurException("Last elections was at " + fastRound.getPreviousElectionsYear() + ".\nTry again");
		}
		if (fastRound.getPreviousElectionsYear() + 100 < electionYear) {
			throw new OurException("It does not make sense that it has been so long since the last election.\nTry again");
		}
	}
}
