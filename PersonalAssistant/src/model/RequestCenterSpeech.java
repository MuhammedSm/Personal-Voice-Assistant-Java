package model;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import tts.TextToSpeech;

public class RequestCenterSpeech  {
	private static final String Result2 = null;
	TextToSpeech textToSpeech= new TextToSpeech();
	// Necessary
	private LiveSpeechRecognizer recognizer;
	
	// Logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * This String contains the Result that is coming back from SpeechRecognizer
	 */
	private String Result;
	
	//-----------------Lock Variables-----------------------------
	
	/**
	 * This variable is used to ignore the results of speech recognition cause actually it can't be stopped...
	 * 
	 * <br>
	 * Check this link for more information: <a href=
	 * "https://sourceforge.net/p/cmusphinx/discussion/sphinx4/thread/3875fc39/">https://sourceforge.net/p/cmusphinx/discussion/sphinx4/thread/3875fc39/</a>
	 */
	private boolean ignoreSpeechRecognitionResults = false;
	
	/**
	 * Checks if the speech recognise is already running
	 */
	private boolean speechRecognizerThreadRunning = false;
	
	/**
	 * Checks if the resources Thread is already running
	 */
	private boolean resourcesThreadRunning;
	
	//---
	
	/**
	 * This executor service is used in order the playerState events to be executed in an order
	 */
	private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);
	
	//------------------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public  RequestCenterSpeech() {
		
		// Loading Message
		logger.log(Level.INFO, "Loading Speech Recognizer...\n");
		
		// Configuration
		Configuration configuration = new Configuration();
		
		// Load model from the jar
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		
		//====================================================================================
		//=====================READ THIS!!!===============================================
		//Uncomment this line of code if you want the recognizer to recognize every word of the language 
		//you are using , here it is English for example	
		//====================================================================================
//		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		
		//====================================================================================
		//=====================READ THIS!!!===============================================
		//If you don't want to use a grammar file comment below 3 lines and uncomment the above line for language model	
		//====================================================================================
		
		// Grammar
		configuration.setGrammarPath("resource:/grammars");
		configuration.setGrammarName("grammar");
		configuration.setUseGrammar(true);
		
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		
		// Start recognition process pruning previously cached data.
		// recognizer.startRecognition(true);
		
		//Check if needed resources are available
		startResourcesThread();
		//Start speech recognition thread
		startSpeechRecognition();
	}
	
	//-----------------------------------------------------------------------------------------------
	
	/**
	 * Starts the Speech Recognition Thread
	 */
	public synchronized void startSpeechRecognition() {
		
		Assistant jarvis = new Assistant("Jarvis","1",25);
		if (speechRecognizerThreadRunning)
			logger.log(Level.INFO, "Speech Recognition Thread already running...\n");
		else
			//Submit to ExecutorService
			eventsExecutorService.submit(() -> {
				
				//locks
				speechRecognizerThreadRunning = true;
				ignoreSpeechRecognitionResults = false;
				
				//Start Recognition
				recognizer.startRecognition(true);
				
				//Information			
				logger.log(Level.INFO, "You can start to speak...\n");
				
				try {
					while (speechRecognizerThreadRunning) {
						/*
						 * This method will return when the end of speech is reached. Note that the end pointer will determine the end of speech.
						 */
						SpeechResult speechResult = recognizer.getResult();
						
						//Check if we ignore the speech recognition results
						if (!ignoreSpeechRecognitionResults) {
							
							//Check the result
							if (speechResult == null)
								logger.log(Level.INFO, "I can't understand what you said.\n");
							else {
								
								//Get the hypothesis
								Result = speechResult.getHypothesis();
								
								//You said?
								System.out.println("You said: [" + Result + "]\n");
								
								//Call the appropriate method 
							makeDecision(Result, speechResult.getWords());
								
							}
						} else
							logger.log(Level.INFO, "Ingoring Speech Recognition Results...");
						
					}
				} catch (Exception ex) {
					logger.log(Level.WARNING, null, ex);
					speechRecognizerThreadRunning = false;
				}
				
				logger.log(Level.INFO, "SpeechThread has exited...");
				
			});
	}
	
	/**
	 * Stops ignoring the results of SpeechRecognition
	 */
	public synchronized void stopIgnoreSpeechRecognitionResults() {
		
		//Stop ignoring speech recognition results
		ignoreSpeechRecognitionResults = false;
	}
	
	/**
	 * Ignores the results of SpeechRecognition
	 */
	public synchronized void ignoreSpeechRecognitionResults() {
		
		//Instead of stopping the speech recognition we are ignoring it's results
		ignoreSpeechRecognitionResults = true;
		
	}
	
	//-----------------------------------------------------------------------------------------------
	
	/**
	 * Starting a Thread that checks if the resources needed to the SpeechRecognition library are available
	 */
	public void startResourcesThread() {
		
		//Check lock
		if (resourcesThreadRunning)
			logger.log(Level.INFO, "Resources Thread already running...\n");
		else
			//Submit to ExecutorService
			eventsExecutorService.submit(() -> {
				try {
					
					//Lock
					resourcesThreadRunning = true;
					
					// Detect if the microphone is available
					while (true) {
						
						//Is the Microphone Available
						if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
							logger.log(Level.INFO, "Microphone is not available.\n");
						
						// Sleep some period
						Thread.sleep(350);
					}
					
				} catch (InterruptedException ex) {
					logger.log(Level.WARNING, null, ex);
					resourcesThreadRunning = false;
				}
			});
	}
	
	/**
	 * Takes a decision based on the given result
	 * 
	 * @param speechWords
	 */
