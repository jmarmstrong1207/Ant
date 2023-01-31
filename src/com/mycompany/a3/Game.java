package com.mycompany.a3;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.codename1.charts.util.ColorUtil;


/*
 * This class currently represents as the game controller. All commands manipulating
 * the game world shall be in this class
 */
public class Game extends Form {

	private GameWorld gameWorld;
	
	private MapView mapView;
	private ScoreView scoreView;
	
	// tickRate of the timer in ms
	public static final int tickRate = 20;

	public Game() {
		mapView = new MapView(this);
		scoreView = new ScoreView();
		
		// Change layout to Border Layout
		this.setLayout(new BorderLayout());
		
		Container westC = new Container();
		westC.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));

		Container eastC = new Container();
		eastC.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));

		Container southC = new Container();
		southC.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));

		AccelerateCommand accelerate = new AccelerateCommand(this);
		BrakeCommand brake = new BrakeCommand(this);
		LeftTurnCommand leftTurn = new LeftTurnCommand(this);
		RightTurnCommand rightTurn = new RightTurnCommand(this);
		PlayPauseCommand play = new PlayPauseCommand(this);
		PositionCommand position = new PositionCommand(this);

		// Add buttons to south container
		CustomButton bPlay = new CustomButton();
		CustomButton bPosition = new CustomButton();

		bPlay.setCommand(play);
		bPosition.setCommand(position);

		southC.add(bPlay);
		southC.add(bPosition);
		///////////////////////////////////////
		
		// Add buttons to east container
		CustomButton bBrake = new CustomButton();

		CustomButton bRight = new CustomButton();
		
		bBrake.setCommand(brake);
		bRight.setCommand(rightTurn);
		
		eastC.add(bBrake);
		eastC.add(bRight);
		///////////////////////////////////////

		// Add buttons to west container
		CustomButton bAccelerate = new CustomButton();

		CustomButton bLeft = new CustomButton();
		
		bAccelerate.setCommand(accelerate);
		bLeft.setCommand(leftTurn);
		
		westC.add(bAccelerate);
		westC.add(bLeft);
		///////////////////////////////////////

		// Add the necessary containers
		this.add(BorderLayout.WEST, westC);
		this.add(BorderLayout.EAST, eastC);
		this.add(BorderLayout.NORTH, scoreView);
		this.add(BorderLayout.SOUTH, southC);
		this.add(BorderLayout.CENTER, mapView);


		// Add keybinds
		this.addKeyListener('a', accelerate);
		this.addKeyListener('b', brake);
		this.addKeyListener('l', leftTurn);
		this.addKeyListener('r', rightTurn);
		
		// Add toolbar
		Toolbar tb = new Toolbar();
		this.setToolbar(tb);
		
		// Add toolbar buttons
		SoundCommand sound = new SoundCommand(this);
		ExitCommand exit = new ExitCommand();
		AboutCommand about = new AboutCommand();
		HelpCommand help = new HelpCommand();

		CheckBox cSound = new CheckBox();
		cSound.getAllStyles().setFgColor(ColorUtil.WHITE);
		cSound.setCommand(sound);

		tb.addComponentToLeftSideMenu(cSound);
		tb.addCommandToLeftSideMenu(accelerate);
		tb.addCommandToLeftSideMenu(about);
		tb.addCommandToRightBar(help);
		tb.addCommandToLeftSideMenu(exit);
		///////////////////////////////////////
		
		show();

		gameWorld = new GameWorld(new int[] {mapView.getWidth(), mapView.getHeight()});
		gameWorld.init();

		gameWorld.addObserver(mapView);
		gameWorld.addObserver(scoreView);
		
		// Create the sounds
		gameWorld.createSounds();
		revalidate();

		// Add timer that runs clockUpdate() in gameworld
		UITimer timer = new UITimer(gameWorld);
		timer.schedule(tickRate, true, this);
	}

	public GameWorld getGameWorld() {
		return gameWorld;
	}
}
