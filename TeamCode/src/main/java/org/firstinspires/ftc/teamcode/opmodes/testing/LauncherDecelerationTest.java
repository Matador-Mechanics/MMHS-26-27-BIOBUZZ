package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.hardware.motors.RevRoboticsUltraPlanetaryHdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.psilynx.psikit.core.Logger;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;

@PsiKitAutoLog
@Disabled
@TeleOp(name = "LauncherLogger", group = "Testing")
public class LauncherDecelerationTest extends OpMode {
	DcMotorEx launcherMotor;

	@Override
	public void init() {
		Logger.recordMetadata("Phase", "Init");

		launcherMotor = hardwareMap.get(DcMotorEx.class, "launcherMotor");
		launcherMotor.setMotorType(MotorConfigurationType.getMotorType(RevRoboticsUltraPlanetaryHdHexMotor.class));
		launcherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		launcherMotor.setPower(1);

		Logger.recordMetadata("Phase", "Init_Loop");
	}
	@Override
	public void init_loop() {
		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Velocity ticks/sec", launcherMotor.getVelocity());
	}

	@Override
	public void start() {
		Logger.recordMetadata("Phase", "Loop");
	}

	@Override
	public void loop() {
		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Velocity ticks/sec", launcherMotor.getVelocity());
	}

	@Override
	public void stop() {
		Logger.recordMetadata("Phase", "Stop");
		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Velocity ticks/sec", launcherMotor.getVelocity());
		launcherMotor.setPower(0);

	}
}
