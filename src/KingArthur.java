

/**
 * KingArthur thread, KingArthur can 
 * - enter/exit the Great Hall
 * - begin/ end a meeting
 * - wait some time after a meeting.
 * 
 * @author Lu Wang 1054195
 *
 */


public class KingArthur extends Thread {

	private Hall hall;

	public KingArthur(Hall hall) {
		this.hall = hall;
	}

	public void run() {
		while (true) {
			//King enters the Great Hall.
			hall.enterGreathall(0);
			
			//Meeting begins.
			hall.meetingBegins();
			
			//Meeting ends.
			hall.meetingEnds();
			
			//King exits the Great Hall.
			hall.waitToLeave(0);
			
			//King waits some time after a meeting.
			try {
				sleep(Params.getKingWaitingTime());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
