

/**
 * Knight thread:
 * it can:
 *  - enter the Great Hall when the King Arther haven't entered.
 *  - sit down at the Round Table before meeting starts.
 *  - acquire a new quest and release a completed quest during meeting.
 *  - stand up from the table after acquiring a new quest.
 *  - leave the great Hall after the Kning Arthur leaves.
 *  - set off to complete the quest and complete the quest before enter the Great Hall.
 * 
 * @author Lu Wang 1054195
 *
 */

public class Knight extends Thread {
	private int knightId; 	// Knight's Id.
	private Agenda agendaNew;
	private Agenda agendaComplete;
	private Hall hall;
	private boolean enterHall = false;	//whether the knight enters the Great Hall.
	private boolean sitDown = false;	//whether the knight sits down.
	private Quest quest;	// the quest was acquired by the knight.

	//Constructor of Class Knight 
	public Knight(int knightId, Agenda agendaNew, Agenda agendaComplete, Hall hall) {
		this.knightId = knightId;
		this.agendaNew = agendaNew;
		this.agendaComplete = agendaComplete;
		this.hall = hall;
	}

	public void run() {
		while (true) {
			
			//Knight can enter the Great hall when he has no or a completed quest.
			if (quest == null || quest.completed == true) {
				//Knight enters the Great Hall.
				if (enterHall == false) {
					hall.enterGreathall(knightId);
					enterHall = true;
				}
				
				//check whether the knight sits down.
				if (sitDown == false) {
					hall.KnightSits(knightId);
					sitDown = true;
				}
				
				//Time for discussing adventure with one another and sit at the Round table.
				try {
					sleep(Params.getMinglingTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//Meeting begins.
				if (hall.isMeeting() == true) {
					//if the knight has acquired a quest then let him release the quest during meeting.
					if (quest != null) {
						agendaComplete.releaseQuest(quest, knightId);
						
						quest = null;
					}
					
					//Time of questing duration.
					try {
						sleep(Params.getQuestingTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//Acquire a new quest.
					quest = agendaNew.acquireNewQuest(knightId);
					
					
					//stand up from the Round table.
					hall.KnightStand(knightId);
					sitDown = false;
					//wait for meeting ends.
					hall.waitToLeave(knightId);
				}
			}
			
			//leave the Great hall to complete the new quest.
			if (quest != null && quest.completed == false) {
				hall.KnightExits(knightId);
				enterHall = false;
				System.out.println("Knight " + knightId + " sets off to complete the " + quest.toString());
			
				//Time for completing the acquired quest.
				try {
					sleep(Params.MEAN_QUESTING_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//Complete the quest
				if(hall.isKing() != true) {
					quest.completed = true;
					System.out.println("Knight " + knightId + " completes the " + quest.toString());
				}
			}
		}
	}
}
