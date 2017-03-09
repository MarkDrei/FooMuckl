package de.rkable.foomuckl.core.action.handler;


import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.SaySomething;
import de.rkable.foomuckl.core.action.judgment.Judgment;
import de.rkable.foomuckl.core.action.judgment.SatisfiesNeed;
import de.rkable.foomuckl.core.action.judgment.SatisfiesNeed.Need;

public class SpeechHandler extends AbstractActionHandler {

	private static final int BOREDOM_CHANGE = -5000;

	@Override
	public void handle(Action action) {
		if (action instanceof SaySomething) {
			System.out.println("FooMuckl says: \"" + ((SaySomething) action).getSpeech() + "\"");
		}
	}
	
	@Override
	public Judgment judge(Action action) {
		if (action instanceof SaySomething) {
			SatisfiesNeed satisfiesNeed = new SatisfiesNeed();
			satisfiesNeed.addSatisfaction(Need.NOT_BORED, BOREDOM_CHANGE);
			
			SaySomething say = (SaySomething) action;
			if (say.isRelevant()) {
				satisfiesNeed.addSatisfaction(Need.SHARE_INFORMATION, 1);
			}
			return satisfiesNeed;
		} 
		return super.judge(action);
	}
	
}
