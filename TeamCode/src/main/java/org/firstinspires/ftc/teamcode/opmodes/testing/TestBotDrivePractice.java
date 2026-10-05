package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class TestBotDrivePractice extends OpMode {
	MotorEx fL, fR, bL, bR, intake;
	MecanumDrive mecanumDrive;
	GamepadEx GP1;
	GoBildaPinpointDriver pinpoint;

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

		pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
	}

	@Override
	public void loop() {
		GP1 = new GamepadEx(gamepad1);
		mecanumDrive.driveFieldCentric(GP1.getLeftX(), GP1.getLeftY(), GP1.getRightX(), pinpoint.getHeading(AngleUnit.DEGREES), true);
		if (GP1.isDown(GamepadKeys.Button.RIGHT_BUMPER)) {
			intake.set(1);
		} else if (GP1.gamepad.right_trigger_pressed) {
			intake.set(-.5);
		} else {
			intake.set(0);
		}
	}
}
