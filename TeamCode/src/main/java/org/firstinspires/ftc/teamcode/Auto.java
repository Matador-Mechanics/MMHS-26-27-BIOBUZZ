package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Auto", group = "Auto")
public class Auto extends LinearOpMode {
    Robot Robot = new Robot(this);

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init();

        waitForStart();
    }
}
