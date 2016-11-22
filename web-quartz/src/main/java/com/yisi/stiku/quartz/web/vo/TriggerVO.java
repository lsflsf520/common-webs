package com.yisi.stiku.quartz.web.vo;

import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;

public class TriggerVO {
	private Trigger trigger;

	private TriggerState state;

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	public TriggerState getState() {
		return state;
	}

	public void setState(TriggerState state) {
		this.state = state;
	}

}
