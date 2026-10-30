package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.hardware.motors.RevRoboticsUltraPlanetaryHdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.psilynx.psikit.core.Logger;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;

@PsiKitAutoLog(rlogPort = 5800, rlogFolder = "/sdcard/FIRST/PsiKit/")
//@Disabled
@TeleOp(name = "LauncherLogger", group = "Testing")
public class LauncherDecelerationTest extends OpMode {
	DcMotorEx NectarMotor, PollenMotor;
	double dpad = 2750;
	Gamepad gp1 = null;
	VoltageSensor voltageSensor;

	@Override
	public void init() {
		resetRuntime();
		Logger.recordMetadata("Phase", "Init");

		NectarMotor = hardwareMap.get(DcMotorEx.class, "NectarMotor");
		NectarMotor.setMotorType(MotorConfigurationType.getMotorType(RevRoboticsUltraPlanetaryHdHexMotor.class));
		NectarMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		NectarMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		NectarMotor.setDirection(DcMotorSimple.Direction.FORWARD);

		PollenMotor = hardwareMap.get(DcMotorEx.class, "PollenMotor");
		PollenMotor.setMotorType(MotorConfigurationType.getMotorType(RevRoboticsUltraPlanetaryHdHexMotor.class));
		PollenMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		PollenMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		PollenMotor.setDirection(DcMotorSimple.Direction.REVERSE);

		voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

		Logger.recordMetadata("Phase", "Init_Loop");
	}

	@Override
	public void init_loop() {
		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Nectar RPM", getNectarRPM());
		Logger.recordOutput("Pollen RPM", getPollenRPM());
		Logger.recordOutput("Voltage", voltageSensor.getVoltage());
	}

	@Override
	public void start() {
		//NectarMotor.setPower(1);
		gp1 = gamepad1;
		Logger.recordMetadata("Phase", "Loop");
	}

	@Override
	public void loop() {
		gp1 = gamepad1;
		if (gp1.dpadUpWasPressed()) {
			dpad += 50;
		}
		if (gp1.dpadDownWasPressed()) {
			dpad -= 50;
		}
		double velocity = dpad * 28 / 60;
		NectarMotor.setVelocity(velocity);
		PollenMotor.setVelocity(velocity);
		telemetry.addData("RPM", dpad);

		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Nectar RPM", getNectarRPM());
		Logger.recordOutput("Pollen RPM", getPollenRPM());
		Logger.recordOutput("Voltage", voltageSensor.getVoltage());

		telemetry.update();
	}

	@Override
	public void stop() {
		Logger.recordMetadata("Phase", "Stop");
		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Nectar RPM", getNectarRPM());
		Logger.recordOutput("Pollen RPM", getPollenRPM());
		Logger.recordOutput("Voltage", voltageSensor.getVoltage());
		NectarMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		PollenMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		Logger.recordMetadata("Phase", "Spool down");
		NectarMotor.setPower(0);
		PollenMotor.setPower(0);
		Logger.recordMetadata("Phase", "End of Execution");
	}

	private double getNectarRPM() {
		return NectarMotor.getVelocity() / 28 * 60;
	}

	private double getPollenRPM() {
		return PollenMotor.getVelocity() / 28 * 60;
	}
}
