package com.mattdavben.emeraldsisters;

import com.google.common.eventbus.Subscribe;

public interface StateChangeListener {

	@Subscribe
	public void listen(StateChangeEvent event);

}
