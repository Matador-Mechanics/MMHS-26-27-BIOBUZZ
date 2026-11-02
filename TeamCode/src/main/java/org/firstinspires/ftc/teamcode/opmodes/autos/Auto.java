package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;
import com.skeletonarmy.marrow.zones.Zone;

import org.firstinspires.ftc.teamcode.globals.constants.Alliance;
import org.firstinspires.ftc.teamcode.globals.constants.HiveSide;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import java.util.List;

//import org.firstinspires.ftc.teamcode.CommandBase.Robot;

@Autonomous(name = "Auto", group = "Auto")
public class Auto extends LinearOpMode {
	Follower follower = Constants.create(hardwareMap);
	Zone robotZone = new PolygonZone(16, 18);
	Zone redHiveRedSide = new PolygonZone(
			new Point(1, 1),
			new Point(2, 2),
			new Point(1, 2),
			new Point(2, 1));
	Zone redHiveBlueSide = new PolygonZone(
			new Point(1, 1),
			new Point(2, 2),
			new Point(1, 2),
			new Point(2, 1));
	Alliance alliance = Alliance.RED;
	HiveSide hiveSide = HiveSide.RED;
	int i = 0;


	@Override
	public void runOpMode() throws InterruptedException {
		//Robot.init();
		follower.pose();

		Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
		limelight.setPollRateHz(30);
		limelight.start();

		while (!isStarted()) {
			List<LLResultTypes.FiducialResult> tags = limelight.getLatestResult().getFiducialResults();
			for (i = 0, i > tags.size(), i++) {
				LLResultTypes.FiducialResult tag = tags.get(i);
				if (tag.getFiducialId() == 6 /* arbitrary for now */){
					hiveSide = HiveSide.RED;
				}
			}
		}
		waitForStart();
	}

	public boolean inZone() {
		if (alliance == Alliance.RED) {
			if (hiveSide == HiveSide.RED) {
				return robotZone.isFullyInside(redHiveRedSide);
			} else {
				return robotZone.isFullyInside(redHiveBlueSide);
			}
		} else if (alliance == Alliance.BLUE) {
			if (hiveSide == HiveSide.RED) {
				return robotZone.isFullyInside(redHiveRedSide);
			} else {
				return robotZone.isFullyInside(redHiveBlueSide);
			}
		} else {
			return false;
		}
	}
}
