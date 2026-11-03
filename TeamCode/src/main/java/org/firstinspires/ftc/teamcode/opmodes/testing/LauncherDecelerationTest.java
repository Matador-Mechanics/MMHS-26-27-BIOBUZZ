package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.hardware.motors.RevRoboticsUltraPlanetaryHdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.psilynx.psikit.core.Logger;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;

@PsiKitAutoLog(rlogPort = 5800, rlogFolder = "/sdcard/FIRST/PsiKit/")
@Disabled
@TeleOp(name = "LauncherLogger", group = "Testing")
public class LauncherDecelerationTest extends OpMode {
	MotorEx NectarMotor, PollenMotor;
	double dpad = 2750;
	Gamepad gp1 = null;
	VoltageSensor voltageSensor;

	@Override
	public void init() {
		resetRuntime();
		Logger.recordMetadata("Phase", "Init");

		NectarMotor = new MotorEx(hardwareMap, "NectarMotor");
		NectarMotor.setRunMode(Motor.RunMode.VelocityControl);
		NectarMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
		NectarMotor.setInverted(true);

		PollenMotor = new MotorEx(hardwareMap, "PollenMotor");
		PollenMotor.setRunMode(Motor.RunMode.VelocityControl);
		PollenMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
		PollenMotor.setInverted(false);

		voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

		Logger.recordMetadata("Phase", "Init_Loop");
	}

	@Override
	public void init_loop() {
		logger();
	}

	@Override
	public void start() {
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

		logger();

		telemetry.update();
	}

	@Override
	public void stop() {
		Logger.recordMetadata("Phase", "Stop");
		logger();
		NectarMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		PollenMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		Logger.recordMetadata("Phase", "Spool down");
		NectarMotor.setVelocity(0);
		PollenMotor.setVelocity(0);
		Logger.recordMetadata("Phase", "End of Execution");
	}

	private double getNectarRPM() {
		return NectarMotor.getVelocity() / 28 * 60;
	}

	private double getPollenRPM() {
		return PollenMotor.getVelocity() / 28 * 60;
	}

	private void logger(){
		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Nectar RPM", getNectarRPM());
		Logger.recordOutput("Pollen RPM", getPollenRPM());
		Logger.recordOutput("Nectar Current", NectarMotor.getCurrent(CurrentUnit.AMPS));
		Logger.recordOutput("Pollen Current", PollenMotor.getCurrent(CurrentUnit.AMPS));
		Logger.recordOutput("Voltage", voltageSensor.getVoltage()); 
	}
}
