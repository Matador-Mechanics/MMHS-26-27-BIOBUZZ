package org.firstinspires.ftc.teamcode.commandBase.subsystems;

import android.graphics.Color;
import android.util.Size;

import com.qualcomm.hardware.limelightvision.Limelight3A;
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
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ColorSpace;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.Scalar;

import java.util.ArrayList;
import java.util.List;

public class Vision extends SubsystemBase {
	private final OpMode OpMode;
	private final ColorBlobLocatorProcessor blobProcessor;
	private VisionPortal vPortal;
	private Limelight3A limelight;

	public Vision(final OpMode opMode) {
		OpMode = opMode;

		limelight = OpMode.hardwareMap.get(Limelight3A.class, "HiveCam");
		limelight.start();

		blobProcessor = new ColorBlobLocatorProcessor.Builder()
				.setRoi(ImageRegion.entireFrame())
				.setTargetColorRange(ColorRange.YELLOW)
				.setTargetColorRange(ColorRange.RED)
				.setTargetColorRange(ColorRange.BLUE)
				.setCircleFitColor(Color.rgb(255, 0, 255))
				.setBoxFitColor(0)
				.setContourColor(0)
				.build();

		VisionPortal.Builder builder = new VisionPortal.Builder()
				.setCamera(OpMode.hardwareMap.get(WebcamName.class, "FieldCam"))
				.setCameraResolution(new Size(640, 480))
				.enableLiveView(true)
				.setStreamFormat(VisionPortal.StreamFormat.MJPEG)
				.setAutoStopLiveView(false)
				.addProcessor(blobProcessor);
		vPortal = builder.build();
		vPortal.setProcessorEnabled(blobProcessor, true);
	}

	@Override
	public void periodic() {
		limelight.getLatestResult();
	}
}
