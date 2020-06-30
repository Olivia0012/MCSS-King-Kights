

/**
 * The Great hall class,in it:
 * - Knight and King Arthur can know whether he can enter the Great Hall;
 * - they know whether meeting begins.
 * - they know whether they can leave the Great hall.
 * - King can start the meeting when all knights entered the Great Hall sit down.
 * - King can ends the meeting when all knights stand up.
 * - Knight can sit and stand up from the Round table.
 * 
 * @author Lu Wang 1054195
 *
 */

public class Hall {
	private String hallName;
	private Agenda agendaNew;
	private Agenda agendaComplete;

	// king will be true when King Arthur enters the Great hall
	private boolean king = false;
	
	
	// meeting will be true when meeting begins.
	private boolean meeting = false;
	
	// numbers of entering the Great hall knights.
	private volatile int enteredHall = 0;
	
	// numbers of sitting down knights .
	private volatile int roundTable = 0;

	
	public Hall(String hallName, Agenda agendaNew, Agenda agendaComplete) {
		this.hallName = hallName;
		this.agendaNew = agendaNew;
		this.agendaComplete = agendaComplete;
	}
	
	// Knight checks whether King enters the Great hall.
	public boolean isKing() {
		return king;
	}


	// Knight checks whether the meeting begins.
	public boolean isMeeting() {
		return meeting;
	}

	// Check whether King Arthur enters the Great Hall.
	public synchronized void enterGreathall(int knightId) {
		// wait until no King is in the Great Hall.
		while (king == true) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// King Arthur enters.
		if (knightId == 0) {
			System.out.println("King Arthur enters the Great Hall.");
			king = true;
		} else {
			enteredHall++;
			System.out.println("Knight " + knightId + " enters the Great Hall.");
		}
		notifyAll();
	}

	// King starts the meeting until all entered Great hall knights sit down.
	public synchronized void meetingBegins() {
		// when some knights haven't sitted down, then wait.
		while (roundTable < enteredHall) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Meeting begins.");
		meeting = true;
		notifyAll();
	}

	// King ends the meeting until no knight sits.
	public synchronized void meetingEnds() {
		// when some knights still sit, meeting can not end.
		while (roundTable != 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Meeting ends.");
		meeting = false;
		notifyAll();
	}

	// King Arthur can exit the Great Hall or knights can leave the Great Hall until
	// meeting ends.
	public synchronized void waitToLeave(int knightId) {
		while (meeting == true) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// King Arthur exits the Great hall.
		if (knightId == 0) {
			System.out.println("King Arthur exits the Great Hall.");
			king = false;
		}
		notifyAll();
	}

	// Knight exits the Great Hall until the King exits.
	public synchronized void KnightExits(int knightId) {
		// Knights can not exit until King exits.
		while (king == true) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		enteredHall--;
		System.out.println("Knight " + knightId + " exits the Great Hall.");
		notifyAll();
	}

	// Knight sits at the Round Table.
	public synchronized void KnightSits(int knightId) {
		roundTable++;
		System.out.println("Knight " + knightId + " sits  at the Round Table.");
		notifyAll();
	}

	// Knight stands from the Round Table.
	public synchronized void KnightStand(int knightId) {
		roundTable--;
		System.out.println("Knight " + knightId + " stands from the Round Table.");
		notifyAll();
	}

}
