package de.rkable.foomuckl.core.action.judgment;

import static org.junit.Assert.*;

import org.junit.Test;

import de.rkable.foomuckl.core.action.judgment.SatisfiesNeed.Need;

public class JudgementCombinatorTest {

	@Test
	public void testThatAsimovsLawHolds() {
		JudgmentCombinator judgmentCombinator = new JudgmentCombinator();
		
		Judgment judgment = judgmentCombinator.combine(new HumanHarmed(), new HumanHarmed());
		assertTrue(judgment instanceof HumanHarmed);
		judgment = judgmentCombinator.combine(new HumanHarmed(), new RobotHarmed());
		assertTrue(judgment instanceof HumanHarmed);
		judgment = judgmentCombinator.combine(new OrderIgnored(), new HumanHarmed());
		assertTrue(judgment instanceof HumanHarmed);
		
		judgment = judgmentCombinator.combine(new OrderIgnored(), new OrderIgnored());
		assertTrue(judgment instanceof OrderIgnored);
		judgment = judgmentCombinator.combine(new OrderIgnored(), new SatisfiesNeed());
		assertTrue(judgment instanceof OrderIgnored);
		judgment = judgmentCombinator.combine(new SatisfiesNeed(), new OrderIgnored());
		assertTrue(judgment instanceof OrderIgnored);
		
		judgment = judgmentCombinator.combine(new RobotHarmed(), new RobotHarmed());
		assertTrue(judgment instanceof RobotHarmed);
		judgment = judgmentCombinator.combine(new RobotHarmed(), new SatisfiesNeed());
		assertTrue(judgment instanceof RobotHarmed);
		judgment = judgmentCombinator.combine(new SatisfiesNeed(), new RobotHarmed());
		assertTrue(judgment instanceof RobotHarmed);

		SatisfiesNeed satisfaction = new SatisfiesNeed();
		satisfaction.addSatisfaction(Need.NOT_BORED, 50);
		satisfaction.addSatisfaction(Need.SHARE_INFORMATION, 1);
		SatisfiesNeed satisfaction2 = new SatisfiesNeed();
		satisfaction2.addSatisfaction(Need.NOT_BORED, -10);
		satisfaction2.addSatisfaction(Need.SHARE_INFORMATION, 2);
		
		judgment = judgmentCombinator.combine(satisfaction, satisfaction2);
		assertTrue(judgment instanceof SatisfiesNeed);
		satisfaction = (SatisfiesNeed) judgment;
		assertEquals(40, satisfaction.getSatisfaction(Need.NOT_BORED));
		assertEquals(3, satisfaction.getSatisfaction(Need.SHARE_INFORMATION));
		
		
	}
}
