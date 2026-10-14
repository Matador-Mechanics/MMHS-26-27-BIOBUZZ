package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.hardware.motors.RevRoboticsUltraPlanetaryHdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.psilynx.psikit.core.Logger;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;

@PsiKitAutoLog(rlogPort = 5800, rlogFolder = "/sdcard/FIRST/PsiKit/")
//@Disabled
@TeleOp(name = "LauncherLogger", group = "Testing")
public class LauncherDecelerationTest extends OpMode {
	DcMotorEx launcherMotor;

	@Override
	public void init() {
		resetRuntime();
		Logger.recordMetadata("Phase", "Init");

		launcherMotor = hardwareMap.get(DcMotorEx.class, "launcherMotor");
		launcherMotor.setMotorType(MotorConfigurationType.getMotorType(RevRoboticsUltraPlanetaryHdHexMotor.class));
		launcherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

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
		launcherMotor.setVelocity(3000.0 * 28 / 60);

		Logger.recordMetadata("Phase", "Loop");
	}

	@Override
	public void loop() {
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
