package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.gamepad.SlewRateLimiter;
import com.seattlesolvers.solverslib.hardware.RevIMU;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

@TeleOp(name = "TestBotPractice", group = "Testing")

public class TestBotDrivePractice extends OpMode {
	MotorEx fL, fR, bL, bR, intake;
	MecanumDrive mecanumDrive;
	GamepadEx GP1;
	RevIMU IMU;
	SlewRateLimiter IntakeSlew = new SlewRateLimiter(0.5);

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

		IMU = new RevIMU(hardwareMap);
		IMU.init();
	}

	@Override
	public void loop() {
		GP1 = new GamepadEx(gamepad1);
		double rTrig = IntakeSlew.calculate(GP1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
		mecanumDrive.driveRobotCentric(GP1.getLeftX(), GP1.getLeftY(), GP1.getRightX(), true);
		if (rTrig > 0.3) {
			intake.set(rTrig);
		} else if (GP1.isDown(GamepadKeys.Button.RIGHT_BUMPER)) {
			intake.set(-.5);
		} else {
			intake.set(0);
		}
	}
}
