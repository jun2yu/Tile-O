package ca.mcgill.ecse223.tileo.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.DesignModeController;
import ca.mcgill.ecse223.tileo.controller.PlayModeController;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.TileO;

import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class TeleportPopOut extends JFrame {

	private JPanel contentPane;
//	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeleportPopOut frame = new TeleportPopOut();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void close() { 
		this.setVisible(false);
	    this.dispose();
	}
	public TeleportPopOut() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblYouHaveDrawn = new JLabel("You have drawn a Teleport action card, please select a tile ");
		lblYouHaveDrawn.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblOnTheBoard = new JLabel("on the Board to teleport to and click on the \"Teleport\" button. ");
		lblOnTheBoard.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnTeleport = new JButton("Ok");
		btnTeleport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.print("lmao");
				close();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addComponent(lblOnTheBoard))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(36)
							.addComponent(lblYouHaveDrawn)))
					.addContainerGap(25, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(188, Short.MAX_VALUE)
					.addComponent(btnTeleport)
					.addGap(177))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblYouHaveDrawn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblOnTheBoard)
					.addPreferredGap(ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
					.addComponent(btnTeleport)
					.addGap(30))
		);
		contentPane.setLayout(gl_contentPane);
	}
//	private class SwingAction extends AbstractAction {
//		public SwingAction() {
//			putValue(NAME, "SwingAction");
//			putValue(SHORT_DESCRIPTION, "Some short description");
//		}
//		
//		public void actionPerformed(ActionEvent e) {
//			String cmd = e.getActionCommand();
//			
//			if(cmd.equals("Teleport"))
//			{
//				PlayModeController pmc = new PlayModeController();
//				System.out.print("lmao");
//			}
//
//			
//		}
//	}
}