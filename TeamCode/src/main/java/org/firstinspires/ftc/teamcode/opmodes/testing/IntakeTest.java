package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.psilynx.psikit.core.Logger;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;

@PsiKitAutoLog(rlogPort = 5800, rlogFolder = "/sdcard/FIRST/PsiKit/")
@Disabled
@Utility(name = "IntakeLogger", description = "Spins up intake to desired speed and logs motor data.")
public class IntakeTest extends OpMode {
	MotorEx IntakeMotor;
	double dpad = 2750;
	Gamepad gp1 = null;
	VoltageSensor voltageSensor;
	double velocity = 0;
	double GR = 5; // gear ratio

	@Override
	public void init() {
		resetRuntime();
		Logger.recordOutput("Phase", "Init");

		IntakeMotor = new MotorEx(hardwareMap, "IntakeMotor");
		IntakeMotor.setRunMode(Motor.RunMode.VelocityControl);
		IntakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
		IntakeMotor.setInverted(false);

		voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

		Logger.recordOutput("Phase", "Init_Loop");
	}

	@Override
	public void init_loop() {
		logger();
	}

	@Override
	public void start() {
		Logger.recordOutput("Phase", "Loop");
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
		velocity = dpad * 28 * 60 * GR;
		IntakeMotor.setVelocity(velocity);
		telemetry.addData("RPM", dpad);

		logger();

		telemetry.update();
	}

	@Override
	public void stop() {
		Logger.recordOutput("Phase", "Stop");
		logger();
		IntakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		Logger.recordOutput("Phase", "Spool down");
		IntakeMotor.setVelocity(0);
		Logger.recordOutput("Phase", "End of Execution");
	}

	private double getIntakeRPM() {
		return IntakeMotor.getVelocity() / 28 * 60 * GR;
	}

	private void logger() {
		Logger.recordOutput("Time", getRuntime());
		Logger.recordOutput("Intake RPM", getIntakeRPM());
		Logger.recordOutput("Intake Current", IntakeMotor.getCurrent(CurrentUnit.AMPS));
		Logger.recordOutput("Voltage", voltageSensor.getVoltage());
	}
}
