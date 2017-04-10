/*********************************************************
 *	File:			UserInterface.java
 *	Date:			12/24/2016
 *	Author:			Yukun Chen
 *
 *	Description:	This class is used to build up the user
 *					interface for the sound imitation system.
 *					It will trace the motion like clicking
 *					and trigger the related component.					
 ********************************************************/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UserInterface extends JFrame implements ActionListener,Runnable{	//build up the interface
	private JPanel jp;				// a jpanel to hold all the elements
	private JPanel jp_north;
	private JPanel jp_north_north;
	private JPanel jp_north_south;
	private JButton jb1;			//jbutton to record and stop recording the sound
	private JButton jb2;			//jbutton to play the recording
	private JButton jb3;			//jbutton for search
	private JButton jb4;			//jbutton for reset
	private JList jlist;			//a jlist to list all the returned files
	private DefaultListModel model = new DefaultListModel();
	private JScrollPane jsp;
	private JComboBox jbox;			//a jcombobox to select category
	private JTextField jtext;
	private boolean recording = false;
	private String[] matchedList = new String[100];
	public static ArrayList<file> library = new ArrayList<file>();
	
	//String[] catagory = {"Acoustic Instruments", "Commercial Synthesizers", "Everyday", "Single Synthesizer"};
	String[] catagory = {"All", "Machine", "Animal", "Effect", "Everyday", "Alarm", "Music Instrument", "Nature"};
	public UserInterface(){
		this.setSize(550, 500);		//set the size of the UserInterface	
		jp = new JPanel();			//initiate functions
		jp_north = new JPanel();
		jp_north_north = new JPanel();
		jp_north_south = new JPanel();
		jb1 = new JButton();
		jb2 = new JButton();
		jb3 = new JButton();
		jb4 = new JButton();
		jbox = new JComboBox(catagory);
		
		jlist = new JList(model);
		jsp = new JScrollPane(jlist);
		jtext = new JTextField("Type something to search!", 20);
		jb1.setText("Record");
		jb2.setText("Play Back");
		jb3.setText("Search");
		jb4.setText("Reset");
		
		jb1.addActionListener(this);		//implement the actionlistener when clicked
		jb2.addActionListener(this);	
		jb3.addActionListener(this);
		jb4.addActionListener(this);
		jtext.addActionListener(this);
		jbox.addActionListener(this);
		jp.setSize(500, 500);
		jp.setLayout(new BorderLayout());
		jp.add(jp_north, BorderLayout.NORTH);
		jp_north.setLayout(new BorderLayout());
		jp_north.add(jp_north_north, BorderLayout.NORTH);
		jp_north.add(jp_north_south, BorderLayout.SOUTH);
		jp.add(jsp, BorderLayout.CENTER);
		jp_north_north.add(jb1);
		jp_north_north.add(jb2);
		jp_north_north.add(jb3);
		jp_north_north.add(jb4);
		jp_north_south.add(jbox);
		jp_north_south.add(jtext);
		this.add(jp);				//add UserInterface to jpanel
		jlist.addMouseListener(mouseListener);
		jtext.addKeyListener(keylistener);
	}
	
	public void actionPerformed(ActionEvent e){		//method to trigger different accoridng to the action
		MultiThread.stop();			//whenever a change, stop the sound
		if(e.getSource()==jb1){			//when record/stop button pressed
			if(!recording){				//if the system is not recording, start recording the sound
				recording = true;
				jb1.setText("Stop");
				MultiThread.startRecord();
			}
			else{						//if the system is currently recording, stop recording the sound and process the data
				jb1.setText("Record");
				try {
					MultiThread.stopRecord();
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
				recording = false;
			}
		}
		
		if(e.getSource()==jb2){			//if the "play back" button clicked, play the recorded sound
			PlaySound.setPlayFile("RecordAudio.wav");
			try {
				MultiThread.play();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(e.getSource()==jb3){
			File f = new File("RecordAudio.wav");
			if(!f.exists()){
				JOptionPane.showMessageDialog(null, "No file to search.");
			}
			else{
				model.removeAllElements();
				try {
					Control.getList();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Control.search((String)jbox.getSelectedItem(), jtext.getText());
				library = Control.getLibrary();
				for(int i=0; i<library.size(); i++){
					model.addElement(library.get(i).name);		//put the file names in the list to display it
				}
			}
		}
		
		if(e.getSource()==jb4){
			File f = new File("RecordAudio.wav");
			f.delete();
			JOptionPane.showMessageDialog(null, "Recorded file deleted.");
			/*jtext.setText("");
			model.removeAllElements();
			jp.add(jsp, BorderLayout.CENTER);
			jbox.setSelectedItem("All");
			Control.search("All", "");
			Control.search((String)jbox.getSelectedItem(), jtext.getText());
			library = Control.getLibrary();
			for(int i=0; i<library.size(); i++){
				model.addElement(library.get(i).name);		//put the file names in the list to display it
			}*/
		}
		
		if(e.getSource()==jbox){		//if the category changed, clear jlist
			model.removeAllElements();
			jp.add(jsp, BorderLayout.CENTER);
			Control.search((String)jbox.getSelectedItem(), jtext.getText());
			library = Control.getLibrary();
			for(int i=0; i<library.size(); i++){
				model.addElement(library.get(i).name);		//put the file names in the list to display it
			}
		}
	}
	
	MouseListener mouseListener = new MouseAdapter() {		//if mouse clicked on a file, play it
	    public void mouseClicked(MouseEvent e) {
	    	int temp = jlist.getSelectedIndex();
	    	Control.playSound(library.get(temp));
	    }
	};
	
	KeyListener keylistener = new KeyAdapter() {
		public void keyReleased(KeyEvent e) {
			model.removeAllElements();
			Control.search((String)jbox.getSelectedItem(), jtext.getText());
			library = Control.getLibrary();
			for(int i=0; i<library.size(); i++){
				model.addElement(library.get(i).name);		//put the file names in the list to display it
			}
		}
	};
	
	
	public void run(){			//run is the initialize function of the user interface
		UserInterface a = new UserInterface();									//start a new UserInterface
		try {
			Control.getVariables();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Initialization finished.");
		a.setVisible(true);
		Control.search("All", "");
		library = Control.getLibrary();
		for(int i=0; i<library.size(); i++){
			model.addElement(library.get(i).name);		//put the file names in the list to display it
		}
		
		a.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	File audio = new File("RecordAudio.wav");
            	audio.delete();
                e.getWindow().dispose();
            }
        });
	}
}