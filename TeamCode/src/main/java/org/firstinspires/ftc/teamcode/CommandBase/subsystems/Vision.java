package org.firstinspires.ftc.teamcode.CommandBase.subsystems;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.RobotLog;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Vision extends SubsystemBase {
	private final OpMode OpMode;
	private final AprilTagProcessor aprilTag;
	private VisionPortal vPortal;

	Vision(final OpMode opMode) {
		OpMode = opMode;

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
				.setCamera(OpMode.hardwareMap.get(WebcamName.class, "HiveCam"))
				.setCameraResolution(new Size(640, 480))
				.enableLiveView(true)
				.setStreamFormat(VisionPortal.StreamFormat.MJPEG)
				.setAutoStopLiveView(false)
				.addProcessor(aprilTag);
		vPortal = builder.build();
		vPortal.setProcessorEnabled(aprilTag, true);
	}

	@Override
	public void periodic() {
		List<AprilTagDetection> currentDetections = aprilTag.getDetections();

		// Step through the list of detections and display info for each one.
		for (AprilTagDetection detection : currentDetections) {
			if (detection instanceof AprilTagClusterDetection) {
				AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
				RobotLog.dd("Tag Name", clusterDet.metadata.name);
			} else {
				RobotLog.ww("Vision System", "Registered Impossible Single Tag Instance");
			}
		}
	}
}
