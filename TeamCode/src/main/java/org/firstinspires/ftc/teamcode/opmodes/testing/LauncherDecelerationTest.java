package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.hardware.motors.RevRoboticsUltraPlanetaryHdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.psilynx.psikit.core.Logger;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;

@PsiKitAutoLog(rlogPort = 5800, rlogFolder = "/sdcard/FIRST/PsiKit/")
//@Disabled
@TeleOp(name = "LauncherLogger", group = "Testing")
public class LauncherDecelerationTest extends OpMode {
	DcMotorEx launcherMotor;
	double dpad = 2750;
	Gamepad gp1;
	Gamepad oldgp1;

	@Override
	public void init() {
		resetRuntime();
		Logger.recordMetadata("Phase", "Init");

		launcherMotor = hardwareMap.get(DcMotorEx.class, "launcherMotor");
		launcherMotor.setMotorType(MotorConfigurationType.getMotorType(RevRoboticsUltraPlanetaryHdHexMotor.class));
		launcherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		launcherMotor.setDirection(DcMotorSimple.Direction.REVERSE);

		Logger.recordMetadata("Phase", "Init_Loop");
	}
	@Override
	public void init_loop() {
		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Launcher RPM", getLauncherRPM());
	}

	@Override
	public void start() {
		//launcherMotor.setPower(1);
		gp1 = gamepad1;
		Logger.recordMetadata("Phase", "Loop");
	}

	@Override
	public void loop() {
		 oldgp1 = gp1;
		gp1 = gamepad1;
		if (!(gp1.dpad_up == oldgp1.dpad_up) || !(gp1.dpad_down == oldgp1.dpad_down)) {
			if (gp1.dpad_up) dpad += 50;
			if (gp1.dpad_down) dpad -= 50;
		}
		launcherMotor.setVelocity(dpad * 28 / 60);
		telemetry.addData("RPM", dpad);

		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Launcher RPM", getLauncherRPM());
	}

	@Override
	public void stop() {
		Logger.recordMetadata("Phase", "Stop");
		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Launcher RPM", getLauncherRPM());
		launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		launcherMotor.setPower(0);
		Logger.recordMetadata("Phase", "Spool down");
	}

	private double getLauncherRPM() {
		return launcherMotor.getVelocity() / 28 * 60;
	}
}
