package Exercise1;

import java.awt.FlowLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import java.io.IOException;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;

import javax.sound.sampled.Line;


import javax.sound.sampled.LineUnavailableException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class audio extends JApplet implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton play;
	JButton repeat;
	JButton mute;
	JButton stop;
	JButton pause;
	
	BooleanControl bc;
	Clip clip;
	File soundFile;
	
	boolean played;
	boolean isPaused;
	boolean isMuted;
	boolean isRepCont;
	
	public audio() throws LineUnavailableException {
		
		getContentPane().setLayout(new FlowLayout());
		play = new JButton();
		play.setText("Play");
		this.getContentPane().add(play);
		 play.addMouseListener(this);

		
		
		repeat = new JButton();
		repeat.setText("Repeat");
		this.getContentPane().add(repeat);
		repeat.setEnabled(false);
		repeat.addMouseListener(this);
		
		mute = new JButton();
		mute.setText("Mute");
		this.getContentPane().add(mute);
		mute.setEnabled(false);
		mute.addMouseListener(this);
		
		stop = new JButton();
		stop.setText("Stop");
		this.getContentPane().add(stop);
		stop.setEnabled(false);
		stop.addMouseListener(this);		
		
		pause = new JButton();
		pause.setText("Pause");
		this.getContentPane().add(pause);
		pause.setEnabled(false);
		pause.addMouseListener(this);
		
		JButton open  = new JButton();
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("WAVE files", new String[] {"WAV"});
		fc.setFileFilter(filter);
		fc.addChoosableFileFilter(filter);
		fc.setCurrentDirectory(new java.io.File("C:"));
		fc.setDialogTitle("choose audio file");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		try{
		if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
			soundFile = fc.getSelectedFile();
		}
			}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		
		Line.Info linfo = new Line.Info(Clip.class);
	    Line line = AudioSystem.getLine(linfo);
	    clip = (Clip) line;

		
		
		
		this.setBounds(100, 100,600, 200);
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
/*	public static void main(String[]args) throws LineUnavailableException {
	
		audio a = new audio();
		
		a.setVisible(true);
		
		
	}*/
	public void Play() throws Exception {
		
		if (played == true) {
			clip.close();
		}
		 AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
		    clip.open(ais);
		    clip.start();
		    play.setEnabled(false);
		    
		    stop.setEnabled(true);
		    mute.setEnabled(true);
		    pause.setEnabled(true);
	}
	public void repeat() throws Exception{
		if (played == true) {
			clip.close();
		}
		 AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
		    clip.open(ais);
		    clip.start();
		    play.setEnabled(false);
		    
		    stop.setEnabled(true);
		    mute.setEnabled(true);
		    pause.setEnabled(true);
	}
	
	public void repeatCont() {
		int loops = clip.LOOP_CONTINUOUSLY;
		clip.loop(loops);
		isRepCont = true;
		
		repeat.setEnabled(false);
		mute.setEnabled(true);
		pause.setEnabled(true);
		stop.setEnabled(true);
		repeat.setText("Undo Repeat");
	}
	
	public void stopRep() {
		clip.loop(0);
		isRepCont = false;
		repeat.setText("Repeat");
	}
	
	
	public void Stop() throws UnsupportedAudioFileException, IOException {
	
		clip.close();
		repeat.setEnabled(false);
		play.setEnabled(true);
		played= false;
		stop.setEnabled(false);
		pause.setEnabled(false);
		mute.setEnabled(false);
		
	}
	public void Pause() throws UnsupportedAudioFileException, IOException {
		
		clip.stop();
		isPaused = true;
		
		play.setEnabled(true);
		stop.setEnabled(false);
		pause.setEnabled(false);
		mute.setEnabled(false);
		
		
	}
	public void Resume() {
	
		clip.start();
		isPaused = false;
		play.setEnabled(false);
		repeat.setEnabled(true);
		pause.setEnabled(true);
		stop.setEnabled(true);
		mute.setEnabled(true);
	}
	public void Mute() {
		bc = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
		if (bc != null) {
		    bc.setValue(true); 
		    play.setEnabled(true);
		    repeat.setEnabled(true);
		    isMuted = true;
		}
		
	}
	public void unMute() throws Exception {
		if (!clip.isRunning()) {
			clip.close();
			bc.setValue(false);
			Play();
		
		}
		bc = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
		if (clip.isRunning() && bc != null) {		
		bc.setValue(false);	
		
		}
		isMuted = false;
		play.setEnabled(false);
		pause.setEnabled(true);
		mute.setEnabled(true);
		stop.setEnabled(true);
	}
	
					 
	
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == play && played == false && !isPaused) {
			try {
				Play();
				
				} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			played = true;
		repeat.setEnabled(true);
			
		}
		else {
			if (e.getSource() == play && played == true && isPaused && clip.isOpen()) {
				Resume();
				
				
		}
			if (e.getSource() == play && played == true && isPaused && !clip.isOpen()){
				try {
					Play();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				if (e.getSource() == play && isMuted ) {
				try {
					unMute();
					
					
					
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
		}
		if (e.getSource() == repeat && played == true && !isMuted && clip.isRunning() && isRepCont == false) {
		try {
			repeatCont();
			repeat.setEnabled(true);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		}
		else {
			if (e.getSource() == repeat && played == true && !isMuted && clip.isRunning() && isRepCont == true) {
			stopRep();
		}
		}
			if (e.getSource() == repeat && played == true && isMuted) {
				try {
					unMute();
					repeat();
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				
			}
		
		if (e.getSource() == repeat && !clip.isRunning()) {
			try {
				repeat();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource() == stop) {
			try {
				Stop();
			} catch (UnsupportedAudioFileException e1) {
				
				e1.printStackTrace();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		if (e.getSource() == pause) {
			try {
				Pause();
			} catch (UnsupportedAudioFileException e1) {
				
				e1.printStackTrace();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		if (e.getSource() == mute) {
			Mute();
		}
		
	}
	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
