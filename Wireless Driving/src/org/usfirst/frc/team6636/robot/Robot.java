package org.usfirst.frc.team6636.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.*;
import edu.wpi.cscore.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	Spark leftMotor = new Spark(0);
	Spark rightMotor = new Spark(1);
	Timer timer = new Timer();	
	Joystick stick = new Joystick(0);
	
	private CvSource cameraStream = CameraServer.getInstance().putVideo("Vision", 320, 240);
	
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		CameraServer.getInstance().startAutomaticCapture();
		
		vision.start();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		 if (timer.get() <= 3.10) {
			 leftMotor.set(.3);
			 rightMotor.set(.3);
		 }
		 else {
			 leftMotor.set(0);
			 rightMotor.set(0);
		 }
	
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		 double wheelRotation = stick.getRawAxis(2);
		 double forwardBackward = stick.getRawAxis(1);
		 
		 if (forwardBackward <= .1 && forwardBackward >= -.1){
			 rightMotor.set(wheelRotation);
			 leftMotor.set(wheelRotation);
		 }
		 
		 if (wheelRotation <= .1 && wheelRotation >= -.1){
			 rightMotor.set(forwardBackward);
			 leftMotor.set(-forwardBackward);
		 }
		 System.out.print(wheelRotation);

		 
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		 timer.reset();
		 if (timer.get() <= 1) {
			 leftMotor.set(-.6);
			 rightMotor.set(.6);
		 }
		 else {
			 leftMotor.set(0);
			 rightMotor.set(0);
		 }
	}
}