//	public String Data() {
//		String Res = " ";
//	
//		try {
//			while (speechRecognizerThreadRunning) {
//				/*
//				 * This method will return when the end of speech is reached. Note that the end pointer will determine the end of speech.
//				 */
//				SpeechResult speechResult = recognizer.getResult();
//				
//				//Check if we ignore the speech recognition results
//				if (!ignoreSpeechRecognitionResults) {
//					
//					//Check the result
//					if (speechResult == null)
//						logger.log(Level.INFO, "I can't understand what you said.\n");
//					else {
//						
//						//Get the hypothesis
//						Res = speechResult.getHypothesis();
//						
//						//You said?
//						System.out.println("You said: [" + Res + "]\n");
//						
//						//Call the appropriate method 
//					
//						return Res;
//					}
//				} else
//					logger.log(Level.INFO, "Ingoring Speech Recognition Results...");
//				
//			}
//		} catch (Exception ex) {
//			logger.log(Level.WARNING, null, ex);
//			speechRecognizerThreadRunning = false;
//		}
//		return Res;
//		
//	}
//	
	public void makeDecision(String speech , List<WordResult> speechWords) {
		
		Assistant jarvis = new Assistant("Jarvis","1",25);
		String word =jarvis.getSchedule();
		String time=jarvis.GetTime();
		String date=jarvis.GetDate();
//		String hw=Data();
//		
//		if(speech.equals("set homework")) {
//			Data();
//			jarvis.setHW(hw,2);
//			
//		}
	    
		if(speech.equals("hi jarvis")) {
			
			textToSpeech.speak("hi muhammed", 1.5f, false, ignoreSpeechRecognitionResults);
			
		}
		
        if(speech.equals("jarvis")) {
			
			textToSpeech.speak("at your service", 1.5f, false, ignoreSpeechRecognitionResults);
			
		}  
        if(speech.equals("what should a human never do")) {
			
			textToSpeech.speak("Spray pif puff in the fridge you idiot", 1.5f, false, ignoreSpeechRecognitionResults);
			
		}
       if(speech.equals("show me my homework for monday")) {
			jarvis.ShowHW(2);
			System.exit(0);
		}
       if(speech.equals("show me my homework for tuesday")) {
			jarvis.ShowHW(3);
			System.exit(0);
		}
       if(speech.equals("show me my homework for wednesday")) {
    	   jarvis.ShowHW(4);  
    	   System.exit(0);
		
			
		}
       if(speech.equals("show me my homework for thursday")) {
    	   jarvis.ShowHW(5);
    	   System.exit(0);
		}
       if(speech.equals("show me my homework for this week")) {
    	   jarvis.ShowAllHW();
    	   System.exit(0);
		}
       if(speech.equals("delete the homework for this week")) {
    	   jarvis.ShowAllHW();
    	   System.exit(0);
		}
       
       
       
       
       
       
       
       
		if(speech.equals("schedule")) {
		
			textToSpeech.speak(word, 1.5f, false, ignoreSpeechRecognitionResults);
			
		}
		if(speech.equals("get time")) {
		
			textToSpeech.speak(time, 1.5f, false, ignoreSpeechRecognitionResults);
			
		}
		if(speech.equals("get date")) {
		
			textToSpeech.speak(date, 1.5f, false, ignoreSpeechRecognitionResults);
			
		}}
		
		
	
	
	public boolean getIgnoreSpeechRecognitionResults() {
		return ignoreSpeechRecognitionResults;
	}
	
	public boolean getSpeechRecognizerThreadRunning() {
		return speechRecognizerThreadRunning;
	}
	
	/**
	 * Main Method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new RequestCenterSpeech();
	}
}
