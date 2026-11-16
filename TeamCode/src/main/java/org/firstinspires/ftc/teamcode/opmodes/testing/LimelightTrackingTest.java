package org.firstinspires.ftc.teamcode.opmodes.testing;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Servo;

import org.psilynx.psikit.core.Logger;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;

@PsiKitAutoLog
@Autonomous(name = "LimelightTrackingTest")
public class LimelightTrackingTest extends LinearOpMode {

	Limelight3A limelight;
	private Servo limelightServo;

	int sleepTime = 1;
	double power = .35;

	double txMax = 15;
	double txMin = 9;
	double tyMax = 13.5;
	double tyMin = 12;
	double taMax = 2.35;
	double taMin = 2.07;
	int degree = 0;
	int MaxDegree = 270;
	double muliplyer =.5;
	double servoInPut = (double) degree / MaxDegree;


	@Override
	public void runOpMode() {

		limelight = hardwareMap.get(Limelight3A.class, "limelight");
		limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
		limelight.start(); // This tells Limelight to start looking!
		limelightServo = hardwareMap.get(Servo.class, "limelightServo");


		limelight.pipelineSwitch(0);


		waitForStart();


		while (opModeIsActive()) {
			telemetry.update();
			localize();

		}
	}

	public void localize() {
		//boolean localizing = true;

		LLResult result = limelight.getLatestResult();
		double tx;
		double ty;
		double ta;
		if (result != null && result.isValid()) {
			tx = result.getTx();
			ty = result.getTy(); // How far up or down the target is (degrees)
			ta = result.getTa(); // How big the target looks (0%-100% of the image)

			telemetry.addData("Target X", tx);
			telemetry.addData("Target Y", ty);
			telemetry.addData("Target Area", ta);
			telemetry.addData("Estimated angle (degree)", degree);
			Logger.recordOutput("Target X", tx);
			Logger.recordOutput("Target Y", ty);
			Logger.recordOutput("Target Area", ta);
			Logger.recordOutput("Estimated angle (degree)", degree);
			telemetry.update();

			if (tx < txMin) {
				// turn right
				degree -= muliplyer*(Math.abs((tx + 9.5)/19.5));
				if (degree <= 0) {
					degree = 0;
				} else if (degree >= MaxDegree) {
					degree = MaxDegree;
				}
				servoInPut = (double) degree / MaxDegree;
				limelightServo.setPosition(servoInPut);
				sleep(sleepTime);
			} else if (tx > txMax) {
				//turn left
				degree += muliplyer*(Math.abs((tx + 9.5)/19.5));
				if (degree <= 0) {
					degree = 0;
				} else if (degree >= MaxDegree) {
					degree = MaxDegree;
				}
				servoInPut = (double) degree / MaxDegree;
				limelightServo.setPosition(servoInPut);
				sleep(sleepTime);

			} else if (ta > taMax) {
				//move backward

			} else if (ta < taMin) {
				//move Forward

			} else {
				telemetry.addData("done", 0);
			}

		} else {
			telemetry.addData("Limelight", "No Targets");


		}
	}
}
