package org.firstinspires.ftc.teamcode.opmodes.testing;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Disabled
@TeleOp(name = "VisionTesting", group = "Testing")
public class VisionTest extends OpMode {
	private AprilTagProcessor aprilTag = null;
	private VisionPortal vPortal = null;

	@Override
	public void init() {
		aprilTag = new AprilTagProcessor.Builder()

				// The following default settings are available to un-comment and edit as needed.
				.setDrawAxes(true) // Changed in V12.0
				.setDrawTagOutline(true)
				.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
				.setTagLibrary(AprilTagGameDatabase.getBioBuzzTagLibrary())
				.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
				.setDrawCubeProjection(false)
				.build();

		VisionPortal.Builder builder = new VisionPortal.Builder()
				.setCamera(hardwareMap.get(WebcamName.class, "HiveCam"))
				.setCameraResolution(new Size(640, 480))
				.enableLiveView(true)
				.setStreamFormat(VisionPortal.StreamFormat.MJPEG)
				.setAutoStopLiveView(false)
				.addProcessor(aprilTag);
		vPortal = builder.build();
		vPortal.setProcessorEnabled(aprilTag, true);
	}

	@Override
	public void loop() {
		List<AprilTagDetection> currentDetections = aprilTag.getDetections();

		// Step through the list of detections and display info for each one.
		for (AprilTagDetection detection : currentDetections) {
			if (detection instanceof AprilTagClusterDetection) {
				AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
				RobotLog.dd("Tag Name", clusterDet.metadata.name);
				telemetry.addData("Tag metadata.name", clusterDet.metadata.name);
				telemetry.addData("Tag metadata.shortName", clusterDet.metadata.shortName);
				telemetry.addData("Tag metadata.distanceUnit", clusterDet.metadata.distanceUnit);
				telemetry.addData("Tag metadata.fieldOrientation", clusterDet.metadata.fieldOrientation);
				telemetry.addData("Tag metadata.fieldPosition", clusterDet.metadata.fieldPosition);
				telemetry.addData("Tag percentClusterFound", clusterDet.percentClusterFound);
				telemetry.addData("Tag distanceUnit", clusterDet.distanceUnit);
				telemetry.addData("Tag frameAcquisitionNanoTime", clusterDet.frameAcquisitionNanoTime);
				telemetry.addData("Tag ftcPose", clusterDet.ftcPose);
				telemetry.addData("Tag rawPose", clusterDet.rawPose);
				telemetry.addData("Tag robotPose", clusterDet.robotPose);


			} else {
				RobotLog.ww("Vision System", "Registered Impossible Single Tag Instance");
				telemetry.addData("Vision System", "Registered Impossible Single Tag Instance");
			}
		}
		telemetry.update();
	}
}

