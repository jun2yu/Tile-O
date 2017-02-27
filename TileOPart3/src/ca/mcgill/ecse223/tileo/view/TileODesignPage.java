package ca.mcgill.ecse223.tileo.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;

public class TileODesignPage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TileODesignPage frame = new TileODesignPage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public TileODesignPage() {
		initComponents();
		refreshData();
	}
	
	public void close() { 
		this.setVisible(false);
	    this.dispose();
	}
	
	private void initComponents(){
								
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setSize(1200, 700);
		setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		
		
		
		JButton btnOpenNewWindow = new JButton("Add Tiles");
		btnOpenNewWindow.addActionListener(new ActionListener() {
			
	
			public void actionPerformed(ActionEvent e) {
				
				AddTilePopOut te = new AddTilePopOut();
				te.setVisible(true);
			}

			
		});
		
		JButton btnRemoveTiles = new JButton("Remove Tile");
		btnRemoveTiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		JButton btnAddConnections = new JButton("Add Connections");
		btnAddConnections.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnRemoveConnections = new JButton("Remove Connections");
		
		JButton btnCreateDeck = new JButton("Add Deck");
		btnCreateDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeckPopOut d = new DeckPopOut();
				d.setVisible(true);
			}
		});
		
		JButton btnCreateGame = new JButton("Create Game");
		btnCreateGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(1008, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCreateGame, Alignment.TRAILING)
						.addComponent(btnOpenNewWindow, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRemoveTiles, Alignment.TRAILING)
						.addComponent(btnAddConnections, Alignment.TRAILING)
						.addComponent(btnRemoveConnections, Alignment.TRAILING)
						.addComponent(btnCreateDeck, Alignment.TRAILING))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnCreateGame)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOpenNewWindow)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRemoveTiles)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddConnections)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRemoveConnections)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCreateDeck)
					.addContainerGap(458, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
		

	private void refreshData(){
		
	}

}
