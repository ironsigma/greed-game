package com.izylab.greed;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.izylab.greed.Board.Direction;

/**
 * Greed Application.
 */
public final class GreedApp extends JFrame
		implements BoardListener, KeyListener {

	/** Logger. */
	private static final Logger LOG = Logger.getLogger(GreedApp.class);

	/** Version. */
	private static final long serialVersionUID = 1L;

	/** Width. */
	private static final int BOARD_WIDTH = 30;

	/** Height. */
	private static final int BOARD_HEIGHT = 20;

	/** Board. */
	private Board board;

	/** Score. */
	private int score = 0;

	/** Items cleared. */
	private int cleared = 0;

	/** Status label. */
	private JLabel statusLabel = new JLabel("Let's begin");

	/**
	 * Constructor.
	 */
	public GreedApp() {
		board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
		board.init(5);
		board.addListener(this);
		JPanel content = new JPanel(new BorderLayout());
		content.add(board);
		content.add(statusLabel, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addKeyListener(this);
		setTitle("Greed");
		getContentPane().add(content);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Main.
	 * @param args Args
	 */
	public static void main(final String[] args) {
		new GreedApp();
	}

	@Override
	public void keyTyped(final KeyEvent e) {
		/* empty */
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		/* empty */
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			board.move(Direction.DOWN);
			return;

		case KeyEvent.VK_UP:
			board.move(Direction.UP);
			return;

		case KeyEvent.VK_LEFT:
			board.move(Direction.LEFT);
			return;

		case KeyEvent.VK_RIGHT:
			board.move(Direction.RIGHT);
			return;

		default:
			LOG.error("Uh?! Invalid direction");
		}
	}

	@Override
	public void moved(Cell[] cells) {
		for (Cell cell : cells) {
			score += cell.getValue();
		}
		cleared += cells.length;
		String percent = String.format("%.2f",
				((double) cleared / (BOARD_WIDTH * BOARD_HEIGHT)) * 100);
		statusLabel.setText("  " + cleared + " tiles cleared, " + percent
				+ "% complete. Current Score: " + score);
	}

}
