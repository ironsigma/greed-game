package com.izylab.greed;

/** Board Listener. */
public interface BoardListener {
	/**
	 * Board moved.
	 * @param cells Cells consumed
	 */
	void moved(final Cell[] cells);
}
