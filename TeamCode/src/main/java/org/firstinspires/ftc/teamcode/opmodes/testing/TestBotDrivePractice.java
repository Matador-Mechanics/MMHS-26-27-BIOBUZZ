package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.gamepad.SlewRateLimiter;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Disabled
@TeleOp(name = "TestBotPractice", group = "Testing")
public class TestBotDrivePractice extends OpMode {
	MotorEx fL, fR, bL, bR, intake;
	MecanumDrive mecanumDrive;
	GamepadEx GP1;
	IMU imu;
	IMU.Parameters imuParams = new IMU.Parameters(new RevHubOrientationOnRobot(
			RevHubOrientationOnRobot.LogoFacingDirection.UP,
			RevHubOrientationOnRobot.UsbFacingDirection.LEFT));;
	SlewRateLimiter IntakeSlew = new SlewRateLimiter(0.5);
	boolean robotDrive = true;

	@Override
	public void init() {
		fL = new MotorEx(hardwareMap, "frontLeft");
		fL.motor.setDirection(DcMotorSimple.Direction.FORWARD);
		fR = new MotorEx(hardwareMap, "frontRight");
		fL.motor.setDirection(DcMotorSimple.Direction.FORWARD);
		bL = new MotorEx(hardwareMap, "backLeft");
		fL.motor.setDirection(DcMotorSimple.Direction.FORWARD);
		bR = new MotorEx(hardwareMap, "backRight");
		fL.motor.setDirection(DcMotorSimple.Direction.FORWARD);

		mecanumDrive = new MecanumDrive(fL, fR, bL, bR);

		intake = new MotorEx(hardwareMap, "intake");
		intake.motor.setDirection(DcMotorSimple.Direction.REVERSE);

		imu.initialize(imuParams);
		imu.resetYaw();
	}

	@Override
	public void loop() {
		GP1 = new GamepadEx(gamepad1);
		double rTrig = IntakeSlew.calculate(GP1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
		if (GP1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
			robotDrive = !robotDrive;
		}

		if (robotDrive) {
			mecanumDrive.driveRobotCentric(GP1.getRightX(), -GP1.getRightY(), GP1.getLeftX(), true);
		} else {
			mecanumDrive.driveFieldCentric(GP1.getRightX(), -GP1.getRightY(), GP1.getLeftX(), imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES), true);
		}

		if (GP1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.3) {
			intake.set(GP1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
		} else if (GP1.isDown(GamepadKeys.Button.RIGHT_BUMPER)) {
			intake.set(-.5);
		} else {
			intake.set(0);
		}
	}
}
