
/**
 * New Aganda and Complete Agenda Class.
 * - Producer can add new quest to the New Agenda.
 * - Comsumer can delete the completed quest from the Complete Agenda.
 * - Knights can acquire new quest from the New Agenda.
 * - The number of quests in each agenda should not be greater than the number of knights.
 * 
 *  @author Lu Wang 1054195
 */

import java.util.ArrayList;
import java.util.List;

public class Agenda {
	
	private String agenda;
	
	//Quests list in each agenda.
	private volatile List<Quest> quests = new ArrayList<Quest>();
	
	public Agenda(String agenda) {
		this.agenda = agenda;
	}

	// knigts acquire for the new quest when there is one.
	public synchronized Quest acquireNewQuest(int knightId) {
		this.isEmpty();
		Quest q =  quests.get(0);
		System.out.println("Knight " + knightId + " acquires the Quest " + quests.get(0).getId());
		quests.remove(0);
		notifyAll();
		return q;
	}

	
	//Producer uses this function to generate new quest for the new Agenda.
	public synchronized Quest addNew(Quest quest) {
		this.isFull();
		this.quests.add(quest);
		System.out.println(quest.toString() + " added to " + agenda + ". " );
		notifyAll();
		return quest;
	}

	// when a knight releases a completed quest, then this quest will be added to the completed agenda.
	public synchronized void releaseQuest(Quest quest, int knightId) {
		this.isFull();
		this.quests.add(quest);
		System.out.println("Knight " + knightId + " releases the Quest " + quest.getId());
		notifyAll();
	}

	//Consumer removes the completed quest from the complete agenda.
	public synchronized void removeComplete() {
		this.isEmpty();
		System.out.println(quests.get(0).toString() + " removed from " + agenda + ". " );
		quests.remove(0);
		notifyAll();
	}
	
	
	//Wait until quests are not empty.
	public void isEmpty() {
		while (quests.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//Wait until quests are not full.
	public void isFull() {
		while (quests.size() == Params.NUM_KNIGHTS) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
