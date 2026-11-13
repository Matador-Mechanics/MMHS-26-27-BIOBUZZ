package org.firstinspires.ftc.teamcode.opmodes.testing;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;
import org.psilynx.psikit.core.Logger;

import java.util.List;
import java.util.Objects;

@PsiKitAutoLog(rlogPort = 5800, rlogFolder = "/sdcard/FIRST/PsiKit/")
@Autonomous(name = "LimelightTesting")
public class LimelightTesting extends LinearOpMode {

	Limelight3A limelight;

	@Override
	public void runOpMode() {
		limelight = hardwareMap.get(Limelight3A.class, "limelight");
		limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
		limelight.start(); // This tells Limelight to start looking!
		limelight.pipelineSwitch(0);

		int txMax = 0;
		int txMin = 0;
		int tyMax = 0;
		int tyMin = 0;
		int taMax = 0;
		int taMin = 0;

		LLResultTypes.FiducialResult fiducialResult = null;

		waitForStart();
		while (opModeIsActive()) {
			telemetry.update();
			LLResult result = limelight.getLatestResult();

			double tx;
			double ty;
			double ta;

			processLimeLightResults();


		}

	}


	private void processLimeLightResults() {
		double tx;
		double ty;
		double ta;
		Pose3D txx;
		LLResult result = limelight.getLatestResult();
		if (result != null && result.isValid()) {
			// Get the list of ALL detected fiducials (AprilTags)
			List<LLResultTypes.FiducialResult> fiducialList = result.getFiducialResults();

			tx = result.getTx();
			ty = result.getTy(); // How far up or down the target is (degrees)
			ta = result.getTa(); // How big the target looks (0%-100% of the image)
			txx = result.getBotpose();

			double targetOffsetAngle_Vertical = ty;

			// how many degrees back is your limelight rotated from perfectly vertical?
			double limelightMountAngleDegrees = 25;

			// distance from the center of the Limelight lens to the floor
			double limelightLensHeightInches = 3.25;

			// distance from the target to the floor
			double goalHeightInches = 28.5;

			double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;

			//calculate distance
			double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(Math.toRadians(angleToGoalDegrees));
/*
			double angleToGoalRadians = Math.toRadians(65 - ty); // the mount agnle  + ty

			double distanceInches = (15.5 - 9) / Math.tan(angleToGoalRadians);
*/

			telemetry.addData("Target X", tx);
			telemetry.addData("Target Y", ty);
			Logger.recordOutput("Target Y", ty);
			telemetry.addData("Target Distance", distanceFromLimelightToGoalInches);
			Logger.recordOutput("Target Distance", distanceFromLimelightToGoalInches);
			telemetry.addData("Robot Pose x", txx.getPosition().x);
			telemetry.addData("Robot Pose y", txx.getPosition().y);
			telemetry.addData("Robot Pose x", txx.getPosition().x);
			telemetry.addData("Robot Pose Yaw", txx.getOrientation().getYaw());

			telemetry.addData("Target Area", ta);

			if (!fiducialList.isEmpty()) {
				telemetry.addData("Detections Found", fiducialList.size());

				// Iterate through each detected tag
				for (LLResultTypes.FiducialResult fiducial : fiducialList) {
					int id = fiducial.getFiducialId();


					// You can also get pose data (X, Y, Z, Pitch, Yaw, Roll)


					telemetry.addData("Tag ID", id);
					telemetry.addData("Tag ID2", fiducial.getFiducialId());

				}
			} else {
				telemetry.addData("Detections Found", "None");
			}
		} else {
			telemetry.addData("Limelight Data", "Invalid or Stale");
			telemetry.addData("Staleness", Objects.requireNonNull(result).getStaleness());
		}
		telemetry.update();
	}
}