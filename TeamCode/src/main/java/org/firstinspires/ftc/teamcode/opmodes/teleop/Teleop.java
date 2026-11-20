package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.util.MathUtils;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.commandBase.subsystems.Drive;
import org.firstinspires.ftc.teamcode.commandBase.subsystems.Vision;
import org.firstinspires.ftc.teamcode.globals.constants;
import org.psilynx.psikit.core.Logger;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;

@PsiKitAutoLog(rlogPort = constants.rlogPort, rlogFolder = "/sdcard/FIRST/PsiKit/")
//@Disabled
@TeleOp(name = "TeleOp", group = "TeleOp")
public class Teleop extends OpMode {

	MotorEx fL, fR, bL, bR, IntakeMotor, PollenMotor, NectarMotor;
	Servo PollenDoor, NectarDoor;
	MecanumDrive mecanumDrive;
	GamepadEx GP1;
	VoltageSensor voltageSensor;
	GamepadEx GP1Old;
	final double deadzone = 0.325;

	@Override
	public void init() {
		Logger.start();
		resetRuntime();
		//Drive drive = new Drive(this);
		//Vision vision = new Vision(this);
		fL = new MotorEx(hardwareMap, "frontLeft");
		fL.motor.setDirection(DcMotorSimple.Direction.REVERSE);
		fR = new MotorEx(hardwareMap, "frontRight");
		fR.motor.setDirection(DcMotorSimple.Direction.FORWARD);
		bL = new MotorEx(hardwareMap, "backLeft");
		bL.motor.setDirection(DcMotorSimple.Direction.REVERSE);
		bR = new MotorEx(hardwareMap, "backRight");
		bR.motor.setDirection(DcMotorSimple.Direction.REVERSE);

		mecanumDrive = new MecanumDrive(fL, fR, bL, bR);

		IntakeMotor = new MotorEx(hardwareMap, constants.hardwareStrings.get("intakeMotor"));
		IntakeMotor.setRunMode(Motor.RunMode.VelocityControl);
		IntakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
		IntakeMotor.setInverted(false);

		PollenMotor = new MotorEx(hardwareMap, constants.hardwareStrings.get("pollenMotor"));
		PollenMotor.setRunMode(Motor.RunMode.VelocityControl);
		PollenMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
		PollenMotor.setInverted(false);

		NectarMotor = new MotorEx(hardwareMap, constants.hardwareStrings.get("nectarMotor"));
		NectarMotor.setRunMode(Motor.RunMode.VelocityControl);
		NectarMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
		NectarMotor.setInverted(true);

		PollenDoor =  hardwareMap.get(Servo.class, constants.hardwareStrings.get("pollenDoor"));

		NectarDoor = hardwareMap.get(Servo.class, constants.hardwareStrings.get("nectarDoor"));

		voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

		GP1 = new GamepadEx(gamepad1);
	}

	@Override
	public void init_loop() {
		logger();
	}

	@Override
	public void loop() {
		logger();
		GP1Old = GP1;
		GP1 = new GamepadEx(gamepad1);
		if (GP1 != GP1Old) {
			drive();
		}
		if (GP1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1) {
			IntakeMotor.setVelocity(ticksToVelocity(2000 * MathUtils.clamp(GP1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER), 0.1, 1), 5));
		} else {
			IntakeMotor.stopMotor();
		}
		if (GP1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1) {
			PollenMotor.setVelocity(ticksToVelocity(2750, 1));
			NectarMotor.setVelocity(ticksToVelocity(2750, 1));
		} else {
			PollenMotor.stopMotor();
			NectarMotor.stopMotor();
		}
		if (GP1.getButton(GamepadKeys.Button.DPAD_LEFT)) {
			NectarDoor.setPosition(1);
		} else {
			NectarDoor.setPosition(0);
		}
		if (GP1.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
			PollenDoor.setPosition(0);
		} else {
			PollenDoor.setPosition(1);
		}
	}

	public double motorVoltage(double volts) {
		return volts / voltageSensor.getVoltage();
	}

	public void drive() {
//		if (GP1.getRightX() != 0) {
//			if (Math.signum(GP1.getRightX()) != Math.signum(GP1Old.getRightX())) {
//				mecanumDrive.stop();
//			}
//		} else if (GP1.getRightY() != 0) {
//			if (Math.signum(GP1.getRightY()) != Math.signum(GP1Old.getRightY())) {
//				mecanumDrive.stop();
//			}
//		} else if (GP1.getLeftX() != 0) {
//			if (Math.signum(GP1.getLeftX()) != Math.signum(GP1Old.getLeftX())) {
//				mecanumDrive.stop();
//			}
//		} else {
			double rX = deadzone(GP1.getRightX());
			double rY = deadzone(GP1.getRightY());
			double lX = deadzone(GP1.getLeftX());
			mecanumDrive.driveRobotCentric(rX, -rY, lX, false);
//		}
	}

	public double ticksToVelocity(double RPM, double gearRatio) {
		return RPM * 28 / 60 * gearRatio;
	}

	public double deadzone(double input) {
		if (Math.abs(input) <= deadzone){
			return 0;
		} else {
			return input;
		}
	}

	public void logger() {
		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Voltage", voltageSensor.getVoltage());
		Logger.recordOutput("Intake RPM", IntakeMotor.getVelocity() / (28 * 5) * 60);
		Logger.recordOutput("Pollen RPM", PollenMotor.getVelocity() / 28 * 60);
		Logger.recordOutput("Nectar RPM", NectarMotor.getVelocity() / 28 * 60);
		Logger.recordOutput("Front Left Current", fL.getCurrent(CurrentUnit.AMPS));
		Logger.recordOutput("Front Right Current", fR.getCurrent(CurrentUnit.AMPS));
		Logger.recordOutput("Back Left Current", bL.getCurrent(CurrentUnit.AMPS));
		Logger.recordOutput("Back Right Current", bR.getCurrent(CurrentUnit.AMPS));
	}
}
