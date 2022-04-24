package Exception;

import Model.Elections;

public class CheckingValidElectionMonth {
	public CheckingValidElectionMonth(Elections fastRound, int electionMonth) throws OurException {
		if (fastRound.getPreviousElectionsYear() == fastRound.getElectionYear()) {
			if (fastRound.getPreviousElectionsMonth() >= electionMonth) {
				throw new OurException(
						"Last elections was at " + fastRound.getPreviousElectionsMonth() + ", try again");
			}
		}
	}
}
